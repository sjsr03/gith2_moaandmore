package team.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import team.model.dto.TeamDTO;
import team.model.dto.TeamMemberDTO;


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
		//소진
		if(dto.getStatus() == 3) {// status가 3 ; 종료일때 rank기록
			//최종랭크 final_rank에 기록
			sqlSession.update("teamMember.updateFinalRank", dto.getTeam_no());	
			System.out.println("final_rank 업데이트 : "+ dto.getTeam_no());
			
			//record_rank테이블에 누적 랭킹정보 업데이트
			List<TeamMemberDTO> list = sqlSession.selectList("teamMember.selectAllByTeamNo",dto.getTeam_no());
			

			for(TeamMemberDTO t : list) {
				int res = sqlSession.selectOne("recordRank.checkRecord", t.getId());
				if(res == 0) {
					sqlSession.insert("recordRank.insertOne",t);
				}else if(res==1) {
					sqlSession.update("recordRank.updateOne", t);
				}
			
			}
			
			
			
		}
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

	@Override
	public String getTeamUpdateTime() throws SQLException {
		String time = sqlSession.selectOne("team.selectTeamUpdateTime");
		
		return time;
	}

	@Override
	public void updateTeamUpdateTime(String day) throws SQLException {
		sqlSession.update("team.updateTeamUpdateTime", day);
	}

	@Override
	public int checkPw(int team_no, String pw) throws SQLException {
		HashMap map = new HashMap();
		map.put("team_no", team_no);
		map.put("pw", Integer.parseInt(pw));
		
		
		int result = sqlSession.selectOne("team.checkPw", map);
		
		return result;
	}
	
}
