package team.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import team.model.dto.TeamMemberDTO;

@Repository
public interface TeamMemberDAO {
	
	//그룹참여시 팀원 한명 추가
	public void insertOne(TeamMemberDTO member) throws SQLException;
	
	//그룹개설시 팀원 여러명 추가
	public void insertAll() throws SQLException;
	
	//team_no 팀원리스트 가져오기
	public List<TeamMemberDTO> selectAllByTeam(int team_no) throws SQLException;
	
	//그룹 개설 신청 거절시 해당 그룹의 팀원들 전체 삭제
	public void deleteTeamMemberAll(int teamNo) throws SQLException;
	
	//실제 그룹 가입 상태(is_join) 가입(1)으로 변경
	public void updateTeamMemJoin(int team_no, String nickname) throws SQLException;
	
	//내가 가입된 그룹 전체 가져오기
	public List selectMyTeamAll(String nickname) throws SQLException;
}
