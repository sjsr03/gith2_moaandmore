package budget.service.bean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dao.RecordBudgetDAO;
import budget.model.dao.RecordNoBudgetDAO;
import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.RecordPageDTO;

@Service
public class RecordServiceImpl implements RecordService{

	@Autowired
	private RecordBudgetDAO recordBudgetDAO = null;
	@Autowired
	private RecordNoBudgetDAO recordNoBudgetDAO = null;

	// 수입/지출내역 insert 메서드  
	@Override
	public void insertRecord(MultipartHttpServletRequest request, BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO, NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBudgetDetailDTO, Timestamp date) throws SQLException, IOException {
		
		
		// 이미지 저장처리 			
		MultipartFile mf = request.getFile("image");
		if(mf.getSize() > 0) {
			System.out.println("이미지파일 : " +  mf);
			String path = request.getRealPath("save"); // 저장할 폴더 경로
			String orgName = mf.getOriginalFilename(); 
			System.out.println("orgname : " + orgName);
			String imgName = orgName.substring(0, orgName.lastIndexOf('.'));
			System.out.println("imgName : " + imgName);
			// 이미지 파일 확장자만 추출
			String ext = orgName.substring(orgName.lastIndexOf('.'));
			System.out.println("subString 확장자 이름 : " + ext);
			
			// 이름에 실행되는 시간 넣어주기 
			long now = System.currentTimeMillis();
			// 새로운 이름 만들기
			String newName = imgName+now+ext;
			System.out.println(newName);
			
			// imgPath 다시 만들어주기(newName사용)
			String imgPath = path + "\\" + newName;
			System.out.println("newName으로 새로만든 imgPath : " + imgPath);
			
			// 저장해주기
			File file = new File(imgPath);
			File copyFile = new File(imgPath);
			
			mf.transferTo(copyFile);
			
			// dto에 세팅까지해주기
			noBudgetDetailDTO.setImg(newName);
			budgetDetailDTO.setImg(newName);
			
		}else { // 이미지가 없을 땐 default값 넣어주기.
			noBudgetDetailDTO.setImg("default.gif");
			budgetDetailDTO.setImg("default.gif");
		}
		
		System.out.println("타입 확인! : " + request.getParameter("type"));
		
		
		// dto에 세팅 
		String type = request.getParameter("type");
		if(type.equals("outcome") || type.equals("income")){ // 예산외 수입/지출 일 떄
			noBudgetDTO.setReg(date);
			
			System.out.println("예산 외 되냥 " + noBudgetDetailDTO.getImg());
			System.out.println("예산외 메모 : " + noBudgetDetailDTO.getMemo());
			System.out.println("예산외 content : " + noBudgetDetailDTO.getContent());
			
			recordNoBudgetDAO.insertNoBudget(noBudgetDTO);
			
			int nobudget_no = noBudgetDTO.getNobudget_no();
			// 예산 외 내역 insert해준 후  구분번호 예산외 세부내역dto에 다시 세팅해주기 
			noBudgetDetailDTO.setNobudget_no(nobudget_no);
			recordNoBudgetDAO.insertNoBudgetDetailDTO(noBudgetDetailDTO);	
			
		}else { // 예산일 때 

			//budgetDTO.setDate(date);
			

			budgetDTO.setReg(date);

			System.out.println("되낭 : " + budgetDetailDTO.getImg());
			System.out.println("예산내 메모 : " + budgetDetailDTO.getMemo());
			
			// 예산 내역 insert해준 후  구분번호 예산세부내역dto에 다시 세팅해주기 
			recordBudgetDAO.insertBudget(budgetDTO);
			int budget_outcome_no = budgetDTO.getBudget_outcome_no();
			budgetDetailDTO.setBudget_outcome_no(budget_outcome_no);
			
			//System.out.println("구분번호 ㅣ: " + budget_outcome_no);
			
			recordBudgetDAO.insertBudgetDetail(budgetDetailDTO);			
		}	
	}
	// 예산번호로 해당 예산 기록 목록 가져오기
	@Override
	public RecordPageDTO selectAllBudgetByNum(int budgetNum, String pageNum) throws SQLException {
		
		RecordPageDTO recordPage = new RecordPageDTO();
		
		if(pageNum == null) {
			pageNum = "1";
		}
		// 페이지 정보 담기
		int pageSize = 10;
		int currPage = Integer.parseInt(pageNum);
		int startRow = (currPage - 1) * pageSize + 1;
		int endRow = currPage * pageSize + 1;
		int count = 0;
		
		List recordList = null;
		
		// 전체 목록 수 가져오기 
		count = recordBudgetDAO.countAllBudgetByNum(budgetNum);
		if(count > 0) { // 지출 내역이 하나라도 있으면 전체 리스트 가져오기 
			recordList = recordBudgetDAO.selectAllBudgetByNum(budgetNum, startRow, endRow);
			System.out.println("예산번호로 예산기록목록 가져오기  : " + recordList.size());
		}
		
		recordPage.setCount(count);
		recordPage.setCurrPage(currPage);
		recordPage.setEndRow(endRow);
		recordPage.setPageNum(pageNum);
		recordPage.setPageSize(pageSize);
		recordPage.setRecordList(recordList);
		recordPage.setStartRow(startRow);
		
		return recordPage;
	}
}
