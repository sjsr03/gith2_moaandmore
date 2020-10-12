package budget.service.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;

public interface RecordService {

	// 수입지출내역 추가메서드
	public void insertRecord(MultipartHttpServletRequest request, BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO, NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBugetDetailDTO, Timestamp date) throws SQLException, IOException;

}
