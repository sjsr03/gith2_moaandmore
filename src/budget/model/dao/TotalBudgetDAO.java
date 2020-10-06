package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.TotalBudgetDTO;

public interface TotalBudgetDAO {

	public void setBudget(TotalBudgetDTO total, List list, String id) throws SQLException;
}
