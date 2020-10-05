package member.service.bean;

import java.sql.SQLException;

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
	public int deleteMember(MemberDTO dto) throws SQLException;
	//아이디 중복검사
	public int confirmId(String id) throws SQLException;
	//로그아웃
	public void logout(String sessionName) throws SQLException;





}
