package report.model.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.TotalBudgetDTO;

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
	
	@Override
	public List selectAllOrderByReg(String id) {
		List list = sqlSession.selectList("report.selectAllOrderByReg", id);
//		//종료일 표기를 다음날로 할 경우...
//		for(Object obj : list) {
//			TotalBudgetDTO dto = (TotalBudgetDTO) obj;
//			Timestamp ts = dto.getEnd_day();
//			ts.setDate((dto.getEnd_day().getDate()+1));
//			dto.setEnd_day(ts);
//		}
		return list;
	}
}