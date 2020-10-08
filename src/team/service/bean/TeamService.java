package team.service.bean;

import java.sql.SQLException;
import java.util.List;

import team.model.dto.TeamDTO;

public interface TeamService {
		//진행중인 공개 그룹 개수 검색
		public int getTeamArticleCount() throws SQLException;
		
		//진행중인 공개 그룹 범위 지정 가져오기
		public List getTeamArticles(int start, int end) throws SQLException;
		
		//그룹 하나 가져오기
		public TeamDTO selectOne(int team_no) throws SQLException;
		
		//그룹 신청
		public void insertTeamArticle(TeamDTO dto) throws SQLException;
}