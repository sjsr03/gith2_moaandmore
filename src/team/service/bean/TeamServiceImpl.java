package team.service.bean;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.model.dao.TeamDAO;
import team.model.dto.TeamDTO;
@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	TeamDAO dao = null; 

	private SqlSessionTemplate sqlSession = null;
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public int getTeamArticleCount(int pageStatus,int isSearch,String search) throws SQLException {
		return dao.getTeamArticleCount(pageStatus,isSearch,search);
	}

	@Override
	public List<TeamDTO> getTeamAll() throws SQLException{
		return dao.getTeamAll();
	}
	
	@Override
	public List<TeamDTO> getTeamArticles(int pageStatus, int start, int end,int isSearch,String search,int range) throws SQLException {
		return dao.getTeamArticles(pageStatus, start, end,isSearch,search,range);
	}
	
	@Override
	public void insertTeamArticle(TeamDTO dto) throws SQLException {
		dto.setStart_day(dto.getStart_day().replaceAll("-", ""));
		dto.setEnd_day(dto.getEnd_day().replaceAll("-", ""));
		
		dao.insertTeamArticle(dto);
	}

	@Override
	public TeamDTO selectOne(int team_no) throws SQLException {
		return dao.selectOne(team_no);
	}

	
	@Override
	public void updateTeamStatus(TeamDTO dto) throws SQLException {
		//status 업데이트
		dao.updateTeamStatus(dto);
		
	}

	@Override
	public int getTeamMyRequestCount(String nickname) throws SQLException {
		return dao.getTeamMyRequestCount(nickname);
	}

	@Override
	public List getTeamMyRequests(String nickname, int start, int end) throws SQLException {
		return dao.getTeamMyRequests(nickname, start, end);
	}

	@Override
	public String getTeamUpdateTime() throws SQLException {
		return dao.getTeamUpdateTime();
	}

	@Override
	public void updateTeamUpdateTime(String day) throws SQLException {
		dao.updateTeamUpdateTime(day);
	}

	@Override
	public int checkPw(int team_no, String pw) throws SQLException {
		return dao.checkPw(team_no, pw);
	}

}
