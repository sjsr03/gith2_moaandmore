package budget.service.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.RecordPageDTO;

public interface RecordService {

	// 수입지출내역 추가메서드
	public void insertRecord(MultipartHttpServletRequest request, BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO, NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBugetDetailDTO, Timestamp date) throws SQLException, IOException;

	// 예산 번호로 해당 예산 기록 전부 가져오기
	public RecordPageDTO selectAllBudgetByNum(int budgetNum, String pageNum)throws SQLException;
}
