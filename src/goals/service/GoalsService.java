package goals.service;

import java.sql.SQLException;
import java.util.List;

import goals.model.dto.GoalsDTO;

public interface GoalsService {

	//목표 하나가져오기
		public GoalsDTO selectOne(int goal_no) throws SQLException;
		
		//정렬기준으 정렬된  목표 전체 가져오기
		public List<GoalsDTO> selectAllByPublicCh(int public_ch, String sorting) throws SQLException;
		//목표 타입으로 목표 전체 가져오기
		public List<GoalsDTO> selectAllById() throws SQLException;
		
		//삽입
		public boolean insertGoal(GoalsDTO goal) throws SQLException;
		
		//수정
		public void modifyGoal(GoalsDTO goal) throws SQLException;
		
		//삭제
		public void deleteGoal(int goal_no,int public_ch,int team_no) throws SQLException;
		
		public void myGoalDetail(int goal_no) throws SQLException;
		
		public void enterTeam(int team_no) throws SQLException;
		
		//남은돈 전환가능한 목표리스트
		public List selectTransferPossibleGoals(String id) throws SQLException;
}

