package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;

public interface RecordBudgetDAO {

	// 예산지출 내역 입력
	public int insertBudget(BudgetDTO budgetDTO) throws SQLException;
	
	// 예산 지출 세부내역 입력
	public void insertBudgetDetail(BudgetDetailDTO budgetDetailDTO) throws SQLException;
	
	// 예산 번호로 지출 기록 가져오기 
	public List selectAllBudgetByNum(int budgetNum, int startRow, int endRow) throws SQLException; 
	
}
