package calendar.service.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import budget.model.dto.AllRecordDTO;
import budget.model.dto.BudgetDTO;
import budget.model.dto.NoBudgetDTO;
import calendar.model.dao.CalendarDAO;



public class CalendarServiceImpl implements CalendarService{

	
	@Autowired
	private CalendarDAO calendarDAO = null;

	@Autowired
	private CalendarService calendarService = null;
	
	
	@Override
	public List selectBudgetDatebyId(String id) throws SQLException {
		
		SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		
		List budget = calendarDAO.selectBudgetDatebyId(id);
		
		//return 해줄 중복값 제거된 budget 테이블 총 날짜
		List budgetalldate = new ArrayList();
		
		

			for(int i=0; i<budget.size(); i++) {
				BudgetDTO budgetList = (BudgetDTO)budget.get(i);
				
				Date indata =budgetList.getReg();
				
				String budgetDate = yyyymmdd.format(indata);
				
				if(!budgetalldate.contains(budgetDate)){
			       budgetalldate.add(budgetDate);
				}
				

			}

			
		return budgetalldate;
	}

	


	//budget 테이블 날짜랑 지출총액 맵으로 같이 보내줄 메서드 
	@Override
	public Map selectBudgetDateAndAmount(String id) throws SQLException {
		
			
		//id로 budget테이블에서 데이터가 있는 날짜 가져오기
		List<String> budgetAlldate = calendarService.selectBudgetDatebyId(id);
		//id랑 날짜(list)로 날짜에 해당하는 총 지출액 합계 가져오기
		List<Integer> allbudgetAmount = calendarDAO.selectBudgetAmount(id,budgetAlldate);
	
		//리턴해줄 날짜랑 지출액
		Map selectBudgetDateAndAmount = new HashMap();
		
		for(int i = 0; i<budgetAlldate.size(); i++) {
			selectBudgetDateAndAmount.put(budgetAlldate.get(i), allbudgetAmount.get(i));
		}				
		
		return selectBudgetDateAndAmount;
	}

	//nobudget테이블에서 지출 데이터가 있는 날짜 가져오기
	@Override
	public List selectNobudgetExpenseDatebyId(String id) throws SQLException {
	
		SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		
		List noBudgetExpense = calendarDAO.selectNoBudgetExpenseDatebyId(id);
		
		//return 해줄 중복값 제거된 budget 테이블 총 날짜
		List noBudgetExpensealldate = new ArrayList();
		
		for(int i=0; i<noBudgetExpense.size(); i++) {
			NoBudgetDTO noBudgetList = (NoBudgetDTO)noBudgetExpense.get(i);
			Date indata =noBudgetList.getReg();
			
			String noBudgetDate = yyyymmdd.format(indata);
			
			if(!noBudgetExpensealldate.contains(noBudgetDate)){
				noBudgetExpensealldate.add(noBudgetDate);
			}
			
		}

		return noBudgetExpensealldate;
		
	}

	@Override
	public List selectNobudgetIncomeDatebyId(String id) throws SQLException {
	
		SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		
		List noBudget = calendarDAO.selectNoBudgetIncomeDatebyId(id);
		
		//return 해줄 중복값 제거된 budget 테이블 총 날짜
		List noBudgeIncometalldate = new ArrayList();
		
		for(int i=0; i<noBudget.size(); i++) {
			NoBudgetDTO noBudgetList = (NoBudgetDTO)noBudget.get(i);
			Date indata =noBudgetList.getReg();
			
			String noBudgetDate = yyyymmdd.format(indata);
			
			if(!noBudgeIncometalldate.contains(noBudgetDate)){
				noBudgeIncometalldate.add(noBudgetDate);
			}
			
		}

		return noBudgeIncometalldate;
		
	}
	

	//nobudget 테이블 날짜랑 지출총액 맵으로 같이 보내줄 메서드 
	@Override
	public Map selectNobudgetExpenseDateAndAmount(String id) throws SQLException {
		
		//id로 nobudget테이블에서 데이터가 있는 날짜 가져오기
		List<String> noBudgetAlldate = calendarService.selectNobudgetExpenseDatebyId(id);
		//id랑 날짜(list)로 날짜에 해당하는 총 지출액 합계 가져오기
		List<Integer> allnoBudgetExpenseAmount = calendarDAO.selectNoBudgetExpenseAmount(id, noBudgetAlldate);
	
		//리턴해줄 날짜랑 지출액
		Map noBudgetExpenseDateAndAmount = new HashMap();
		
		for(int i = 0; i<noBudgetAlldate.size(); i++) {
			noBudgetExpenseDateAndAmount.put(noBudgetAlldate.get(i), allnoBudgetExpenseAmount.get(i));
		}				
		
		return noBudgetExpenseDateAndAmount;
	}

	//nobudget 테이블 날짜랑 수입총액 맵으로 같이 보내줄 메서드 
	@Override
	public Map selectNobudgetIncomeDateAndAmount(String id) throws SQLException {
		
		//id로 nobudget테이블에서 데이터가 있는 날짜 가져오기
		List<String> noBudgetAlldate = calendarService.selectNobudgetIncomeDatebyId(id);
		//id랑 날짜(list)로 날짜에 해당하는 총 지출액 합계 가져오기
		List<Integer> allnoBudgetAmount = calendarDAO.selectNoBudgetIncomeAmount(id, noBudgetAlldate);
		
		//리턴해줄 날짜랑 지출액
		Map noBudgetIncomeDateAndAmount = new HashMap();
		
		for(int i = 0; i<noBudgetAlldate.size(); i++) {
			noBudgetIncomeDateAndAmount.put(noBudgetAlldate.get(i), allnoBudgetAmount.get(i));
		}				
		
		return noBudgetIncomeDateAndAmount;
	}



	@Override
	public List getAlldata(String date) throws SQLException {

		String id=(String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
		List alldata = new ArrayList();
		List budgetDetail = calendarDAO.getBudgetDetail(id,date);
		List budgetList = new ArrayList();

		
		for(int i = 0; i<budgetDetail.size(); i++) {
			AllRecordDTO budget = (AllRecordDTO)budgetDetail.get(i);
			 
			
			budgetList.add("지출");
			budgetList.add(budget.getAmount());
			budgetList.add(budget.getContent());
			budgetList.add(budget.getMemo());
			
			alldata.add("지출");
			alldata.add(budget.getAmount());
			alldata.add(budget.getContent());
			alldata.add(budget.getMemo());
			
			
			
		
		}
		System.out.println("budgetList"+budgetList);
		
		
		List nobudgetExpenseDetail= calendarDAO.getNobudgetExpenseDetail(id,date);
		
		List nobudgetExpenseList = new ArrayList();
			for(int i=0; i<nobudgetExpenseDetail.size(); i++) {
				AllRecordDTO nobudget = (AllRecordDTO)nobudgetExpenseDetail.get(i);
				nobudgetExpenseList.add("지출");
				nobudgetExpenseList.add(nobudget.getAmount());
				nobudgetExpenseList.add(nobudget.getContent());
				nobudgetExpenseList.add(nobudget.getMemo());
			
				alldata.add("지출");
				alldata.add(nobudget.getAmount());
				alldata.add(nobudget.getContent());
				alldata.add(nobudget.getMemo());
			
				
				
			}
			
		System.out.println("nobudgetExpenseList"+nobudgetExpenseList);
		//if(budgetList.size()>0) {alldata.add(budgetList);}
		//if(nobudgetExpenseList.size()>0) {alldata.add(nobudgetExpenseList);}
	
		
			
			
		calendarDAO.getNobudgetIncomeDetail(id,date);
		
		
		
		System.out.println("alldata"+alldata);
		
		return alldata;
	}


	


	
	
	

	


	
	
	
}
