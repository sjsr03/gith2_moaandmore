package goals.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import goals.model.dao.GoalsDAOImpl;
import goals.model.dto.GoalsDTO;


@Service
public class GoalsServiceImpl implements GoalsService {
	
	@Autowired
	private GoalsDAOImpl goalsDAO = null;

	@Override
	public GoalsDTO selectOne(int goal_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GoalsDTO> selectAllById(String id) {	
		return goalsDAO.selectAllById(id);
	}

	@Override
	public boolean insertGoal(GoalsDTO goal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyGoal(GoalsDTO goal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteGoal(int goal_no) {
		// TODO Auto-generated method stub
		return false;
	}

}
