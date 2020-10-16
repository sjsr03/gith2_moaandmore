package budget.service.bean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import budget.model.dto.SearchForRecordDTO;
import category.model.dao.CategoryDAO;
import category.model.dto.income_categoryDTO;
import category.model.dto.outcome_categoryDTO;

@Service
public class RecordServiceImpl implements RecordService{

	@Autowired
	private RecordBudgetDAO recordBudgetDAO = null;
	@Autowired
	private RecordNoBudgetDAO recordNoBudgetDAO = null;
	@Autowired
	private CategoryDAO categoryDAO = null;
	
	// 수입/지출내역 insert 메서드  
	@Override
	public void insertRecord(MultipartHttpServletRequest request, BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO, NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBudgetDetailDTO, Timestamp date) throws SQLException, IOException {
		
		
		// 이미지 저장처리 			
		MultipartFile mf = request.getFile("image");
		if(mf.getSize() > 0) {
			String path = request.getRealPath("save"); // 저장할 폴더 경로
			String orgName = mf.getOriginalFilename(); 
			String imgName = orgName.substring(0, orgName.lastIndexOf('.'));
			// 이미지 파일 확장자만 추출
			String ext = orgName.substring(orgName.lastIndexOf('.'));
			
			// 이름에 실행되는 시간 넣어주기 
			long now = System.currentTimeMillis();
			// 새로운 이름 만들기
			String newName = imgName+now+ext;
			
			// imgPath 다시 만들어주기(newName사용)
			String imgPath = path + "\\" + newName;
			
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
		
		
		
		// dto에 세팅 
		String type = request.getParameter("type");
		if(type.equals("outcome") || type.equals("income")){ // 예산외 수입/지출 일 떄
			noBudgetDTO.setReg(date);
			
			
			recordNoBudgetDAO.insertNoBudget(noBudgetDTO);
			
			int nobudget_no = noBudgetDTO.getNobudget_no();
			// 예산 외 내역 insert해준 후  구분번호 예산외 세부내역dto에 다시 세팅해주기 
			noBudgetDetailDTO.setNobudget_no(nobudget_no);
			recordNoBudgetDAO.insertNoBudgetDetailDTO(noBudgetDetailDTO);	
			
		}else { // 예산일 때 

			//budgetDTO.setDate(date);
			

			budgetDTO.setReg(date);

			
			// 예산 내역 insert해준 후  구분번호 예산세부내역dto에 다시 세팅해주기 
			recordBudgetDAO.insertBudget(budgetDTO);
			int budget_outcome_no = budgetDTO.getBudget_outcome_no();
			budgetDetailDTO.setBudget_outcome_no(budget_outcome_no);
			
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
		int endRow = currPage*pageSize;
		int count = 0;
		
		List recordList = null;
		
		// 전체 목록 수 가져오기 
		count = recordBudgetDAO.countAllBudgetByNum(budgetNum);
		if(count > 0) { // 지출 내역이 하나라도 있으면 전체 리스트 가져오기 
			recordList = recordBudgetDAO.selectAllBudgetByNum(budgetNum, startRow, endRow);
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
	
	// 내역 삭제
	@Override
	public int budgetRecordDelete(String budget_outcome_no) throws SQLException {
		int result = 0;
		result = recordBudgetDAO.budgetRecordDelete(budget_outcome_no);
		return result;	
	}
	@Override
	public RecordPageDTO selectAllNoBudget(SearchForRecordDTO searchForRecordDTO)
			throws SQLException {
		RecordPageDTO recordPage = new RecordPageDTO();
		
		if(searchForRecordDTO.getPageNum() == null) {
			searchForRecordDTO.setPageNum("1");
		}
		// 페이지 정보 담기
		int pageSize = 10;
		int currPage = Integer.parseInt(searchForRecordDTO.getPageNum());
		int startRow = (currPage - 1) * pageSize + 1;
		int endRow = currPage*pageSize;
		int count = 0;
		
		List recordList = null;
		searchForRecordDTO.setStartRow(startRow);
		searchForRecordDTO.setEndRow(endRow);
		
		// 전체 목록 수 가져오기 (타입별로) 
		count = recordNoBudgetDAO.CountAllNoBudgetById(searchForRecordDTO);
		if(count > 0) { // 지출 내역이 하나라도 있으면 전체 리스트 가져오기 
			recordList = recordNoBudgetDAO.selectAllNoBudget(searchForRecordDTO);
			System.out.println("예산번호로 예산기록목록 가져오기  : " + recordList.size());
		}
		
		recordPage.setCount(count);
		recordPage.setCurrPage(currPage);
		recordPage.setEndRow(endRow);
		recordPage.setPageNum(searchForRecordDTO.getPageNum());
		recordPage.setPageSize(pageSize);
		recordPage.setRecordList(recordList);
		recordPage.setStartRow(startRow);
		
		return recordPage;	
	}
	// 아이디랑 타입으로 나눠서 예산, 예산외 기록들 가져오기 
	@Override
	public Map selectAllRecord(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		RecordPageDTO recordPage = new RecordPageDTO();
		Map allRecord = new HashMap();
		if(searchForRecordDTO.getPageNum() == null) {
			searchForRecordDTO.setPageNum("1");
		}
		// 페이지 정보 담기
		int pageSize = 10;
		int currPage = Integer.parseInt(searchForRecordDTO.getPageNum());
		int startRow = (currPage - 1) * pageSize + 1;
		int endRow = currPage*pageSize;
		int count = 0;
		
		List recordList = null;
		searchForRecordDTO.setStartRow(startRow);
		searchForRecordDTO.setEndRow(endRow);
		
		
		// 아이디당 수입/지출 카테고리 통으로 가져오기
		
		
		List incomeCategoryList = categoryDAO.selectAllIncomeCategoryById(searchForRecordDTO.getId());
		List outcomeCategoryList = categoryDAO.selectAllById(searchForRecordDTO.getId());
		
		Map incomeCategories = new HashMap();
		for(int i = 0; i < incomeCategoryList.size(); i++) {
			incomeCategories.put(((income_categoryDTO)incomeCategoryList.get(i)).getCategory_no(), ((income_categoryDTO)incomeCategoryList.get(i)).getCategory_name());		
		}
		
		Map outcomeCategories = new HashMap();
		for(int i = 0; i < outcomeCategoryList.size(); i++) {
			outcomeCategories.put(((outcome_categoryDTO)outcomeCategoryList.get(i)).getCategory_no(), ((outcome_categoryDTO)outcomeCategoryList.get(i)).getCategory_name());		
		}
		
		allRecord.put("incomeCategories", incomeCategories);
		allRecord.put("outcomeCategories", outcomeCategories);
		System.out.println("11111111111111");
		// 여기서 타입 체크 후 dao 각각 불러줘야함!
		String type=searchForRecordDTO.getType();
		if(type.equals("budgetincome")) {
			System.out.println("222222222222");
			System.out.println("타입확인 : "+type);
			searchForRecordDTO.setType("income");
			//예산+수입이면
			System.out.println("예산+수입");
			count = recordNoBudgetDAO.CountBudgetRecordById(searchForRecordDTO);
			count += recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			
			if(count >0) { // 내역이 하나라도 있으면 
				recordList = recordNoBudgetDAO.selectAllRecord(searchForRecordDTO);
			}
		}else if(type.equals("budgetoutcome")){
			searchForRecordDTO.setType("outcome");
			//예산+지출이면
			System.out.println("예산+지출");
			count = recordNoBudgetDAO.CountBudgetRecordById(searchForRecordDTO);
			count += recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			
			if(count >0) { // 내역이 하나라도 있으면 
				recordList = recordNoBudgetDAO.selectAllRecord(searchForRecordDTO);
			}
		}else if(type.equals("all")){ //예산+수입+지출이면
			System.out.println("예산+수입+지출");
			count = recordNoBudgetDAO.CountBudgetRecordById(searchForRecordDTO);
			count += recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			if(count>0) {
				recordList = recordNoBudgetDAO.selectNobudgetRecord(searchForRecordDTO);
			}
		}else{ //수입+지출이면
			System.out.println("수입+지출");
			count = recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			
			if(count>0) {
				recordList = recordNoBudgetDAO.selectNobudgetRecord(searchForRecordDTO);
			}
		}
		System.out.println("결과는???  : " + recordList.size());
		System.out.println("결과 count ??  : " + count);
		
		
		recordPage.setCount(count);
		recordPage.setCurrPage(currPage);
		recordPage.setEndRow(endRow);
		recordPage.setPageNum(searchForRecordDTO.getPageNum());
		recordPage.setPageSize(pageSize);
		recordPage.setRecordList(recordList);
		recordPage.setStartRow(startRow);
		
		allRecord.put("recordPage", recordPage);
		return allRecord;	
	}
	// 날짜비교 
	@Override
	public Boolean compareDate(SearchForRecordDTO searchForRecordDTO, List budgetDate) throws SQLException, ParseException{
		Boolean result = false;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDay = format.parse((String) budgetDate.get(1));
		Date endDay = format.parse((String)budgetDate.get(0));
		Date comparedDay = format.parse(searchForRecordDTO.getSearchDate());
		
		//System.out.println("startDay : " +startDay);
		//System.out.println("endDay : " +endDay);
		//System.out.println("comparedDay : " +comparedDay);
		int compareS = comparedDay.compareTo(startDay);
		int compareE = comparedDay.compareTo(endDay);
		//System.out.println("시작날짜랑 비교 : " + compareS + "    끝나는 날짜랑 비교 : " + compareE );
		if((compareS >= 0) && (compareE <= 0)) {
			result = true;
		}
		return result;
	}
	
}
