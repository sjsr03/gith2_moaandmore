package main.service.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import budget.model.dao.LeftMoneyDAO;
import budget.model.dao.TodayBudgetDAO;
import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.TotalBudgetDTO;
import goals.model.dao.GoalsDAO;
import report.model.dao.ReportDAO;


@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	private ReportDAO reportDAO = null;
	@Autowired
	private GoalsDAO goalsDAO = null;
	@Autowired
	private LeftMoneyDAO leftMoneyDAO = null;
	@Autowired
	private TodayBudgetDAO todayBudgetDAO = null;
	@Autowired
	private TotalBudgetDAO totalBudgetDAO = null;
	
	@Override
	//현재 예산에서 총 사용액
	public int selectOutcomeSumByBudgetId(int num, String id) throws SQLException {
		TotalBudgetDTO TBdto = totalBudgetDAO.selectCurrentOne(id);
		
		int sum = reportDAO.selectOutcomeSumByBudgetId(num);
		int todaySum = todayBudgetDAO.selectSumTodayBudget(id);
		int LM = leftMoneyDAO.selectCurrentLeftMoneySum(id);
		return (TBdto.getBudget()-TBdto.getTotal_budget_current()-todaySum);
	}
	
	@Override
	public List selectMostGoals(String id) throws SQLException {
		List goalsList = new ArrayList();
		goalsList.add(goalsDAO.selectMostPersonalGoal(id));
		goalsList.add(goalsDAO.selectMostTeamGoal(id));
		
		return goalsList;
	}
	

}
