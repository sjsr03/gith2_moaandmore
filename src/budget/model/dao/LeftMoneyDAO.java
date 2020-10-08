package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface LeftMoneyDAO {
	//회원의 남은 예산 정보 가져오기
	public List selectAllById(String id) throws SQLException;
}
