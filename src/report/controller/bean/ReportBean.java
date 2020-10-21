package report.controller.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import budget.model.dto.TotalBudgetDTO;
import budget.service.bean.BudgetService;
import report.service.bean.ReportService;

@Controller
@RequestMapping("/report/")
public class ReportBean {
	
	@Autowired
	private ReportService reportService = null;
	@Autowired
	private BudgetService budgetService = null;
	
	
	@RequestMapping("report.moa")
	public String LCreport(HttpServletRequest request, Model model) throws SQLException {
		String id = (String) request.getSession().getAttribute("memId");
		//모든 예산정보를 날짜 내림차순으로 정렬해서 가져오기
		List TBList = reportService.selectAllOrderByReg(id);
		
		model.addAttribute("TBList", TBList);
		
		return "report/report";
	}
	
	@RequestMapping("reportContent.moa")
	public String reportContent(String id, int budget_no, Model model) throws SQLException {
		TotalBudgetDTO TBdto = null;
		if(budget_no == -1) {	
//			TBdto = budgetService.selectLastTB(id); //가장 최근 총예산정보 (close=1)
			TBdto = budgetService.selectCurrentOne(id); //현재 총예산정보 (close=0)
		} else {
			TBdto = budgetService.selectOneByNum(budget_no);
		}
		model.addAttribute("TBdto",TBdto);	//총 예산 정보 전달
		
		HashMap returnMap = reportService.selectLabelDataList(TBdto);
		
		Set keySet = returnMap.keySet();
		Iterator it = keySet.iterator();
		
		while(it.hasNext()) {
			String key = (String)it.next();
			model.addAttribute(key, returnMap.get(key));
		}
		
		return "report/reportContent";
	}
	
	@RequestMapping("getBudgetNum.moa")
	@ResponseBody
	public int getBudgetNum(String date, String id) throws SQLException {
		Timestamp dateTime = Timestamp.valueOf(date + " 00:00:00");
		// 예산 번호 뽑아오기
		int budgetNum = budgetService.selectBudgetNum(id, dateTime);
		return budgetNum;
	}
	
	@RequestMapping("expectation.moa")
	public String expectation(HttpServletRequest request, Model model) throws SQLException {
		//추정치를 도출할 수 있는지
		String id = (String) request.getSession().getAttribute("memId");
		int reject = reportService.checkBeforeExpectation(id);
		model.addAttribute("reject", reject);
		
		if(reject == 1) {	//추정불가면 바로 리턴
			return "report/expectation";
		} else {	//추정가능
			
			HashMap expectation = reportService.expectation(id);
			model.addAttribute("expectation", expectation);
			
			
			
			
			
			return "report/expectation";
		}
		
	}
}
