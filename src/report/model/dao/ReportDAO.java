package report.model.dao;

public interface ReportDAO {
	
	//현재 예산에서 총 사용액
	public int selectOutcomeSumByBudgetId(int num);

}
