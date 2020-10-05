package budget.service.bean;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.TotalBudgetDTO;

public interface BudgetService {
	
	public void setBudget(TotalBudgetDTO total, List list, String id) throws SQLException;
}
