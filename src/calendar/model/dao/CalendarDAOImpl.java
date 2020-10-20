package calendar.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;




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
		
		
			Map map = new HashMap();
			map.put("id", id);
			map.put("budgetAlldate", budgetAlldate);
			
			
			
			List Allamount = sqlSession.selectList("calendar.selectBudgetAmountBydate",map);
			
			return Allamount;
		
	}

	@Override
	public List selectNoBudgetExpenseDatebyId(String id) throws SQLException {
		
		List noBudget = sqlSession.selectList("calendar.selectNoBudgetExpenseDatebyId",id);
		
		return noBudget;
		
		
		
	}
	
	@Override
	public List selectNoBudgetIncomeDatebyId(String id) throws SQLException {
	
		List noBudget = sqlSession.selectList("calendar.selectNoBudgetIncomeDatebyId",id);
		
		return noBudget;
	}
	

	@Override
	public List selectNoBudgetExpenseAmount(String id, List nobudgetAlldate) throws SQLException {
		
		Map map = new HashMap();
		map.put("id", id);
		map.put("nobudgetAlldate", nobudgetAlldate);
		
		
		
		List AllNobudgetExpenseAmount = sqlSession.selectList("calendar.selectNoBudgetExpenseAmountBydate",map);
		
		
		return AllNobudgetExpenseAmount;
	}

	@Override
	public List selectNoBudgetIncomeAmount(String id, List nobudgetAlldate) throws SQLException {


		Map map = new HashMap();
		map.put("id", id);
		map.put("nobudgetAlldate", nobudgetAlldate);
		
		
		
		List AllNobudgetIncomeAmount = sqlSession.selectList("calendar.selectNoBudgetIncomeAmountBydate",map);

		
		return AllNobudgetIncomeAmount;
	}

	@Override
	public List getBudgetDetail(String id, String date) throws SQLException {
		
		Map map = new HashMap();
		map.put("id", id);
		map.put("date",date); 
		List budgetDetail = sqlSession.selectList("calendar.selectBudgetDetail",map);
		
		
		return budgetDetail;
	}

	@Override
	public List getNobudgetExpenseDetail(String id, String date) throws SQLException {
		
		Map map = new HashMap();
		map.put("id", id);
		map.put("date",date); 
		List noBudgetExpenseDetail = sqlSession.selectList("calendar.selectNobudgetExpenseDetail",map);
		
		return noBudgetExpenseDetail;
	}

	
	@Override
	public List getNobudgetIncomeDetail(String id, String date) throws SQLException {
	
		Map map = new HashMap();
		map.put("id", id);
		map.put("date",date); 
		List nobudgetIncomeDetail = sqlSession.selectList("calendar.selectNobudgetIncomeDetail",map);
		
		
		return nobudgetIncomeDetail;
	}

	

	
	

	
	
}
