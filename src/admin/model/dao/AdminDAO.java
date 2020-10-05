package admin.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
	// 전체 회원 정보 가져오기
	public List selectAll() throws SQLException;
}
