package budget.controller.bean;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

import budget.model.dao.RecordBudgetDAO;
import budget.model.dao.RecordBudgetDAOImpl;
import budget.model.dao.RecordNoBudgetDAO;
import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.RecordModifyDTO;
import budget.model.dto.RecordPageDTO;
import budget.model.dto.SearchForRecordDTO;
import budget.service.bean.BudgetService;
import budget.service.bean.RecordService;
import category.model.dto.income_categoryDTO;
import category.model.dto.outcome_categoryDTO;
import category.service.bean.CategoryService;


@Controller
@EnableWebMvc
@RequestMapping("/record/")
public class RecordBean {

	
	@Autowired
	private RecordService recordService = null;	
	@Autowired
	private CategoryService categoryService = null;
	@Autowired
	private BudgetService budgetService = null;

	
	// 걍해보는거
	@Autowired
	private RecordBudgetDAO dao = null;
	
	@RequestMapping("recordForm.moa")
	public String recordForm(HttpServletRequest request, Model model)throws SQLException {
		// 여기에서 해당 회원의 카테고리까지 한번에 다 가져올 것임
		// 가져와서 model로 다 뿌려주기!!! (수입, 지출, 예산지출 list에 담아서)
		String id = (String)request.getSession().getAttribute("memId");
		
		
		// 현재 날짜+시간 받아오기
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currTime = new Date();
		String now = sdf.format(currTime);
		Timestamp dateTime = Timestamp.valueOf(now);
		String today = now.substring(0, 10);
		//System.out.println("today >>>" + today);
		// 예산 번호 뽑아오기
		int budgetNum = budgetService.selectBudgetNum(id, dateTime);
		
		// 카테고리 번호 뽑아오기
		List categoryNums = budgetService.selectBudgetCategoryNums(budgetNum);
		
		// 카테고리 번호로 카테고리 이름 가져오기(hashmap으로)
		HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);
		
		List outcomeCategories = categoryService.selectAllById(id);
		List incomeCategories = categoryService.selectAllIncomeCategoryById(id);
		
		
		
		// 현재 진행중인 예산의 끝나는 날짜와 지난 예산의 시작 날짜를 가져오기
		List budgetDate = budgetService.selectBudgetDate(id);
		model.addAttribute("budgetDate", budgetDate);
		model.addAttribute("outcomeCategories", outcomeCategories);
		model.addAttribute("incomeCategories", incomeCategories);
		model.addAttribute("categories", categories);
		model.addAttribute("id", id);
		model.addAttribute("today", today);
		model.addAttribute("budgetNum", budgetNum);
		//System.out.println("recordBean budgetNum : " + budgetNum);
		
		return "budget/recordForm";
	}
	
	// ajax로 회원 예산 카테고리 가져오기
	@RequestMapping(value="budgetCategory.moa", method= {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map budgetCategory(HttpServletRequest request, String date, String id) throws SQLException{
		// moneyLog에서 사용할 경우 아이디 없이옴 
		if(id == "") {
			id = (String)request.getSession().getAttribute("memId");
		}	
		// budgetdetail 테이블에 있는 예산 번호 가져와야함 
		// string으로 넘어온 날짜에 시간 임의로 넣어서 timeStamp로 형변환
		String newDate = date + " 00:00:00";
		Timestamp dateTime = Timestamp.valueOf(newDate);
		// 예산 번호 뽑아오기
		int budgetNum = budgetService.selectBudgetNum(id, dateTime);
		// 카테고리 번호 뽑아오기
		List categoryNums = budgetService.selectBudgetCategoryNums(budgetNum);
		
		// 카테고리 번호로 카테고리 이름 가져오기(hashmap으로)	
		HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);
		
		// categories에 예산 번호 추가해주기   
		categories.put("budgetNum", budgetNum);	
		return categories;	
	}
	
	
	
	@RequestMapping(value="recordPro.moa", method=RequestMethod.POST)
	public String recordPro(MultipartHttpServletRequest request, BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO, NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBudgetDetailDTO) throws Exception{
		
		
		
		String id = (String)request.getSession().getAttribute("memId");
		budgetDTO.setId(id);
		noBudgetDTO.setId(id);
		//System.out.println("빈에서 카테고리 넘" + request.getParameter("category_no"));
		int category_no = Integer.parseInt(request.getParameter("category_no"));
		//System.out.println("ㅅㅈ카테고리no : "+category_no);
		
		// 일단 카테고리 정보 전부다 채워서 보낸 후에 서비스에서 나눠서 처리
		budgetDTO.setCategory_no(category_no);
		noBudgetDTO.setIncome_category_no(category_no);
		noBudgetDTO.setOutcome_category_no(category_no);
		String oldDate = request.getParameter("date") + " "+ request.getParameter("time")+":00";
		Timestamp date = Timestamp.valueOf(oldDate);
		
		//System.out.println("amount : " + budgetDTO.getAmount());
		recordService.insertRecord(request, budgetDTO, budgetDetailDTO, noBudgetDTO, noBudgetDetailDTO, date);
		
		return "budget/recordPro";
	}
	//------------------------------------------------------------------------
	
	@RequestMapping("moneyRecord.moa")
	public String moneyRecord(HttpServletRequest request, Model model, SearchForRecordDTO searchForRecordDTO)throws SQLException{
	
		model.addAttribute("searchForRecordDTO", searchForRecordDTO);
		return "budget/moneyRecord";
	}
	
	// 내역 삭제
	@RequestMapping("budgetRecordDelete.moa")
	public void budgetRecordDelete(int number, String type, HttpServletResponse response) throws IOException, SQLException {
		//System.out.println("타입 : " + number);
		int result = 0;
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		// 서비스에 통으로 보내서 type으로 나눈 후  분기처리해줄 것임
		result = recordService.deleteRecord(number, type); 
		// result가 0이면 실패 1이면 성공
		if(result == 0 ) {
			response.getWriter().print(mapper.writeValueAsString("Fail"));
		}else { // 성공
			response.getWriter().print(mapper.writeValueAsString("OK"));
		}
	
	}
	// ajax 로 예산 내역 가져오기
	@RequestMapping(value="selectBudgetRecord.moa")
	public String selectBudgetRecord(SearchForRecordDTO searchForRecordDTO, HttpServletRequest request, Model model) throws Exception{
		// 처음 딱 들어올 땐 dto에 든 변수들 다 null일 것임!
		searchForRecordDTO.setId((String)request.getSession().getAttribute("memId"));
		
		Timestamp dateTime;

		// 현재 진행중인 예산의 끝나는 날짜와 지난 예산의 시작 날짜를 가져오기
		List budgetDate = budgetService.selectBudgetDate(searchForRecordDTO.getId());
		//System.out.println("seleectBudgetRecord에서  type : " + searchForRecordDTO.getType());
		Boolean result = recordService.compareDate(searchForRecordDTO, budgetDate);
		if(result){ //result 값이 참이면 해당 예산이 존재하는 것임.
			//System.out.println("검색날짜: " + searchForRecordDTO.getSearchDate());
			String newDate = searchForRecordDTO.getSearchDate() + " 00:00:00";
			dateTime = Timestamp.valueOf(newDate);
			searchForRecordDTO.setTimeStampDate(dateTime);	
			
			// 예산 번호 뽑아오기
			int budgetNum = budgetService.selectBudgetNum(searchForRecordDTO.getId(), dateTime);
			//System.out.println("컨트롤러 버겟 넘 : "+budgetNum);
			// 카테고리 번호 뽑아오기
			List categoryNums = budgetService.selectBudgetCategoryNums(budgetNum);
			// 카테고리 번호로 카테고리 이름 가져오기(hashmap으로)	
			HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);		
			// 예산번호로 예산 지출 내역 가져오기 
			RecordPageDTO recordPage = recordService.selectAllBudgetByNum(budgetNum, searchForRecordDTO.getPageNum(), searchForRecordDTO.getKeyword(), searchForRecordDTO.getSearchDate());
			String searchDate = searchForRecordDTO.getSearchDate();
			model.addAttribute("searchDate", searchDate);
			
			//System.out.println("빈에서 !! :" + recordPage.getRecordList().size());
			
			recordPage.setType(searchForRecordDTO.getType());
			// model로 다 보내주기
			model.addAttribute("keyword", searchForRecordDTO.getKeyword());
			model.addAttribute("categories", categories);
			model.addAttribute("recordPage", recordPage);	
		}
			
		return "budget/moneyLog";
	}
	
	// 예산외 
	// 아이디로 예산외 수입/지출 카테고리 정보 DTO 담은 리스트 가져오기
	@RequestMapping(value="selectNoBudgetRecord.moa")
	public String selectNoBudgetRecord(SearchForRecordDTO searchForRecordDTO, HttpServletRequest request, Model model) throws Exception{
		searchForRecordDTO.setId((String)request.getSession().getAttribute("memId"));
		//System.out.println("날짜확인! : " + searchForRecordDTO.getSearchDate());
		//System.out.println("타이입" + searchForRecordDTO.getType());
		
		// 내역 가져오기
		RecordPageDTO recordPage = recordService.selectAllNoBudget(searchForRecordDTO);
		recordPage.setType(searchForRecordDTO.getType());
		Map<Integer, String> categories = new HashMap();
		
		
		// 해당 카테고리 가져오기
		if(recordPage.getType().equals("income")){
			List incomeCategoryList= categoryService.selectAllIncomeCategoryById(searchForRecordDTO.getId());

			for(int i = 0; i < incomeCategoryList.size(); i++) {
				categories.put(((income_categoryDTO)incomeCategoryList.get(i)).getCategory_no(), ((income_categoryDTO)incomeCategoryList.get(i)).getCategory_name());
			}				
		}else if(recordPage.getType().equals("outcome")){
			List outcomeCategoryList = categoryService.selectAllById(searchForRecordDTO.getId());
			for(int i = 0; i < outcomeCategoryList.size(); i++) {
				categories.put(((outcome_categoryDTO)outcomeCategoryList.get(i)).getCategory_no(), ((outcome_categoryDTO)outcomeCategoryList.get(i)).getCategory_name());		
				
			}
		}
		
		
		model.addAttribute("searchDate", searchForRecordDTO.getSearchDate());
		model.addAttribute("keyword", searchForRecordDTO.getKeyword());
		model.addAttribute("recordPage", recordPage);
		model.addAttribute("categories", categories);
		
		return "budget/moneyLog";
	}
	// 여러개 가져올 때 
	@RequestMapping(value="selectRecords.moa")
	public String selectRecords(SearchForRecordDTO searchForRecordDTO, HttpServletRequest request, Model model)throws SQLException{
		searchForRecordDTO.setId((String)request.getSession().getAttribute("memId"));
		//System.out.println("타이입" + searchForRecordDTO.getType());
		String type = searchForRecordDTO.getType();
		
		//System.out.println("selectRecords에서 키워드 :" + searchForRecordDTO.getKeyword());
		// 타입에 들어있는 만큼 내역 가져오기
		RecordPageDTO recordPage = recordService.selectAllRecord(searchForRecordDTO);
		recordPage.setType(searchForRecordDTO.getType());
		
		// 아이디당 수입/지출 카테고리 통으로 가져오기	
		List outcomeCategoryList = categoryService.selectAllById(searchForRecordDTO.getId());
		List incomeCategoryList= categoryService.selectAllIncomeCategoryById(searchForRecordDTO.getId());
		Map<Integer, String> incomeCategories = new HashMap();

		for(int i = 0; i < incomeCategoryList.size(); i++) {
			incomeCategories.put(((income_categoryDTO)incomeCategoryList.get(i)).getCategory_no(), ((income_categoryDTO)incomeCategoryList.get(i)).getCategory_name());		
		}
		
		Map<Integer, String> outcomeCategories = new HashMap();
		for(int i = 0; i < outcomeCategoryList.size(); i++) {
			outcomeCategories.put(((outcome_categoryDTO)outcomeCategoryList.get(i)).getCategory_no(), ((outcome_categoryDTO)outcomeCategoryList.get(i)).getCategory_name());		
		}
		
		//System.out.println("타입확인 : " + type);
		recordPage.setType(type);
			
		System.out.println("recordPage 내의 Type 확인 :" + recordPage.getType());		
		model.addAttribute("keyword", searchForRecordDTO.getKeyword());
		model.addAttribute("incomeCategories", incomeCategories);
		model.addAttribute("outcomeCategories", outcomeCategories);
		model.addAttribute("recordPage", recordPage);
		return "budget/moneyLog";
	}

	// 내역 수정  , produces = "application/json;charset=utf-8"
	// MultipartHttpServletRequest request
	@ResponseBody
	@RequestMapping(value="modifyRecord.moa", method = RequestMethod.POST)  
	public void modifyRecord(RecordModifyDTO recordModifyDTO, @RequestParam("image") MultipartFile file) throws Exception{
		//System.out.println("??? : " + recordModifyDTO.getUniqueNum());
		
		//String id = (String)request.getSession().getAttribute("memId");
		//recordModifyDTO.setId(id);
		//System.out.println(request.getParameter("image"));
		
		
		//System.out.println(recordModifyDTO.toString());
		
		recordService.modifyRecord(recordModifyDTO, file);
		//System.out.println("파일 : "+ file);
		
		// mapper 사용해서 map -> json string으로 변환해서 리턴해주기 
		/*
		ObjectMapper mapper = new ObjectMapper();
		String json2 = "";
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		*/
		// json2 = mapper.writeValueAsString(map2);
		
	}
	
}
