package team.service.bean;

import java.sql.SQLException;
import java.util.List;

import team.model.dto.TeamDTO;
import team.model.dto.TeamMemberDTO;

public interface TeamMemberService {
	public List<TeamMemberDTO> selectAllbyTeamNo(int team_no) throws SQLException;
	//그룹개설시 팀원 여러명 추가
	public void insertAll(TeamDTO teamDTO, String nicknames) throws SQLException;
	//그룹개설시 리더 한 명 추가
	public void insertOne(TeamDTO teamDTO) throws SQLException;
	//그룹 개설 신청 거절시 해당 그룹의 팀원들 전체 삭제
	public void deleteTeamMemberAll(int teamNo) throws SQLException;
	//그룹 평균 모은 금액 가져오기
	public List<List> getTeamAvgArticles(List<TeamDTO> teamList) throws SQLException;
	//실제 그룹 가입 상태(is_join) 가입(1)으로 변경
	public void updateTeamMemJoin(int team_no, String nickname) throws SQLException;
}
