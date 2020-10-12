package member.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import budget.model.dto.BudgetDTO;



public class CalendarDAOImpl implements CalendarDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	//id로 버겟 테이블에 데이터가 있는 날짜 가져오기
	@Override
	public List selectBudgetDatebyId(String id) throws SQLException {
		
		List budget = sqlSession.selectList("calendar.selectBudgetDatebyId",id);
		
		return budget;
	}

	@Override
	public List selectBudgetAmount(String id, List budgetAlldate) throws SQLException {
		
		System.out.println(1);
			Map map = new HashMap();
			map.put("id", id);
			map.put("budgetAlldate", budgetAlldate);
			
			
			
			List Allamount = sqlSession.selectList("calendar.selectBudgetAmountBydate",map);
			
			return Allamount;
		
	}
	
	

	
	
}
