package calendar.service.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

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
			
			Date indata =budgetList.getDate();
			String budgetDate = yyyymmdd.format(indata);
			
		
			if(!budgetalldate.contains(budgetDate)){
		       budgetalldate.add(budgetDate);
			}
			
		}
	
		//System.out.println(budgetalldate);
		return budgetalldate;
	}


	@Override
	public List selectBudgetAmount(String id, List budgetAlldate) throws SQLException {
		
		//budget 테이블 지출 총금액
		List AllBudgetamount = calendarDAO.selectBudgetAmount(id,budgetAlldate);
		
		
		return AllBudgetamount;
		
		
	}


	//budget 테이블 날짜랑 지출총액 맵으로 같이 보내줄 메서드 
	@Override
	public Map selectBudgetDateAndAmount(String id) throws SQLException {
		
				//id로 budget테이블에서 데이터가 있는 날짜 가져오기
				List<String> budgetAlldate = calendarService.selectBudgetDatebyId(id);
				//id랑 날짜(list)로 날짜에 해당하는 총 지출액 합계 가져오기
				List<Integer> allbudgetAmount = calendarService.selectBudgetAmount(id,budgetAlldate);
			
				//리턴해줄 날짜랑 지출액
				Map selectBudgetDateAndAmount = new HashMap();
				
				for(int i = 0; i<budgetAlldate.size(); i++) {
					selectBudgetDateAndAmount.put(budgetAlldate.get(i), allbudgetAmount.get(i));
				}				
				
		return selectBudgetDateAndAmount;
	}

	//nobudget테이블에서 데이터가 있는 날짜 가져오기
	@Override
	public List selectNobudgetDatebyId(String id) throws SQLException {
	
		SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		
		List noBudget = calendarDAO.selectNoBudgetDatebyId(id);
		
		//return 해줄 중복값 제거된 budget 테이블 총 날짜
		List noBudgetalldate = new ArrayList();
		
		for(int i=0; i<noBudget.size(); i++) {
			NoBudgetDTO noBudgetList = (NoBudgetDTO)noBudget.get(i);
			
			Date indata =noBudgetList.getDate();
			String noBudgetDate = yyyymmdd.format(indata);
			
			if(!noBudgetalldate.contains(noBudgetDate)){
		       noBudgetalldate.add(noBudgetDate);
			}
			
		}
	
		System.out.println(noBudgetalldate);
		return noBudgetalldate;
		
	}

	//nobudget테이블에서 예산외 총 지출액 가져오기
	@Override
	public List selectNobudgetExpenseAmount(String id, List nobudgetAlldate) throws SQLException {

		//nobudget 테이블 지출 총금액
		List allNoBudgetExpenseAmount = calendarDAO.selectNoBudgetExpenseAmount(id,nobudgetAlldate);
				
				
		return allNoBudgetExpenseAmount;
		
	}

	//nobudget테이블에서 예산외 총 수입액 가져오기
	@Override
	public List selectNobudgetIncomeAmount(String id, List nobudgetAlldate) throws SQLException {

		//nobudget 테이블 수입 총금액
		List allBudgetamount = calendarDAO.selectNoBudgetIncomeAmount(id,nobudgetAlldate);
						
						
		return allBudgetamount;
	}

	//nobudget 테이블 날짜랑 지출총액 맵으로 같이 보내줄 메서드 
	@Override
	public Map selectNobudgetExpenseDateAndAmount(String id) throws SQLException {
		
		//id로 nobudget테이블에서 데이터가 있는 날짜 가져오기
		List<String> noBudgetAlldate = calendarService.selectNobudgetDatebyId(id);
		//id랑 날짜(list)로 날짜에 해당하는 총 지출액 합계 가져오기
		List<Integer> allnoBudgetExpenseAmount = calendarService.selectNobudgetExpenseAmount(id, noBudgetAlldate);
	
		//리턴해줄 날짜랑 지출액
		Map selectNoBudgetDateAndAmount = new HashMap();
		
		for(int i = 0; i<noBudgetAlldate.size(); i++) {
			selectNoBudgetDateAndAmount.put(noBudgetAlldate.get(i), allnoBudgetExpenseAmount.get(i));
		}				
		
		return selectNoBudgetDateAndAmount;
	}

	//nobudget 테이블 날짜랑 수입총액 맵으로 같이 보내줄 메서드 
	@Override
	public Map selectNobudgetIncomeDateAndAmount(String id) throws SQLException {
		
		//id로 nobudget테이블에서 데이터가 있는 날짜 가져오기
		List<String> noBudgetAlldate = calendarService.selectNobudgetDatebyId(id);
		//id랑 날짜(list)로 날짜에 해당하는 총 지출액 합계 가져오기
		List<Integer> allnoBudgetAmount = calendarService.selectNobudgetExpenseAmount(id, noBudgetAlldate);
	
		//리턴해줄 날짜랑 지출액
		Map selectNoBudgetDateAndAmount = new HashMap();
		
		for(int i = 0; i<noBudgetAlldate.size(); i++) {
			selectNoBudgetDateAndAmount.put(noBudgetAlldate.get(i), allnoBudgetAmount.get(i));
		}				
		
		return selectNoBudgetDateAndAmount;
	}
	
	

	


	
	
	
}
