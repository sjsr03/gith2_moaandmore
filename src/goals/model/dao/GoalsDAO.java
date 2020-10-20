package goals.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSessionException;

import goals.model.dto.GoalsDTO;

public interface GoalsDAO {

	//CRUD
	
	//목표 하나가져오기
	public GoalsDTO selectOne(int goal_no) throws SQLException;
	
	//아이디로 목표 전체 가져오기
	public List<GoalsDTO> selectAllByPublicCh(HashMap map) throws SQLException;
	//타입으로 목표 전체 가져오기
	public List<GoalsDTO> selectAllById(String id) throws SQLException;
	
	//개인 목표 삽입
	public boolean insertGoal(GoalsDTO goal) throws SQLException;
	
	//팀 목표 삽입
	public void insertGoalByTeam(String id, int team_no) throws SQLException;
  	
	//수정
	public void modifyGoal(GoalsDTO goal) throws SQLException;
	
	//삭제
	public void deleteGoal(int goal_no,int public_ch, int team_no) throws SQLException;
	
	//saving(달성액) 업데이트
	public void updateSaving(int goa_no, int sum) throws SQLException;
	
	//회원의 가장 달성도가 높은 개인목표 가져오기
	public HashMap selectMostPersonalGoal(String id) throws SQLException;
	
	//회원의 가장 달성도가 높은 그룹목표 가져오기
	public HashMap selectMostTeamGoal(String id) throws SQLException;
	
}
