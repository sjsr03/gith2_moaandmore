package budget.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.BudgetDetailDTO;

@Repository
public class BudgetDetailDAOImpl implements BudgetDetailDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	
	@Override
	public void insertBudgetDetail(List budget_detail) {
		for (int i = 0; i < budget_detail.size(); i++) {
			BudgetDetailDTO dto = (BudgetDetailDTO) budget_detail.get(i);
			sqlSession.insert("budgetDetail.insertBudgetDetail", dto);
		}
	}

}
