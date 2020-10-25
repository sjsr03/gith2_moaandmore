package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.SearchForRecordDTO;

public interface RecordNoBudgetDAO {

	// 예산 외 내역 입력(수입/지출 입력)
	public int insertNoBudget(NoBudgetDTO noBudgetDTO) throws SQLException;
	
	// 예산 외 세부내역 입력(수입/지출 세부내역 입력)
	public void insertNoBudgetDetailDTO(NoBudgetDetailDTO noBudgetDetailDTO) throws SQLException;
	
	// 아이디, 타입, 날짜(월)로 예산외 기록 총 개수  가져오기 (키워드 X)
	public int CountAllNoBudgetById(SearchForRecordDTO searchForRecordDTO)throws SQLException;

	// 아이디, 타입, 날짜(월)로 예산외 기록 총 개수  가져오기(키워드 O)
	public int CountAllNoBudgetByIdKeyword(SearchForRecordDTO searchForRecordDTO)throws SQLException;
	
	// 아이디, 타입, 날짜(월), endRow, startRow로 예산 외 기록 가져오기 (키워드 X)
	public List selectAllNoBudget(SearchForRecordDTO searchForRecordDTO) throws SQLException;
	
	// 아이디, 타입, 날짜(월), endRow, startRow로 예산 외 기록 가져오기 (키워드 O)
	public List selectAllNoBudgetKeyword(SearchForRecordDTO searchForRecordDTO) throws SQLException;
	
	
	// 아이디, 타입으로 예산외 내역 총 개수 가져오기(키워드 X)
	// 수입, 지출, 수입+지출 다  가져올 수 있음
	public int CountNoBudgetRecordById(SearchForRecordDTO searchForRecordDTO)throws SQLException;
	
	// 아이디, 타입으로 예산+예산외 총 기록 가져오기 (키워드 X)
	// 수입+지출 내역을 제외한 나머지는 다 가져올 수 있음
	public List selectAllRecord(SearchForRecordDTO searchForRecordDTO) throws SQLException;
	
	// 아이디, 타입으로 예산외 총 기록 가져오기(키워드 X)
	// 수입+지출 내역을 가져옴 
	public List selectNobudgetRecordById(SearchForRecordDTO searchForRecordDTO) throws SQLException;
	
	
	// 아이디, 타입으로 예산외 내역 총 개수 가져오기(키워드 O)
	// 수입, 지출, 수입+지출 다  가져올 수 있음
	public int CountNoBudgetRecordByIdKeyword(SearchForRecordDTO searchForRecordDTO)throws SQLException;
	// 아이디, 타입으로 예산외 총 기록 가져오기(키워드 O)
	// 수입+지출 내역을 가져옴 
	public List selectNobudgetRecordByIdKeyword(SearchForRecordDTO searchForRecordDTO) throws SQLException;
	
	// 아이디, 타입으로 예산+예산외 총 기록 가져오기 (키워드 O)
	// 수입+지출 내역을 제외한 나머지는 다 가져올 수 있음
	public List selectAllRecordByIdKeyword(SearchForRecordDTO searchForRecordDTO) throws SQLException;

	// 고유번호로 예산 외 기록 삭제
	public int DeleteNoBudgetRecord(int number)throws SQLException;
	
	// 내역 수정
	public void modifyNoBudgetRecord(NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBudgetDetailDTO)throws SQLException;
}
