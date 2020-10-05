package goals.service;

import java.util.List;

import goals.model.dto.GoalsDTO;

public interface GoalsService {

	//목표 하나가져오기
		public GoalsDTO selectOne(int goal_no);
		
		//아이디로 목표 전체 가져오기
		public List<GoalsDTO> selectAllById(String id);
		
		//삽입
		public boolean insertGoal(GoalsDTO goal);
		
		//수정
		public boolean modifyGoal(GoalsDTO goal);
		
		//삭제
		public boolean deleteGoal(int goal_no);
}
