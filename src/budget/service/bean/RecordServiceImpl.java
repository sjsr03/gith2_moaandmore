package budget.service.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import budget.model.dto.RecordModifyDTO;
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
			
			System.out.println("서비스에서 타입 ;;;: "+ noBudgetDTO.getType());
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
	public RecordPageDTO selectAllBudgetByNum(int budgetNum, String pageNum, String keyword) throws SQLException {
		
		RecordPageDTO recordPage = new RecordPageDTO();
		if(pageNum == "") {
			pageNum = "1";
		}
		// 페이지 정보 담기
		int pageSize = 10;
		int currPage = Integer.parseInt(pageNum);
		int startRow = (currPage - 1) * pageSize + 1;
		int endRow = currPage*pageSize;
		int count = 0;
			
		List recordList = null;
		System.out.println("키워드@@@@ "+ keyword);
		// 검색키워드가 있는 경우, 없는 경우 체크해서 처리 
		if(keyword == null) { // 키워드가 비어있으면 전체로 가져오기
			System.out.println("널?");
			// 전체 목록 수 가져오기 
			count = recordBudgetDAO.countAllBudgetByNum(budgetNum);
			//System.out.println("서비스에서!!!!!예산 개수 : " + count);
			//System.out.println("서비스에서!!!!!버젯넘 : " + budgetNum);
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
		}else { // 키워드에 내용이 있으면 키워드 결과로 가져오기
			System.out.println("널아님");
			// 전체 목록 수 가져오기 
			count = recordBudgetDAO.countAllBudgetByNum(budgetNum, keyword);
			System.out.println("서비스에서 카테고리 포함!예산 개수 : " + count);
			//System.out.println("서비스에서!!!!!버젯넘 : " + budgetNum);
			if(count > 0) { // 지출 내역이 하나라도 있으면 전체 리스트 가져오기 
				recordList = recordBudgetDAO.selectAllBudgetByNum(budgetNum, startRow, endRow, keyword);
			}
			recordPage.setCount(count);
			recordPage.setCurrPage(currPage);
			recordPage.setEndRow(endRow);
			recordPage.setPageNum(pageNum);
			recordPage.setPageSize(pageSize);
			recordPage.setRecordList(recordList);
			recordPage.setStartRow(startRow);
			
		}
		return recordPage;
	}
	
	// 내역 삭제
	@Override
	public int deleteRecord(int number, String type) throws SQLException {
		int result = 0;
		if(type.equals("budget")){ // 예산이면 예산 삭제
			result = recordBudgetDAO.deleteBudgetRecord(number);
		}else { // 예산외 삭제
			result = recordNoBudgetDAO.DeleteNoBudgetRecord(number);
		}
		
		return result;	
	}
	@Override
	public RecordPageDTO selectAllNoBudget(SearchForRecordDTO searchForRecordDTO)
			throws SQLException {
		RecordPageDTO recordPage = new RecordPageDTO();
		
		if(searchForRecordDTO.getPageNum() == "") {
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
		
		if(searchForRecordDTO.getKeyword() == null) {
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
			
		}else { // 키워드가 있을 때 
			// 전체 목록 수 가져오기 (타입별로) 
			count = recordNoBudgetDAO.CountAllNoBudgetByIdKeyword(searchForRecordDTO);
			if(count > 0) { // 지출 내역이 하나라도 있으면 전체 리스트 가져오기 
				recordList = recordNoBudgetDAO.selectAllNoBudgetKeyword(searchForRecordDTO);
				System.out.println("예산번호로 예산기록목록 가져오기  : " + recordList.size());
			}
			
			recordPage.setCount(count);
			recordPage.setCurrPage(currPage);
			recordPage.setEndRow(endRow);
			recordPage.setPageNum(searchForRecordDTO.getPageNum());
			recordPage.setPageSize(pageSize);
			recordPage.setRecordList(recordList);
			recordPage.setStartRow(startRow);
		}
		return recordPage;	
	}
	// 아이디랑 타입으로 나눠서 예산, 예산외 기록들 가져오기 
	@Override
	public RecordPageDTO selectAllRecord(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		RecordPageDTO recordPage = new RecordPageDTO();
		
		if(searchForRecordDTO.getPageNum() == "") {
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

		
		// 여기서 타입 체크 후 dao 각각 불러줘야함!
		// 타입 체크  + 키워드 유무 체크 후 각각 해당하는 dao 호출
		String type=searchForRecordDTO.getType();
		if(type.equals("budgetincome")) {
			System.out.println("222222222222");
			System.out.println("타입확인 : "+type);
			searchForRecordDTO.setType("income");
			//예산+수입이면
			System.out.println("예산+수입");
			count = recordBudgetDAO.CountBudgetRecordById(searchForRecordDTO);
			count += recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			
			if(count >0) { // 내역이 하나라도 있으면 
				recordList = recordNoBudgetDAO.selectAllRecord(searchForRecordDTO);
			}
		}else if(type.equals("budgetoutcome")){
			searchForRecordDTO.setType("outcome");
			//예산+지출이면
			System.out.println("예산+지출");
			count = recordBudgetDAO.CountBudgetRecordById(searchForRecordDTO);
			count += recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			
			if(count >0) { // 내역이 하나라도 있으면 
				recordList = recordNoBudgetDAO.selectAllRecord(searchForRecordDTO);
			}
		}else if(type.equals("budgetincomeoutcome")){ //예산+수입+지출이면
			System.out.println("예산+수입+지출");
			count = recordBudgetDAO.CountBudgetRecordById(searchForRecordDTO);
			count += recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			if(count>0) {
				recordList = recordNoBudgetDAO.selectAllRecord(searchForRecordDTO);
			}
		}else{ //수입+지출이면
			System.out.println("수입+지출");
			count = recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			
			if(count>0) {
				recordList = recordNoBudgetDAO.selectNobudgetRecord(searchForRecordDTO);
			}
		}
		
		if(type.equals("incomeoutcome")) { //수입+지출이면
			System.out.println("수입+지출");
			count = recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			if(count>0) {
				recordList = recordNoBudgetDAO.selectNobudgetRecord(searchForRecordDTO);
			}
		}else {
			if(type.equals("budgetincome")) { //예산+수입이면
				searchForRecordDTO.setType("income");
				
			}else if(type.equals("budgetoutcome")){//예산+지출이면
				searchForRecordDTO.setType("outcome");
				
			}else if(type.equals("budgetincomeoutcome")){//예산+수입+지출이면
				
			}
			count = recordBudgetDAO.CountBudgetRecordById(searchForRecordDTO);
			count += recordNoBudgetDAO.CountNoBudgetRecordById(searchForRecordDTO);
			
			if(count >0) { // 내역이 하나라도 있으면 
				recordList = recordNoBudgetDAO.selectAllRecord(searchForRecordDTO);
			}
			
			// 한번에 호출
			
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
		recordPage.setRecordList(recordList);
		
		return recordPage;	
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
	@Override
	public void modifyRecord(RecordModifyDTO recordModifyDTO, MultipartFile imgfile) throws Exception{
		BudgetDTO budgetDTO = new BudgetDTO();
		BudgetDetailDTO budgetDetailDTO = new BudgetDetailDTO();
		NoBudgetDTO noBudgetDTO = new NoBudgetDTO();
		NoBudgetDetailDTO noBudgetDetailDTO = new NoBudgetDetailDTO();
		
		// reg에 값을 넣어주기 위해 날짜+시간을 더해서 timestamp로 변환
		String time = recordModifyDTO.getTime();
		String date = recordModifyDTO.getDate();
		String oldTime = date + " " + time;
		System.out.println("뭔데!! :" + time.length());
		if(time.length() <= 5) {
			oldTime = oldTime+":00";
		}
		System.out.println("시간 :"+ oldTime);
		Timestamp reg = Timestamp.valueOf(oldTime);
		String type = recordModifyDTO.getType();
		
		// 사진 업로드 처리
		if(imgfile.getSize() > 0) {
			String originFileName = imgfile.getOriginalFilename();
			System.out.println("originFileName:" + originFileName);
			// 서버에 저장할 파일 이름 생성
			String saveFileName = getSaveFileName(originFileName);
			
			writeFile(imgfile, saveFileName);
			/*
			String path = request.getRealPath("save"); // 저장할 폴더 경로
			String orgName = imgfile.getOriginalFilename(); 
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
			
			 ((MultipartFile) file).transferTo(copyFile);	
			 */	
			// dto에 세팅까지해주기
			noBudgetDetailDTO.setImg(originFileName);
			budgetDetailDTO.setImg(originFileName);	
				
		}else { // 이미지가 없을 땐 default값 넣어주기.
			noBudgetDetailDTO.setImg("default.gif");
			budgetDetailDTO.setImg("default.gif");
		}
		
		// 타입 체크해서 각 DTO에 담아준 후 맞는 DAO 호출
		if(type.equals("budget")) {		
			budgetDTO.setAmount(recordModifyDTO.getAmount());
			// 예산번호는 날짜로 체크해서 가져오기때문에 같이 update해줘야함 
			budgetDTO.setBudget_no(recordModifyDTO.getBudget_no());
			budgetDTO.setBudget_outcome_no(recordModifyDTO.getUniqueNum());
			budgetDTO.setCategory_no(recordModifyDTO.getBudget_category_no());
			budgetDTO.setId(recordModifyDTO.getId());
			budgetDetailDTO.setBudget_outcome_no(recordModifyDTO.getUniqueNum());
			budgetDetailDTO.setContent(recordModifyDTO.getContent());		
			budgetDetailDTO.setMemo(recordModifyDTO.getMemo());
			
			// reg는 date+time해서 timestamp로 형변환 후 던져줌
			budgetDTO.setReg(reg);
			
			System.out.println(budgetDTO.toString());
			System.out.println(budgetDetailDTO.toString());
			
			
			recordBudgetDAO.modifyBudgetRecord(budgetDTO, budgetDetailDTO);
		}else if(type.equals("income") || type.equals("outcome")) {
			noBudgetDTO.setAmount(recordModifyDTO.getAmount());
			noBudgetDTO.setId(recordModifyDTO.getId());
			noBudgetDTO.setIncome_category_no(recordModifyDTO.getIncome_category_no());
			noBudgetDTO.setNobudget_no(recordModifyDTO.getUniqueNum());
			noBudgetDTO.setOutcome_category_no(recordModifyDTO.getOutcome_category_no());
			noBudgetDTO.setReg(reg);
			noBudgetDTO.setType(type);
			noBudgetDetailDTO.setNobudget_no(recordModifyDTO.getUniqueNum());
			noBudgetDetailDTO.setContent(recordModifyDTO.getContent());
			noBudgetDetailDTO.setMemo(recordModifyDTO.getMemo());	
			recordNoBudgetDAO.modifyNoBudgetRecord(noBudgetDTO, noBudgetDetailDTO);
		}
	}
	@Override
	public String getSaveFileName(String originName) throws Exception {
			String fileName="";
			Calendar calendar = Calendar.getInstance();
			fileName += calendar.get(Calendar.YEAR);
			fileName += calendar.get(Calendar.MONTH);
			fileName += calendar.get(Calendar.DATE);
			fileName += calendar.get(Calendar.HOUR);
			fileName += calendar.get(Calendar.MINUTE);
			fileName += calendar.get(Calendar.SECOND);
			fileName += calendar.get(Calendar.MILLISECOND);
			fileName += originName;
			return fileName;
		
	}
	@Override
	public boolean writeFile(MultipartFile mf, String saveFileName) throws IOException {
		boolean result = false;
		
		byte[] data = mf.getBytes();
		FileOutputStream fos = new FileOutputStream("c:/save/"+ mf.getOriginalFilename());
		fos.write(data);
		fos.close();
		return result;
	}
	
	
}
