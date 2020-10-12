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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
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
		
		// 현재 날짜+시간 받아오기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currTime = new Date();
		String now = sdf.format(currTime);
		System.out.println(now);
		Timestamp dateTime = Timestamp.valueOf(now);
		// 예산 번호 뽑아오기
		int budgetNum = budgetService.selectBudgetNum("test50", dateTime);
		
		// 카테고리 번호 뽑아오기
		List categoryNums = budgetService.selectBudgetCategoryNums(budgetNum);
		
		// 카테고리 번호로 카테고리 이름 가져오기(hashmap으로)
		HashMap categories = categoryService.selectBudgetCategoryNames(categoryNums);
		
		//String id = (String)request.getSession().getAttribute("memId");
		// 일단 test50 값 임의로 넣어줘서 처리
		List outcomeCategories = categoryService.selectAllById("test50");
		List incomeCategories = categoryService.selectAllIncomeCategoryById("test50");
		
		System.out.println("예산 카테고리 :" + categories.size());
		
		model.addAttribute("outcomeCategories", outcomeCategories);
		model.addAttribute("incomeCategories", incomeCategories);
		model.addAttribute("categories", categories);
		
		return "budget/recordForm";
	}
	
	// ajax로 회원 예산 카테고리 가져오기
	@RequestMapping(value="budgetCategory.moa", method= {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map budgetCategory(HttpServletRequest request, String date) throws SQLException{
		//String id = request.getParameter("memId");
		// budgetdetail 테이블에 있는 예산 번호 가져와야함 
		
		
		// string으로 넘어온 날짜에 시간 임의로 넣어서 timeStamp로 형변환
		String newDate = date + " 00:00:00";
		Timestamp dateTime = Timestamp.valueOf(newDate);
		
		// 예산 번호 뽑기 전 아이디로 예산들 가져와서 날짜랑 비교 후에 예산 뽑아야함!!
		boolean result = budgetService.checkDate(date, "test50");
		
		
		// 예산 번호 뽑아오기
		int budgetNum = budgetService.selectBudgetNum("test50", dateTime);
		
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
		/*
		System.out.println("빈임빈임빈임~~");
		System.out.println("category  :" +request.getParameter("category"));
		System.out.println("내역 : "  + request.getParameter("subject"));
		System.out.println("금액 : " + request.getParameter("money"));
		System.out.println("날짜 : " + request.getParameter("date"));
		System.out.println("시간 : " + request.getParameter("time"));
		System.out.println("메모 : " + request.getParameter("memo"));
		System.out.println("뚜두두두두두두둥!!타입 나와라!!!!: " + request.getParameter("type"));
		*/
		
		/*
		String id = (String)request.getSession().getAttribute("memId");
		budgetDTO.setId(id);
		noBudgetDTO.setId(id);
		일단 test50으로 해봄
		*/
		budgetDTO.setId("test50");
		noBudgetDTO.setId("test50");
		System.out.println(request.getParameter("category_no"));
		int category_no = Integer.parseInt(request.getParameter("category_no"));
		budgetDTO.setCategory_no(category_no);
		noBudgetDTO.setCategory_no(category_no);
		
		String oldDate = request.getParameter("reg") + " "+ request.getParameter("time")+":00";
		System.out.println(oldDate);
		Timestamp date = Timestamp.valueOf(oldDate);
		System.out.println(date);
		
		recordService.insertRecord(request, budgetDTO, budgetDetailDTO, noBudgetDTO, noBudgetDetailDTO, date);
		
		return "budget/moneyLog";
	}
	

}
