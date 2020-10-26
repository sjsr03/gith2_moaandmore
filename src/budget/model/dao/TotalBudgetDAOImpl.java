package budget.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
		
		sqlSession.update("totalBudget.updateTBClose", total.getId());		//기존 close 1 --> 2
		sqlSession.update("totalBudget.updateTotalBudgetEnd", map);			//기존 close 0 --> 1
		
		
		//leftmoney에 남아있던 컬럼 지우기
		sqlSession.delete("leftMoney.deleteLeftMoneyById", total.getId());
		//todayBudget에 남아있던 컬럼 지우기
		sqlSession.delete("todayBudget.deleteTodayBudget", total.getId());
		
		
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
		
		if(start == null) {
			start = sqlSession.selectOne("totalBudget.selectCurrBudgetStartDate", id);		
		}
		
		// List 에 시작날짜, 끝나는날짜로 넣어서 리턴.
		budgetDate.add(start);
		budgetDate.add(end);
		
		
		return budgetDate;
	}

	@Override
	public TotalBudgetDTO selectLastTB(String id) throws SQLException {
		return sqlSession.selectOne("totalBudget.selectLastTB", id);
	}
	
	@Override
	public TotalBudgetDTO selectOneByNum(int budget_no) throws SQLException {
		return sqlSession.selectOne("totalBudget.selectOneByNum",budget_no);
	}
	
	@Override
	public int calLeftDaysCurrentTB(String id) throws SQLException {
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		
		TotalBudgetDTO TBdto = selectCurrentOne(id);
		long lt = TBdto.getEnd_day().getTime()-today.getTime();
		int period = (int)Math.round(lt/(1000*60*60*24));
		return period;
	}
	@Override
	public void updateCurrentBudget(int budget_no) throws SQLException {
		sqlSession.update("totalBudget.updateCurrentBudget", budget_no);
	}

	@Override
	public List<TotalBudgetDTO> selectBudgetAllByID(String id) throws SQLException {
		List<TotalBudgetDTO> list = sqlSession.selectList("totalBudget.selectBudgetAllByID", id);
		
		return list;
	}
	

}
