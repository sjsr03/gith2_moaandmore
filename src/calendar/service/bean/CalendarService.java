package calendar.service.bean;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import budget.model.dto.BudgetDTO;
import calendar.model.dto.CalendarDTO;

public interface CalendarService {

	//id로 budget테이블에서 데이터가 있는 날짜 가져오기
	public List selectBudgetDatebyId(String id) throws SQLException;
	
	//id로 budget테이블에서 총 지출액 가져오기
	public List selectBudgetAmount(String id,List budgetAlldate) throws SQLException;

	//budget에서 날짜랑 해당날짜 지출액 가져오기.
	public Map selectBudgetDateAndAmount(String id) throws SQLException;

	//nobudget테이블에서 데이터가 있는 날짜 가져오기
	public List selectNobudgetDatebyId(String id) throws SQLException;

	//nobudget테이블에서 예산외 총 지출액 가져오기
	public List selectNobudgetExpenseAmount(String id,List nobudgetAlldate) throws SQLException;
	
	//nobudget테이블에서 예산외 총 수입액 가져오기
	public List selectNobudgetIncomeAmount(String id,List nobudgetAlldate) throws SQLException;

	//nobudget에서 날짜랑 해당날짜 지출액 가져오기
	public Map selectNobudgetExpenseDateAndAmount(String id) throws SQLException;
	
	//nobudget에서 날짜랑 해당날짜 수입액 가져오기
	public Map selectNobudgetIncomeDateAndAmount(String id) throws SQLException;

	public Map findByCheckVal(String id,List<String> checkVal) throws SQLException;

}
