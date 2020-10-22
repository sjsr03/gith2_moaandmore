package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.SearchForRecordDTO;

public interface RecordNoBudgetDAO {

	// 예산 외 내역 입력(수입/지출 입력)
	public int insertNoBudget(NoBudgetDTO noBudgetDTO) throws SQLException;
	
	// 예산 외 세부내역 입력(수입/지출 세부내역 입력)
	public void insertNoBudgetDetailDTO(NoBudgetDetailDTO noBudgetDetailDTO) throws SQLException;
	
	// 아이디, 타입, 시작, 끝나는 날짜로 예산외 기록 총 개수  가져오기 
	public int CountAllNoBudgetById(SearchForRecordDTO searchForRecordDTO)throws SQLException;

	// 아이디, 타입, 시작날짜, 끝나는 날짜, endRow, startRow로 예산 외 기록 가져오기 
	public List selectAllNoBudget(SearchForRecordDTO searchForRecordDTO) throws SQLException;
	
	// 아이디, 타입으로 예산내역 총 개수 가져오기
	public int CountBudgetRecordById(SearchForRecordDTO searchForRecordDTO)throws SQLException;
	
	// 아이디, 타입으로 예산외 내역 총 개수 가져오기
	public int CountNoBudgetRecordById(SearchForRecordDTO searchForRecordDTO)throws SQLException;
	
	// 아이디, 타입으로 예산+예산외 총 기록 가져오기 
	public List selectAllRecord(SearchForRecordDTO searchForRecordDTO) throws SQLException;
	
	// 아이디, 타입으로 예산외 총 기록 가져오기 
	public List selectNobudgetRecord(SearchForRecordDTO searchForRecordDTO) throws SQLException;
	
	// 고유번호로 예산 외 기록 삭제
	public int DeleteNoBudgetRecord(int number)throws SQLException;
}
