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
		if(dto.getPassword() == null)
			sqlSession.insert("team.insertTeamArticleNoPw", dto);
		else
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
	
}
