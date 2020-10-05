package member.model.dao;

import java.sql.SQLException;
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
	public void testCount() throws SQLException {
		int count = sqlSession.selectOne("test.userCount");
		System.out.println("테스트 카운트 " + count);	
	}
	
	@Override
	public void idPwCheck() throws SQLException {
		// TODO Auto-generated method stub
		
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
	
	


}
