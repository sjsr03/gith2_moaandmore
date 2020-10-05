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
	public void insertMember(MemberDTO dto) throws SQLException {
		
		sqlSession.insert("member.insertMember", dto);
		
		
		
		
	}
	@Override
	public void modifyMember(MemberDTO dto) throws SQLException {
		
		sqlSession.update("member.updateMember",dto);
		
		
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
	public MemberDTO selectOne(String id) throws SQLException {
		
			 MemberDTO member= sqlSession.selectOne("member.selectOne",id);
		
		
		
		return member;
	}
	
	


}
