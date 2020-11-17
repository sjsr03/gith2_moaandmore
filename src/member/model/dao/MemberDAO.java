package member.model.dao;


import java.sql.SQLException;
import java.util.List;

import budget.model.dto.TotalBudgetDTO;
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
	//닉네임으로 아이디 가져오기
	public String selectOneByNick(String nickname) throws SQLException;
	
	//close=0인것 종료일 지났으면 종료
	public void updateClose(String id) throws SQLException;
	
	//현재 예산이 종료일이 지났는지 확인
	public TotalBudgetDTO selectOutClose(String id) throws SQLException;
	//아이디 중복검사 
	public int checkOverId(String user_id) throws SQLException;
	//닉네임 중복검사
	public int checkOverNick(String nickname) throws SQLException;
	
	// 카카오 아이디 유무 체크
	public int socialIdCheck(String id) throws SQLException;
	
	// 아이디로 비밀번호 가져오기
	public String getPwById(String id) throws SQLException;
	
	
}
