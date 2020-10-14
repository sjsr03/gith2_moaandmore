package budget.controller.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import budget.model.dao.RecordBudgetDAO;
import budget.model.dao.RecordBudgetDAOImpl;
import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.RecordPageDTO;
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

	
	@RequestMapping("recordForm.moa")
	public String recordForm(HttpServletRequest request, Model model)throws SQLException {
		// 여기에서 해당 회원의 카테고리까지 한번에 다 가져올 것임
		// 가져와서 model로 다 뿌려주기!!! (수입, 지출, 예산지출 list에 담아서)
		String id = (String)request.getSession().getAttribute("memId");
		
		
		// 현재 날짜+시간 받아오기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currTime = new Date();
		String now = sdf.format(currTime);
		System.out.println(now);
		Timestamp dateTime = Timestamp.valueOf(now);
		// 예산 번호 뽑아오기
		int budgetNum = budgetService.selectBudgetNum(id, dateTime);
		
		// 카테고리 번호 뽑아오기
		List categoryNums = budgetService.selectBudgetCategoryNums(budgetNum);
		
		// 카테고리 번호로 카테고리 이름 가져오기(hashmap으로)
		HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);
		
		List outcomeCategories = categoryService.selectAllById(id);
		List incomeCategories = categoryService.selectAllIncomeCategoryById(id);
		
		System.out.println("예산 카테고리 :" + categories.size());
		
		
		// 현재 진행중인 예산의 끝나는 날짜와 지난 예산의 시작 날짜를 가져오기
		List budgetDate = budgetService.selectBudgetDate(id);
		System.out.println(budgetDate.size());
		System.out.println("리스트리스트 날짜리스트 " + budgetDate.get(0) + "," + budgetDate.get(1));
		model.addAttribute("budgetDate", budgetDate);
		model.addAttribute("outcomeCategories", outcomeCategories);
		model.addAttribute("incomeCategories", incomeCategories);
		model.addAttribute("categories", categories);
		model.addAttribute("id", id);
		return "budget/recordForm";
	}
	
	// ajax로 회원 예산 카테고리 가져오기
	@RequestMapping(value="budgetCategory.moa", method= {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map budgetCategory(HttpServletRequest request, String date, String id) throws SQLException{
		// budgetdetail 테이블에 있는 예산 번호 가져와야함 
		
		// string으로 넘어온 날짜에 시간 임의로 넣어서 timeStamp로 형변환
		String newDate = date + " 00:00:00";
		Timestamp dateTime = Timestamp.valueOf(newDate);
		System.out.println("컨트롤러에서 id" + id);
		// 예산 번호 뽑아오기
		int budgetNum = budgetService.selectBudgetNum(id, dateTime);
		System.out.println("컨트롤러 버겟 넘 : "+budgetNum);
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

		System.out.println("날짜~" + budgetDTO.getReg());
		String id = (String)request.getSession().getAttribute("memId");
		budgetDTO.setId(id);
		noBudgetDTO.setId(id);
	
		System.out.println(request.getParameter("category_no"));
		int category_no = Integer.parseInt(request.getParameter("category_no"));
		budgetDTO.setCategory_no(category_no);
		noBudgetDTO.setCategory_no(category_no);
		
		String oldDate = request.getParameter("date") + " "+ request.getParameter("time")+":00";
		System.out.println(oldDate);
		Timestamp date = Timestamp.valueOf(oldDate);
		
		System.out.println("버겟 넘버!!! : " + request.getParameter("budget_no"));
		
		recordService.insertRecord(request, budgetDTO, budgetDetailDTO, noBudgetDTO, noBudgetDetailDTO, date);
		
		return "budget/moneyLog";
	}
	
	// 수입지출목록 보여주기
	@RequestMapping("moneyLog.moa")
	public String moneyLog(HttpServletRequest request, Model model, String pageNum)throws SQLException {
		String id = (String)request.getSession().getAttribute("memId");
		System.out.println("아이디? : " + id);
		
		// 현재 날짜+시간 받아오기(기본으로 현재 진행중인 예산의 지출 목록 보여줄 것임)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currTime = new Date();
		String now = sdf.format(currTime);
		System.out.println(now);
		Timestamp dateTime = Timestamp.valueOf(now);
		
		// 날짜랑 아이디로 해당 예산 번호 가져오기
		int budgetNum = budgetService.selectBudgetNum(id, dateTime);
		System.out.println("예산번호는??? : " + budgetNum);
		RecordPageDTO recordPage = recordService.selectAllBudgetByNum(budgetNum, pageNum);
		
		System.out.println("잘 나오니? : " +  recordPage.getRecordList().size());
		model.addAttribute("recordPage", recordPage);
		return "budget/moneyLog";
	}

}
