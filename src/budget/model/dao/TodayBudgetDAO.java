package budget.model.dao;

import budget.model.dto.TodayBudgetDTO;

public interface TodayBudgetDAO {

	//하루치 예산 레코드 삽입
	public void insertTodayBudget(TodayBudgetDTO dto);
	
	//오늘의 예산 업데이트
	public void updateTodayBudget(TodayBudgetDTO dto);
	
	//오늘의 예산 마지막 업데이트 날짜
	public String selectLastLoginReg(String id);
}
