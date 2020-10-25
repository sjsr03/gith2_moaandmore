package budget.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import budget.model.dto.RecordGoalsDTO;

public interface RecordGoalsDAO {

	
	public List<RecordGoalsDTO> selectAll(int goals_no, String id) throws SQLException;
	
	//아이디와 목표번호로 전환기록 가져오기
	public List selectAllByIdAndNum(HashMap map);
	
	//아이디로 전환기록 있는 목표번호 리스트 가져오기
	public List selectNumListById(String id);
}
