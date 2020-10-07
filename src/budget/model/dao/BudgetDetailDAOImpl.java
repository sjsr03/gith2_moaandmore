package budget.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public List selectAllbyBudgetNum(int num) {
		List list = sqlSession.selectList("budgetDetail.selectAllbyBudgetNum", num);
		return list;
	}

	@Override
	public List selectBudgetCategoryNums(int budgetNum) throws SQLException {
		List categoryList = new ArrayList();
		//리스트처리
		categoryList = sqlSession.selectList("budgetDetail.selectBudgetCategoryNums", budgetNum);
		return categoryList;
	}

	
}
