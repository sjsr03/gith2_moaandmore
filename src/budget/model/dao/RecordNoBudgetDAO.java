package budget.model.dao;

import java.sql.SQLException;

import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;

public interface RecordNoBudgetDAO {

	// 예산 외 내역 입력(수입/지출 입력)
	public int insertNoBudget(NoBudgetDTO noBudgetDTO) throws SQLException;
	
	// 예산 외 세부내역 입력(수입/지출 세부내역 입력)
	public void insertNoBudgetDetailDTO(NoBudgetDetailDTO noBudgetDetailDTO) throws SQLException;
	
	
}
