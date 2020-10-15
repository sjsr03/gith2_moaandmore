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
	
	// 예산번호로 지출 기록 총 개수 가져오기
	public int countAllBudgetByNum(int budgetNum)throws SQLException;
	
	// 예산 번호로 예산 기록 삭제하기(budget 테이블만 삭제 -> detail도 알아서삭제됨)
	public int budgetRecordDelete(String budget_outcome_no)throws SQLException;
	
	
}
