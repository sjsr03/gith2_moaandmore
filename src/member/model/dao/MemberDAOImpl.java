package member.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;

import member.model.dto.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	@Override
	public int idPwCheck(String id, String pw) throws SQLException {
		HashMap map = new HashMap();
		map.put("id", id);
		map.put("pw", pw);
		
		int result = sqlSession.selectOne("member.idPwCheck", map);
		
		return result;
	}
	@Override
	public void insertMember() throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modifyMember() throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void logout() throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int deleteMember() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public MemberDTO selectOne() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
