package admin.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {
	
	// 전체 회원 정보 범위지정해서 가져오기
	public List selectAll(int startRow, int endRow) throws SQLException;
	
	// 전체 회원 수 가져오기
	public int countAll() throws SQLException;
	
	//승인 대기 그룹들 범위 지정해서 가져오기
	public List selectAllGroupWaitAdminList(int startRow, int endRow) throws SQLException;
	
	//승인 대기 그룹들 전체 개수 가져오기
	public int countAllGroupWaitAdminList() throws SQLException;
}
