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
		
		if(pageStatus==2) {
			//진행중 그룹들
			count = sqlSession.selectOne("team.countOpenAll", map);
		}else if(pageStatus == 1) {
			//개설예정 그룹들
			count = sqlSession.selectOne("team.countOpenApAll", map);
		}else if(pageStatus == 3) {
			//종료된 그룹들
			count = sqlSession.selectOne("team.countEndAll", map);
		}
			
		
		return count;
	}
	
	@Override
	public List getTeamArticles(int pageStatus, int start, int end,int isSearch,String search) throws SQLException {
		HashMap map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("isSearch", isSearch);
		map.put("search", search);
		
		List list = null;
		
		if(pageStatus==2) {
			//진행중 그룹등
			list = sqlSession.selectList("team.selectOpenAll", map);
		}else if(pageStatus == 1) {
			//개설예정 그룹들
			list = sqlSession.selectList("team.selectOpenApAll", map);
		}else if(pageStatus == 3) {
			//종료된 그룹들
			list = sqlSession.selectList("team.selectEndAll", map);
		}
		
		
		
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
