package member.service.bean;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.model.dao.MemberDAO;
import member.model.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDAO memberDAO = null;
	
	@Override
	public void insertMember(MemberDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int confirmId(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int deleteMember(MemberDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int idPwCheck(String id, String pw) throws SQLException {
		int result = memberDAO.idPwCheck(id, pw);
		return result;
	}
	@Override
	public void logout(String sessionName) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modifyMember(MemberDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public MemberDTO selectOne(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
