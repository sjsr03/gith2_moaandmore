package calendar.service.bean;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import budget.model.dto.BudgetDTO;


public interface CalendarService {

	//id로 budget테이블에서 데이터가 있는 날짜 가져오기
	public List selectBudgetDatebyId(String id) throws SQLException;

	//budget에서 날짜랑 해당날짜 지출액 가져오기.
	public Map selectBudgetDateAndAmount(String id) throws SQLException;

	//nobudget테이블에서 지출 데이터가 있는 날짜 가져오기
	public List selectNobudgetExpenseDatebyId(String id) throws SQLException;
	
	//nobudget테이블에서 수입 데이터가 있는 날짜 가져오기
	public List selectNobudgetIncomeDatebyId(String id) throws SQLException;

	//nobudget에서 날짜랑 해당날짜 지출액 가져오기
	public Map selectNobudgetExpenseDateAndAmount(String id) throws SQLException;
	
	//nobudget에서 날짜랑 해당날짜 수입액 가져오기
	public Map selectNobudgetIncomeDateAndAmount(String id) throws SQLException;

	//총 데이터 가져오기
	public List getAlldata(String data) throws SQLException;

}
