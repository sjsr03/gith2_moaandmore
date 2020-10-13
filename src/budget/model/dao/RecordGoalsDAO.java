package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.RecordGoalsDTO;

public interface RecordGoalsDAO {

	
	public List<RecordGoalsDTO> selectAll(int goals_no, String id) throws SQLException;
}
