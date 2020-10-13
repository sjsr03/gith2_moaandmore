package budget.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
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
		//기존에 진행중이던 예산의 종료날짜와 진행상태 변경
		HashMap map = new HashMap();
		map.put("id", total.getId());
		map.put("end_day", total.getStart_day());
		
		sqlSession.update("totalBudget.updateTBClose", total.getId());
		sqlSession.update("totalBudget.updateTotalBudgetEnd", map);
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
		int budgetNum = 0;
		try{
			budgetNum = sqlSession.selectOne("totalBudget.selectBudgetNum", map);
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("dao에서 버겟 넘  : " +  budgetNum );
		return budgetNum;
	}
	
	@Override
	public void updateTotalBudget(TotalBudgetDTO dto) throws SQLException {
		sqlSession.update("totalBudget.updateTotalBudget", dto);
	}

	@Override
	public List selectBudgetDate(String id) throws SQLException {
		List budgetDate = new ArrayList();
		String start = sqlSession.selectOne("totalBudget.selectBudgetStartDate", id);
		String end = sqlSession.selectOne("totalBudget.selectBudgetEndDate", id);
		// List 에 시작날짜, 끝나는날짜로 넣어서 리턴.
		budgetDate.add(start);
		budgetDate.add(end);
		
		
		return budgetDate;
	}

	

}
