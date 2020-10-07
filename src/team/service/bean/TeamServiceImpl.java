package team.service.bean;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import team.model.dao.TeamDAO;
import team.model.dto.TeamDTO;

public class TeamServiceImpl implements TeamService{
	
	@Autowired
	TeamDAO dao = null; 

	private SqlSessionTemplate sqlSession = null;
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public int getTeamArticleCount() throws SQLException {
		return dao.getTeamArticleCount();
	}

	@Override
	public List getTeamArticles(int start, int end) throws SQLException {
		return dao.getTeamArticles(start, end);
	}

	@Override
	public void insertTeamArticle(TeamDTO dto) throws SQLException {
		dto.setStart_day(dto.getStart_day().replaceAll("-", ""));
		dto.setEnd_day(dto.getEnd_day().replaceAll("-", ""));
		
		dao.insertTeamArticle(dto);
	}

}
