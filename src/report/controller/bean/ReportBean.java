package report.controller.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
		model.addAttribute("TBdto",TBdto);
		
		//예산 시작일~종료일 배열로
		Date day = new Date(TBdto.getStart_day().getTime());
		Date endDay = new Date(TBdto.getEnd_day().getTime());
		System.out.println("startDay : " + day + " / endDay : " + endDay);
//		List tmpList = new ArrayList();
//		while(day.before(endDay)) {
//			tmpList.add(day.getMonth()+1 + "월 " + day.getDate() + "일");
//			day.setDate(day.getDate()+1);
//		}
//		String[] labelList = (String[]) tmpList.toArray(new String[tmpList.size()]);
		
		String labelList = "[";
		while(day.before(endDay)) {
			String tmp = "\"" + (day.getMonth()+1) + "월 " + day.getDate() + "일" + "\", ";
			labelList += tmp;
			day.setDate(day.getDate()+1);
		}
		labelList.substring(0, labelList.length()-2);
		labelList += "]";
		
//		
//		int[] dataList = new int[labelList.length];
//		for (int i = 0; i < labelList.length; i++) {
//			dataList[i] = i;
//		}
		model.addAttribute("labelList", labelList);
//		model.addAttribute("dataList", dataList);
		
		return "report/reportContent";
	}
}
