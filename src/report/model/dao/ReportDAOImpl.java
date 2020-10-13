package report.model.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDAOImpl implements ReportDAO {

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	
	@Override
	public int selectOutcomeSumByBudgetId(int num) {
		int sum = 0;
		if(sqlSession.selectOne("report.selectOutcomeSumByBudgetId", num) != null) {
			sum = sqlSession.selectOne("report.selectOutcomeSumByBudgetId", num);
		}
		return sum;
	}
	
	//오늘 소비한 카테고리별 금액
	public HashMap selectTodayOutcome() {
		HashMap map = new HashMap();		
		
		
		return map;
	}
}
