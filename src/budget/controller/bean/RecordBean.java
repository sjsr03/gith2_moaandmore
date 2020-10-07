package budget.controller.bean;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dto.NoBudgetDTO;
import budget.service.bean.RecordService;
import category.service.bean.CategoryService;


@Controller
@RequestMapping("/record/")
public class RecordBean {

	@Autowired
	private RecordService recordService = null;
	
	@Autowired
	private CategoryService categoryService = null;
	
	@RequestMapping("recordForm.moa")
	public String recordForm(HttpServletRequest request, Model model)throws SQLException {
		// 여기에서 해당 회원의 카테고리까지 한번에 다 가져올 것임
		// 가져와서 model로 다 뿌려주기!!! (수입, 지출, 예산지출 list에 담아서)
		
		//String id = (String)request.getSession().getAttribute("memId");
		// 일단 test50 값 임의로 넣어줘서 처리
		List outcomeCategories = categoryService.selectAllById("test50");
		model.addAttribute("outcomeCategories", outcomeCategories);
		
		return "budget/recordForm";
	}
	
	@RequestMapping(value="recordPro.moa", method=RequestMethod.POST)
	public String recordPro(MultipartHttpServletRequest request) throws Exception{
		System.out.println("category 나오니? :" +request.getParameter("category"));
		System.out.println("내역 : "  + request.getParameter("subject"));
		System.out.println("금액 : " + request.getParameter("money"));
		System.out.println("날짜 : " + request.getParameter("date"));
		System.out.println("시간 : " + request.getParameter("time"));
		System.out.println("메모 : " + request.getParameter("memo"));
		//System.out.println("내역 : " + request.getParameter("time"));
		
		// 여기서 예산외인지 내인지 체크해서 dto에 값 일일이 넣어주기(dto 통으로 못받아줌)
		// 일단 에산 외부터 ㄱㄱ
		/*
		NoBudgetDTO nobuget = new NoBudgetDTO();
		nobuget.setNobudget_no(request.getParameter(""));
		nobuget.setAmount(amount);
		nobuget.setCategory_no(category_no);
		nobuget.setDate(date);
		nobuget.setType(type);
		nobuget.setId(id);
		*/
		return "budget/moneyLog";
	}
	

}
