package budget.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.TotalBudgetDetailDTO;

@Repository
public class TotalBudgetDetailDAOImpl implements TotalBudgetDetailDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;

	
	@Override
	public void insertTotalBudgetDetail(List total_budget_detail) {
		for (int i = 0; i < total_budget_detail.size(); i++) {
			TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO) total_budget_detail.get(i);
			sqlSession.insert("totalBudgetDetail.insertTotalBudgetDetail", dto);
		}
	}
	
	@Override
	public void updateTotalBudgetDetail(List total_budget_detail) {
		for (int i = 0; i < total_budget_detail.size(); i++) {
			TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO) total_budget_detail.get(i);
			sqlSession.update("totalBudgetDetail.updateTotalBudgetDetail", dto);
		}
	}
	
	@Override
	public List selectAllbyBudgetNum(int num) {
		List list = sqlSession.selectList("totalBudgetDetail.selectAllbyBudgetNum", num);
		return list;
	}

	@Override
	public List selectBudgetCategoryNums(int budgetNum) throws SQLException {
		List categoryList = new ArrayList();
		//리스트처리
		
		categoryList = sqlSession.selectList("totalBudgetDetail.selectBudgetCategoryNums", budgetNum);
		return categoryList;
	}
	
}
