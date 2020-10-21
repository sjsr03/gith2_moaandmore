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

import budget.model.dao.RecordBudgetDAO;
import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.TotalBudgetDTO;
import budget.model.dto.TotalBudgetDetailDTO;
import budget.service.bean.BudgetService;
import budget.service.bean.RecordService;
import category.service.bean.CategoryService;
import report.service.bean.ReportService;

@Controller
@RequestMapping("/report/")
public class ReportBean {
	
	@Autowired
	private ReportService reportService = null;
	@Autowired
	private BudgetService budgetService = null;
	@Autowired
	private RecordService recordService = null;
	@Autowired
	private CategoryService categoryService = null;
	@Autowired
	private RecordBudgetDAO recordBudgetDAO = null;
	
	
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
	
	@RequestMapping("recordMacroForm.moa")
	public String recordMacroForm() {
		return "report/recordMacro";
	}
	
	@RequestMapping("recordMacro.moa")
	public String recordMacro(String id, int count, String StartDay, String EndDay, String type) throws SQLException, InterruptedException {
		
		List categoryList = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(StartDay);
		
		Date startDay = new Date(Timestamp.valueOf(StartDay).getTime());
		Date endDay = new Date(Timestamp.valueOf(EndDay).getTime());
		
		
		
		
		if (type.equals("budget")) {
			while(startDay.before(endDay)) {
				
				Timestamp startTS = Timestamp.valueOf(sdf.format(startDay));
				// 예산 번호 뽑아오기
				int budgetNum = budgetService.selectBudgetNum(id, startTS);
				// 카테고리 번호 뽑아오기
				categoryList = budgetService.selectBudgetCategoryNums(budgetNum);
				
				for (int i = 0; i < count; i++) {
					BudgetDTO budgetDTO = new BudgetDTO();
					int random = (int)((Math.random()*(categoryList.size()-1)));
//					int category_no = ((TotalBudgetDetailDTO)categoryList.get(random)).getCategory_no();
					int category_no = (int)categoryList.get(random);
					Timestamp date = Timestamp.valueOf(sdf.format(new Date(startDay.getTime()+((long)(Math.round((1000*60*60*24)*Math.random()))))));
					budgetDTO.setReg(date);
					budgetDTO.setAmount((int)(Math.random()*10000) + 100);
					budgetDTO.setBudget_no(budgetNum);
					budgetDTO.setCategory_no(category_no);
					budgetDTO.setId(id);
					

					
					// 예산 내역 insert해준 후  구분번호 예산세부내역dto에 다시 세팅해주기 
					recordBudgetDAO.insertBudget(budgetDTO);
					int budget_outcome_no = budgetDTO.getBudget_outcome_no();
					
					BudgetDetailDTO budgetDetailDTO = new BudgetDetailDTO();
					budgetDetailDTO.setContent("랜덤" + (int)Math.random()*10);
					budgetDetailDTO.setBudget_outcome_no(budget_outcome_no);
					budgetDetailDTO.setImg("");
					budgetDetailDTO.setMemo("랜덤값");
					
					recordBudgetDAO.insertBudgetDetail(budgetDetailDTO);		
					
				}
				
				startDay.setDate(startDay.getDate()+1);
			}
			
		} else {
			if (type.equals("income")) {
				categoryList = categoryService.selectAllIncomeCategoryById(id);
			}else if (type.equals("outcome")) {
				categoryList = categoryService.selectAllById(id);
			}
			
			
			
		
		}
		return "끝";
	}
}
