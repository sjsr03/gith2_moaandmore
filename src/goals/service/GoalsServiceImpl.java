package goals.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import budget.model.dao.RecordGoalsDAOImpl;
import budget.model.dto.RecordGoalsDTO;
import goals.model.dao.GoalsDAOImpl;
import goals.model.dto.GoalsDTO;
import team.model.dao.TeamMemberDAOImpl;
import team.model.dto.TeamMemberDTO;


@Service
public class GoalsServiceImpl implements GoalsService {
	
	@Autowired
	private GoalsDAOImpl goalsDAO = null;
	
	@Autowired
	private TeamMemberDAOImpl teamMemberDAO = null;
	
	@Autowired
	private RecordGoalsDAOImpl recordGoalsDAO = null;
	
	
	@Autowired
	public Date date = null;// util.Date

	@Override
	public GoalsDTO selectOne(int goal_no) throws SQLException {
		return goalsDAO.selectOne(goal_no);
	}

	@Override
	public List<GoalsDTO> selectAllByPublicCh(int public_ch, String sorting) throws SQLException {	
		String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		
		HashMap map = new HashMap();
		map.put("id",id);
		map.put("public_ch",public_ch);

				
		map.put("sorting",sorting);
		
		
		return goalsDAO.selectAllByPublicCh(map);
	}
	
	@Override
	public List<GoalsDTO> selectAllById() throws SQLException {
		String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		return goalsDAO.selectAllById(id);
	}

	@Override
	public boolean insertGoal(GoalsDTO goal) throws SQLException {
		//아이디, 목표명, 목표액만 받아옴
		//나머지 셋팅
		String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		goal.setId(id);
		goal.setStart_day(new java.sql.Timestamp(date.getTime()));
		goal.setEnd_day(new java.sql.Timestamp(date.getTime()));
		System.out.println(goal.toString());
		boolean ch = goalsDAO.insertGoal(goal);
		
		return ch;
	}

	@Override
	public void modifyGoal(GoalsDTO goal) throws SQLException{
		goalsDAO.modifyGoal(goal);
	}

	@Override
	public void deleteGoal(int goal_no,int public_ch, int team_no) throws SQLException {
		goalsDAO.deleteGoal(goal_no, public_ch, team_no);
		
	}

	@Override
	public void myGoalDetail(int goal_no) throws SQLException {
		//목표 정보
		GoalsDTO goal = goalsDAO.selectOne(goal_no);
		
		//목표액 관련 세부내역
		String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		List<RecordGoalsDTO> recordList = recordGoalsDAO.selectAll(goal_no, id);
		
		RequestContextHolder.getRequestAttributes().setAttribute("goal", goal, RequestAttributes.SCOPE_REQUEST);
		RequestContextHolder.getRequestAttributes().setAttribute("recordList", recordList, RequestAttributes.SCOPE_REQUEST);
		
	}

	//팀 가입
	@Override
	public void enterTeam(int team_no) throws SQLException {
		
		//team_member 테이블에 데이터 추가
		String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		String nickname = (String)RequestContextHolder.getRequestAttributes().getAttribute("memName", RequestAttributes.SCOPE_SESSION);
		
		//teamMember
		TeamMemberDTO member = new TeamMemberDTO(team_no, id, nickname, 0, 0, 0, 0);		
		teamMemberDAO.insertOne(member);
		
		//goals테이블에 팀 목표추가
		//GoalsDTO goal = new GoalsDTO(0, id, "", target_money, saving, start_day, end_day, public_ch, public_type, team_no)

		goalsDAO.insertGoalByTeam(id, team_no);
		
			
	}
	
	@Override
	public List selectTransferPossibleGoals(String id) throws SQLException {
		return goalsDAO.selectTransferPossibleGoals(id);
	}
	
	

	

}
