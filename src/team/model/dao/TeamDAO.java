package team.model.dao;

import java.sql.SQLException;
import java.util.List;

import team.model.dto.TeamDTO;

public interface TeamDAO {
	//진행중인 공개 그룹 개수 검색
	public int getTeamArticleCount(int pageStatus,int isSearch,String search) throws SQLException;
	//승인된 그룹 전체 가져오기
	public List<TeamDTO> getTeamAll() throws SQLException;
	//그룹 범위 지정 가져오기
	public List<TeamDTO> getTeamArticles(int pageStatus, int start, int end,int isSearch,String search,int range) throws SQLException;
	//내가 가입된 그룹 중 특정 상태의 그룹 전체 개수 가져오기
	public int getMyOkTeamArticleCount(String nickname, int pageStatus,int isSearch,String search) throws SQLException;
	//내가 가입된 그룹 범위 지정 가져오기
	public List<TeamDTO> getMyOkTeamArticles(String nickname, int pageStatus, int start, int end,int isSearch,String search,int range) throws SQLException;
	//그룹 신청
	public void insertTeamArticle(TeamDTO dto) throws SQLException;
	//그룹 상태 수정
	public void updateTeamStatus(TeamDTO dto) throws SQLException;
	//팀 상태 최종 업데이트 시간 가져오기
	public String getTeamUpdateTime() throws SQLException;
	//팀 상태 최종 업데이트 시간 수정
	public void updateTeamUpdateTime(String day) throws SQLException; 
	
	//그룹 비밀번호가 맞는지 체크
	public int checkPw(int team_no, String pw) throws SQLException;
	
	//그룹 하나
	public TeamDTO selectOne(int team_no) throws SQLException;
	
	//닉네임으로 해당 닉네임이 개설 요청한 그룹 목록들 개수 가져오기
	public int getTeamMyRequestCount(String nickname) throws SQLException;
			
	//닉네임으로 해당 닉네임이 개설 요청한 그룹 목록들 가져오기
	public List getTeamMyRequests(String nickname, int start, int end) throws SQLException;
	
	
	//닉네임을 초대한 승인 완료된 그룹 목록들 개수 가져오기
	public int getTeamComeInviteCount(String nickname) throws SQLException;
	//닉네임을 초대한 승인 완료된 그룹 목록들 가져오기
	public List getTeamComeInvites(String nickname, int start, int end) throws SQLException;
}
