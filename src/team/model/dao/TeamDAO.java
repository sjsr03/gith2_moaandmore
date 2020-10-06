package team.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface TeamDAO {
	//진행중인 공개 그룹 개수 검색
	public int getTeamArticleCount() throws SQLException;
	//진행중인 공개 그룹 범위 지정 가져오기
	public List getTeamArticles(int start, int end) throws SQLException;
}
