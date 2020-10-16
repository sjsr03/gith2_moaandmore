package budget.service.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.RecordPageDTO;
import budget.model.dto.SearchForRecordDTO;

public interface RecordService {

	// 수입지출내역 추가메서드
	public void insertRecord(MultipartHttpServletRequest request, BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO, NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBugetDetailDTO, Timestamp date) throws SQLException, IOException;

	// 예산 번호로 해당 예산 기록 전부 가져오기
	public RecordPageDTO selectAllBudgetByNum(int budgetNum, String pageNum)throws SQLException;

	// 예산 번호로 예산 기록 삭제하기(budget 테이블만 삭제 -> detail도 알아서삭제됨)
	public int budgetRecordDelete(String budget_outcome_no)throws SQLException;
	
	// 아이디, 날짜, pageNum, type, startday, endday로 nobudget 가져오기
	public RecordPageDTO selectAllNoBudget(SearchForRecordDTO searchForRecordDTO)throws SQLException;

	// 날짜비교
	public Boolean compareDate(SearchForRecordDTO searchForRecordDTO, List budgetDate )throws SQLException, ParseException;
}
