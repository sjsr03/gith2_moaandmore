package admin.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	// 전체 회원 정보 가져오기
	@Override
	public List selectAll(int startRow, int endRow) throws SQLException {
		
		// hashmap 타입으로 값 던져줌
		Map map = new HashMap();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		List members = sqlSession.selectList("admin.selectAll", map);
		return members;
	}
	
	// 전체 회원 수 가져오기
	@Override
	public int countAll() throws SQLException {
		int count = sqlSession.selectOne("admin.countAll");
		return count;
	}

	//승인 대기 그룹들 범위 지정해서 가져오기
	@Override
	public List selectAllGroupWaitAdminList(int startRow, int endRow) throws SQLException {
		HashMap map = new HashMap();
		map.put("start", startRow);
		map.put("end", endRow);
		
		List list = sqlSession.selectList("admin.selectAllGroupWaitAdmin", map);
		
		return list;
	}

	@Override
	public int countAllGroupWaitAdminList() throws SQLException {
		int count = sqlSession.selectOne("admin.selectAllGroupWaitAdminCount");
		
		return count;
	}

}
