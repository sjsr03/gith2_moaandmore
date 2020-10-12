package calendar.model.dao;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.BudgetDTO;

public interface CalendarDAO {

	//id로 budget 테이블에 데이터가 있는 날짜 가져오기
	public List selectBudgetDatebyId(String id) throws SQLException;
	//id랑 날짜로 budget 테이블 총 지출액 가져오기
	public List selectBudgetAmount(String id, List budgetAlldate)throws SQLException;
	//id로 nobudget 테이블 데이터가 있는 날짜 가져오기
	public List selectNoBudgetDatebyId(String id) throws SQLException;
	//id랑 날짜로 nobudget 테이블 총 지출액 가져오기
	public List selectNoBudgetExpenseAmount(String id, List nobudgetAlldate) throws SQLException;
	//id랑 날짜로 nobudget 테이블 총 수입액 가져오기
	public List selectNoBudgetIncomeAmount(String id, List nobudgetAlldate) throws SQLException;


}
