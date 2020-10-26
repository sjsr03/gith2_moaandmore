package member.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;

import budget.model.dto.TotalBudgetDTO;
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
	public void deleteMember(String id) throws SQLException {
		sqlSession.delete("member.deleteMember", id);
	}
	@Override
	public MemberDTO selectOne(String id) throws SQLException {

		MemberDTO dto = sqlSession.selectOne("member.selectOne", id);
		return dto;

	}


	@Override
	public String selectOneByNick(String nickname) throws SQLException {
		return sqlSession.selectOne("member.selectOneByNick", nickname);
	}

	
	@Override
	public void updateClose(String id) throws SQLException {
		TotalBudgetDTO dto = sqlSession.selectOne("totalBudget.selectOutClose", id);
		
		if(dto!=null) {	
			sqlSession.update("totalBudget.updateTBClose", id);	//기존 1을 2로 바꾸고
			
			sqlSession.update("totalBudget.updateClose", id);	//기존 0을 1로

		}
		
	}
	
	@Override
	public TotalBudgetDTO selectOutClose(String id) throws SQLException {
		return sqlSession.selectOne("totalBudget.selectOutClose", id);
	}


	@Override
	public int checkOverId(String user_id) throws SQLException {
		
		int checkId=sqlSession.selectOne("member.checkOverId",user_id);
		
		
		return checkId;
	}


	@Override
	public int checkOverNick(String nickname) throws SQLException {
		
		int checkNick=sqlSession.selectOne("member.checkOverNick",nickname);
		
		
		return checkNick;
	}
	
	
	


}
