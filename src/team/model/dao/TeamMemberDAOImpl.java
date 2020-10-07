package team.model.dao;

import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.model.dto.TeamMemberDTO;

@Service
public class TeamMemberDAOImpl implements TeamMemberDAO {

	@Autowired 
	SqlSessionTemplate sqlSession = null;

	@Override
	public void insertOne(TeamMemberDTO member) throws SQLException {
		System.out.println(member.toString());
		sqlSession.insert("teamMember.insertOne", member);
		
	}
	
	@Override
	public void insertAll() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	

}
