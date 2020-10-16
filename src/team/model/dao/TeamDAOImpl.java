package team.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import team.model.dto.TeamDTO;


@Repository
public class TeamDAOImpl implements TeamDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public int getTeamArticleCount(int pageStatus,int isSearch,String search) throws SQLException {
		int count = 0;
		
		HashMap map = new HashMap();
		map.put("isSearch", isSearch);
		map.put("search", search);
		map.put("pageStatus", pageStatus);
		
		count = sqlSession.selectOne("team.countTeamAll", map);
		
		return count;
	}
	
	@Override
	public List<TeamDTO> getTeamArticles(int pageStatus, int start, int end,int isSearch,String search,int range) throws SQLException {
		HashMap map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("isSearch", isSearch);
		map.put("search", search);
		map.put("range", range);
		map.put("pageStatus", pageStatus);
		
		List list = null;
		
		list = sqlSession.selectList("team.selectTeamAllCon", map);
		
		
		
		return list;
	}
	
	@Override
	public List<TeamDTO> getTeamAll() throws SQLException{
		List list = null;
		
		list = sqlSession.selectList("team.selectTeamAll");
		
		return list;
	}

	
	
	@Override
	public void insertTeamArticle(TeamDTO dto) throws SQLException {
		sqlSession.insert("team.insertTeamArticle", dto);
	}

	@Override
	public TeamDTO selectOne(int team_no) throws SQLException {
		
		return sqlSession.selectOne("team.selectOne", team_no);
	}

	@Override
	public void updateTeamStatus(TeamDTO dto) throws SQLException {
		sqlSession.update("team.updateTeamStatus", dto);
	}

	@Override
	public int getTeamMyRequestCount(String nickname) throws SQLException {
		int count = sqlSession.selectOne("team.countAllTeamMyRequest", nickname);
		
		return count;
	}

	@Override
	public List getTeamMyRequests(String nickname, int start, int end) throws SQLException {
		HashMap map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("leader", nickname);
		
		List list = sqlSession.selectList("team.selectAllTeamMyRequest", map);
		
		return list;
	}
	
}
