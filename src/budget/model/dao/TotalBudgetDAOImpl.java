package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import budget.model.dto.TotalBudgetDTO;

@Repository
public class TotalBudgetDAOImpl implements TotalBudgetDAO {
	
	@Override
	public void setBudget(TotalBudgetDTO total, List list, String id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
