package budget.service.bean;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.TotalBudgetDTO;

@Service
public class BudgetServiceImpl implements BudgetService {

	@Autowired
	private TotalBudgetDAO TotalBudgetDAOImpl = null;
	
	@Override
	public void setBudget(TotalBudgetDTO total, List list, String id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	

}
