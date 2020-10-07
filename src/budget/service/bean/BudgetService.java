package budget.service.bean;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.TotalBudgetDTO;

public interface BudgetService {
	
	//예산설정
	public void setBudget() throws SQLException;
	
	//현재 총예산 정보 가져오기
	public TotalBudgetDTO selectCurrentOne(String id) throws SQLException;
	
	//현재 총예산 정보 가져오기
	public List selectAllbyBudgetNum(int num) throws SQLException;
	
	//회원의 남은 예산 정보 가져오기
	public List selectLeftMoneyById(String id) throws SQLException;
}
