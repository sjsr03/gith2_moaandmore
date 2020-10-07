package team.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import team.model.dto.TeamDTO;

public class TeamDAOImpl implements TeamDAO{
	
	private SqlSessionTemplate sqlSession = null;
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public int getTeamArticleCount() throws SQLException {
		int count = sqlSession.selectOne("team.countOpenAll");
		
		return count;
	}
	
	@Override
	public List getTeamArticles(int start, int end) throws SQLException {
		HashMap map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		
		List list = sqlSession.selectList("team.selectOpenAll", map);
		
		return list;
	}

	@Override
	public void insertTeamArticle(TeamDTO dto) throws SQLException {
		sqlSession.insert("team.insertTeamArticle", dto);
	}
	
}