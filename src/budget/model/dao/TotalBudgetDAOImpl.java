package budget.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.TotalBudgetDTO;

@Repository
public class TotalBudgetDAOImpl implements TotalBudgetDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	@Override
	public int setBudget(TotalBudgetDTO total) throws SQLException {
		//기존 예산 모두 close=1 처리
		sqlSession.update("totalBudget.updateTotalBudget", total.getId());
		//새 예산 삽입
		sqlSession.insert("totalBudget.insertTotalBudget", total);
		TotalBudgetDTO TBdto = sqlSession.selectOne("totalBudget.selectCurrentOneById",total.getId());
		
		return TBdto.getBudget_no();
	}
	
	@Override
	public TotalBudgetDTO selectCurrentOne(String id) throws SQLException {
		return sqlSession.selectOne("totalBudget.selectCurrentOneById",id);
	}
	
	@Override
	public int selectBudgetNum(HashMap map) {
		int budgetNum = sqlSession.selectOne("totalBudget.selectBudgetNum", map);
		return budgetNum;
	}

}
