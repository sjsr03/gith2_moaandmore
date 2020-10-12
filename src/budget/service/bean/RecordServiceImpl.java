package budget.service.bean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		String path = request.getRealPath("save"); // 저장할 폴더 경로
		String orgName = mf.getOriginalFilename(); 
		String imgName = orgName.substring(0, orgName.lastIndexOf('.'));
		
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
			
		/*
		System.out.println("예산 사용 금액 : " +budgetDTO.getAmount());
		System.out.println("예산안 구분번호 : " +budgetDTO.getBudget_no());
		System.out.println("예산 카테고리  구분번호: " +budgetDTO.getCategory_no());
		System.out.println("아이디  : " +budgetDTO.getId());
		System.out.println("예산 content : " + budgetDetailDTO.getContent());
		
		System.out.println("-----------------------------------");
		System.out.println("예산외 사용 금액 : " +noBudgetDTO.getAmount());
		System.out.println("예산외 카테고리 구분번호 : " +noBudgetDTO.getCategory_no());
		System.out.println("아이디 : " +noBudgetDTO.getId());
		System.out.println("예산외 타입 : " +noBudgetDTO.getType());
		*/
		
		System.out.println("타입 확인!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 : " + request.getParameter("type"));
		
		
		// dto에 세팅 
		String type = request.getParameter("type");
		if(type.equals("outcome") || type.equals("income")){ // 예산외 수입/지출 일 떄
			noBudgetDTO.setDate(date);
			noBudgetDetailDTO.setImg(newName);
			System.out.println("예산 외 되냥 " + noBudgetDetailDTO.getImg());
			System.out.println("예산외 메모 : " + noBudgetDetailDTO.getMemo());
			System.out.println("예산외 content : " + noBudgetDetailDTO.getContent());
			
			recordNoBudgetDAO.insertNoBudget(noBudgetDTO);
			
			int nobudget_no = noBudgetDTO.getNobudget_no();
			// 예산 외 내역 insert해준 후  구분번호 예산외 세부내역dto에 다시 세팅해주기 
			noBudgetDetailDTO.setNobudget_no(nobudget_no);
			recordNoBudgetDAO.insertNoBudgetDetailDTO(noBudgetDetailDTO);	
			
		}else { // 예산일 때 
			budgetDTO.setDate(date);
			budgetDetailDTO.setImg(newName);
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



}
