package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.TotalBudgetDTO;

public interface TotalBudgetDAO {

	public int setBudget(TotalBudgetDTO total) throws SQLException;
}
