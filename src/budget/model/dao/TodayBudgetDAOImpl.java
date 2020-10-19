package budget.model.dao;

import java.util.Date;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.TotalBudgetDTO;

@Repository
public class TodayBudgetDAOImpl implements TodayBudgetDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	
	
	@Override
	public int calLeftDaysCurrentTB(String id) {
		Date today = new Date();
		return 0;
	}
}
