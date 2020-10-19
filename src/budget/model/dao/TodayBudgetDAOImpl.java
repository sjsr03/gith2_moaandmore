package budget.model.dao;

import java.util.Date;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.TodayBudgetDTO;
import budget.model.dto.TotalBudgetDTO;

@Repository
public class TodayBudgetDAOImpl implements TodayBudgetDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	
	@Override
	public void insertTodayBudget(TodayBudgetDTO dto) {
		sqlSession.insert("todayBudget.insertTodayBudget", dto);
	}
	
	@Override
	public void updateTodayBudget(TodayBudgetDTO dto) {
		sqlSession.update("todayBudget.updateTodayBudget", dto);
	}
	@Override
	public String selectLastLoginReg(String id) {
		String lastDate = sqlSession.selectOne("todayBudget.selectLastLoginReg", id);
		return lastDate;
	}
}
