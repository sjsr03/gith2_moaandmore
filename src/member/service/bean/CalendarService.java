package member.service.bean;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import budget.model.dto.BudgetDTO;

public interface CalendarService {

	//id로 budget테이블에서 데이터가 있는 날짜 가져오기
	public List selectBudgetDatebyId(String id) throws SQLException;
	
	public List selectBudgetAmount(String id,List budgetAlldate) throws SQLException;

}
