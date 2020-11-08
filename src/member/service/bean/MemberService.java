package member.service.bean;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import member.model.dto.MemberDTO;

public interface MemberService {
	
	//회원가입
	public void insertMember(MemberDTO dto,MultipartHttpServletRequest request) throws SQLException;
	//아이디 비번 체크
	public int idPwCheck(String id, String pw) throws SQLException;
	//회원 한명 데이터 조회
	public MemberDTO selectOne(String id) throws SQLException;
	//회원정보 수정
	public void modifyMember(MemberDTO dto,MultipartHttpServletRequest request,String eximage) throws SQLException;
	//회원탈퇴
	public void deleteMember(String id) throws SQLException;
	
	//public int confirmId(String id) throws SQLException;
	//로그아웃
	public void logout(String sessionName) throws SQLException;
	//닉네임으로 아이디 가져오기
	public String selectOneByNick(String nickname) throws SQLException;

	//close=0인것 종료일 지났으면 종료
	public void updateClose(String id) throws SQLException;
	// 아이디 중복검사
	public int userIdCheck(String user_id) throws SQLException;
	//닉네임 중복검사
	public int nicknameCheck(String nickname) throws SQLException;
	
	// 카카오로 로그인했을 때 아이디 유무 체크  후  비밀번호 반환
	public String socialIdCheck(MemberDTO dto) throws SQLException;

}
