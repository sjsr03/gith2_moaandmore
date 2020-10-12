package member.model.dao;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.BudgetDTO;

public interface CalendarDAO {

	//id로 버겟 테이블에 데이터가 있는 날짜 가져오기
	public List selectBudgetDatebyId(String id) throws SQLException;
	
	public List selectBudgetAmount(String id, List budgetAlldate)throws SQLException;





}
