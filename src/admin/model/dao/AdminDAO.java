package admin.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
	
	// 전체 회원 정보 범위지정해서 가져오기
	public List selectAll(int startRow, int endRow) throws SQLException;
	
	// 전체 회원 수 가져오기
	public int countAll() throws SQLException;
}
