package budget.model.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.RecordGoalsDTO;


@Repository
public class RecordGoalsDAOImpl implements RecordGoalsDAO {

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	@Override
	public List<RecordGoalsDTO> selectAll(int goals_no, String id) throws SQLException {
		
		HashMap map = new HashMap();
		map.put("goals_no", goals_no);
		map.put("id", id);

		return sqlSession.selectList("recordGoals.selectAll", map);
	}
	
	@Override
	public List selectAllByIdAndNum(HashMap map) {
		List returnList = new ArrayList();
		List list = sqlSession.selectList("recordGoals.selectAllByIdAndNum", map);
		for(int i = 0; i < list.size(); i++) {
			HashMap tmp = (HashMap)list.get(i);
			System.out.println(tmp);
			RecordGoalsDTO RGdto = new RecordGoalsDTO();
			RGdto.setAmount(((BigDecimal)tmp.get("AMOUNT")).intValue());
			RGdto.setGoal_no(((BigDecimal)tmp.get("GOAL_NO")).intValue());
			RGdto.setId((String)tmp.get("ID"));
			RGdto.setReg(Timestamp.valueOf(((String)tmp.get("REG")) + " 00:00:00"));
			
			returnList.add(RGdto);
		}
		
		
		return returnList;
	}
	
	@Override
	public List selectNumListById(String id) {
		List list = sqlSession.selectList("recordGoals.selectNumListById", id);
		return list;
	}
	

}
