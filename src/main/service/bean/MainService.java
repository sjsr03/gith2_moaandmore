package main.service.bean;

import java.sql.SQLException;
import java.util.List;

public interface MainService {
	//현재 예산에서 총 사용액
	public int selectOutcomeSumByBudgetId(int num);
	
	//회원의 목표 중 달성도가 가장 높은 것
	public List selectMostGoals(String id) throws SQLException;
}
