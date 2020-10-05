package member.model.dao;


import java.sql.SQLException;
import java.util.List;

import member.model.dto.MemberDTO;

public interface MemberDAO {
		
	//회원가입
	public void insertMember(MemberDTO dto) throws SQLException;
	//아이디 비밀번호 체크
	public int idPwCheck(String id, String pw) throws SQLException;
	//로그아웃
	public void logout() throws SQLException;
	//정보수정
	public void modifyMember(MemberDTO dto) throws SQLException;
	//회원탈퇴
	public void deleteMember(String id) throws SQLException;
	//회원 한명 정보 가져오기
	public MemberDTO selectOne(String id) throws SQLException;
	
	
}
