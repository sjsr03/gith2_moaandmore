package budget.controller.bean;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import budget.model.dao.RecordBudgetDAO;
import budget.model.dao.RecordBudgetDAOImpl;
import budget.model.dao.RecordNoBudgetDAO;
import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.RecordPageDTO;
import budget.model.dto.SearchForRecordDTO;
import budget.service.bean.BudgetService;
import budget.service.bean.RecordService;
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
		System.out.println("today >>>" + today);
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
		System.out.println("레코드빈 버짓넘~~ : " + budgetNum);
		return "budget/recordForm";
	}
	
	// ajax로 회원 예산 카테고리 가져오기
	@RequestMapping(value="budgetCategory.moa", method= {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map budgetCategory(HttpServletRequest request, String date, String id) throws SQLException{
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
	
		int category_no = Integer.parseInt(request.getParameter("category_no"));
		budgetDTO.setCategory_no(category_no);
		noBudgetDTO.setCategory_no(category_no);
		
		String oldDate = request.getParameter("date") + " "+ request.getParameter("time")+":00";
		Timestamp date = Timestamp.valueOf(oldDate);
		
		
		recordService.insertRecord(request, budgetDTO, budgetDetailDTO, noBudgetDTO, noBudgetDetailDTO, date);
		
		return "budget/moneyLog";
	}
	
	// 수입지출목록 보여주기
	@RequestMapping("moneyLog.moa")
	public String moneyLog(HttpServletRequest request, Model model, String pageNum)throws SQLException {
		String id = (String)request.getSession().getAttribute("memId");
		
		// 현재 날짜+시간 받아오기(기본으로 현재 진행중인 예산의 지출 목록 보여줄 것임)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currTime = new Date();
		String now = sdf.format(currTime);
		Timestamp dateTime = Timestamp.valueOf(now);
		
		// 날짜랑 아이디로 해당 예산 번호 가져오기
		int budgetNum = budgetService.selectBudgetNum(id, dateTime);
		RecordPageDTO recordPage = recordService.selectAllBudgetByNum(budgetNum, pageNum);

		model.addAttribute("recordPage", recordPage);
		return "budget/moneyLog";
	}
	
	@RequestMapping("moneyRecord.moa")
	public String moneyRecord(HttpServletRequest request, Model model, SearchForRecordDTO searchForRecordDTO)throws SQLException{
		// 처음 딱 들어올 땐 dto에 든 변수들 다 null일 것임!
		searchForRecordDTO.setId((String)request.getSession().getAttribute("memId"));
		System.out.println("아이디?11 : " + searchForRecordDTO.getId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currTime = new Date();
		String now = sdf.format(currTime);
		System.out.println(now);
		Timestamp dateTime = Timestamp.valueOf(now);
		
		searchForRecordDTO.setTimeStampDate(dateTime);
		// 날짜랑 아이디로 해당 예산 번호 가져오기
		int budgetNum = budgetService.selectBudgetNum(searchForRecordDTO.getId(), dateTime);
		RecordPageDTO recordPage = recordService.selectAllBudgetByNum(budgetNum, searchForRecordDTO.getPageNum());
		
		// 예산 번호로 카테고리 번호, 이름 뽑아오기 
		// 카테고리 번호 뽑아오기
		List categoryNums = budgetService.selectBudgetCategoryNums(budgetNum);
		
		// 카테고리 번호로 카테고리 이름 가져오기(hashmap으로)	
		HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);
	
		
		List outcomeCategories = categoryService.selectAllById(searchForRecordDTO.getId());
		List incomeCategories = categoryService.selectAllIncomeCategoryById(searchForRecordDTO.getId());
			
		model.addAttribute("recordPage", recordPage);
		model.addAttribute("categories", categories);
		return "budget/moneyRecord";
	}
	
	// 예산 내역 삭제
	@RequestMapping("budgetRecordDelete.moa")
	public void budgetRecordDelete(String budget_outcome_no, HttpServletResponse response) throws IOException, SQLException {
		System.out.println("타입 : " + budget_outcome_no);
		int result = 0;
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		result = recordService.budgetRecordDelete(budget_outcome_no);
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
		System.out.println("날짜확인하셈! : " + searchForRecordDTO.getSerachDate());
		Timestamp dateTime;

		// 현재 진행중인 예산의 끝나는 날짜와 지난 예산의 시작 날짜를 가져오기
		List budgetDate = budgetService.selectBudgetDate(searchForRecordDTO.getId());
		
		Boolean result = recordService.compareDate(searchForRecordDTO, budgetDate);
		if(result){ //result 값이 참이면 해당 예산이 존재하는 것임.
			System.out.println("검색날짜 ::: " + searchForRecordDTO.getSerachDate());
			String newDate = searchForRecordDTO.getSerachDate() + " 00:00:00";
			dateTime = Timestamp.valueOf(newDate);
			searchForRecordDTO.setTimeStampDate(dateTime);
			
			// 예산 번호 뽑아오기
			int budgetNum = budgetService.selectBudgetNum(searchForRecordDTO.getId(), dateTime);
			System.out.println("컨트롤러 버겟 넘 : "+budgetNum);
			// 카테고리 번호 뽑아오기
			List categoryNums = budgetService.selectBudgetCategoryNums(budgetNum);
			
			// 카테고리 번호로 카테고리 이름 가져오기(hashmap으로)	
			HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);
			
			// 예산번호로 예산 지출 내역 가져오기 
			RecordPageDTO recordPage = recordService.selectAllBudgetByNum(budgetNum, searchForRecordDTO.getPageNum());
		
			// model로 다 보내주기
			model.addAttribute("categories", categories);
			model.addAttribute("recordPage", recordPage);
		
		}else{ // 해당 예산이 없으면  가져올 내역이 없다는 말임 없으면..뭘보내주지;?
		}
		
		return "budget/moneyLog";
	}
	
	// 예산외
	// 아이디랑 pageNum, type, 시작날짜 끝나는날짜로 가져오기.................
	// 아이디로 예산외 수입/지출 카테고리 정보 DTO 담은 리스트 가져오기
	@RequestMapping(value="selectNoBudgetRecord.moa")
	public String selectNoBudgetRecord(SearchForRecordDTO searchForRecordDTO, HttpServletRequest request, Model model) throws Exception{
		searchForRecordDTO.setId((String)request.getSession().getAttribute("memId"));
		System.out.println("날짜확인하셈! : " + searchForRecordDTO.getSerachDate());
		System.out.println("타이입" + searchForRecordDTO.getType());
		
		RecordPageDTO recordPage = recordService.selectAllNoBudget(searchForRecordDTO);
		model.addAttribute("recordPage", recordPage);
		return "budget/moneyLog";
	}
	
	
	
}
