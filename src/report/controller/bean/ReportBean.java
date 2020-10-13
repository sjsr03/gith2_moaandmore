package report.controller.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	public String report(HttpServletRequest request, Model model) throws SQLException {
		String id = (String) request.getSession().getAttribute("memId");
		//모든 예산정보를 날짜 내림차순으로 정렬해서 가져오기
		List TBList = reportService.selectAllOrderByReg(id);
		
		model.addAttribute("TBList", TBList);
		
		return "report/report";
	}
	
	@RequestMapping("reportContent.moa")
	public String reportContent(String id, int budget_no, Model model) throws SQLException {
		TotalBudgetDTO TBdto = null;
		if(budget_no == -1) {	//가장 최근 총예산정보 (close=1)
			TBdto = budgetService.selectLastTB(id);
		} else {
			TBdto = budgetService.selectOneByNum(budget_no);
		}
		model.addAttribute("TBdto",TBdto);	//총 예산 정보 전달
		
		HashMap returnMap = reportService.selectLabelDataList(TBdto);
		
		model.addAttribute("labelList", returnMap.get("labelList"));
		model.addAttribute("dataList", returnMap.get("dataList"));	//그래프용 데이터 전달
		
		
		
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
}
