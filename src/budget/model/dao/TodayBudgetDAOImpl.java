package budget.model.dao;

import java.util.Date;
import java.util.List;

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
	public void insertTodayBudget(List list) {
		for (int i=0; i < list.size(); i++) {
			TodayBudgetDTO todayDTO = (TodayBudgetDTO) list.get(i);
			
			sqlSession.insert("todayBudget.insertTodayBudget", todayDTO);
		}
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
	
	@Override
	public List selectTodayBudgetList(String id) {
		List list = sqlSession.selectList("todayBudget.selectTodayBudgetList", id);
		return list;
	}
	
	@Override
	public int selectSumTodayBudget(String id) {
		int sum = sqlSession.selectOne("todayBudget.selectSumTodayBudget", id);
		return sum;
	}
	
	@Override
	public void deleteTodayBudget(String id) {
		sqlSession.delete("todayBudget.deleteTodayBudget", id);
	}
}
