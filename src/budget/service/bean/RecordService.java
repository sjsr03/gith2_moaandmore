package budget.service.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.RecordModifyDTO;
import budget.model.dto.RecordPageDTO;
import budget.model.dto.SearchForRecordDTO;

public interface RecordService {

	// 수입지출내역 추가메서드
	public void insertRecord(MultipartHttpServletRequest request, BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO, NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBugetDetailDTO, Timestamp date) throws SQLException, IOException;

	// 예산 번호로 해당 예산 기록 전부 가져오기
	public RecordPageDTO selectAllBudgetByNum(int budgetNum, String pageNum, String keyword, String searchDate)throws SQLException;

	// type과 번호로 예산 or 예산외 체크해서 dao 호출 Delete(number, type)
	public int deleteRecord(int number, String type)throws SQLException;
	
	// 아이디, 날짜, pageNum, type, startday, endday로 예산외 기록 가져오기(수입 or 지출) 둘중 하나만!!!!
	public RecordPageDTO selectAllNoBudget(SearchForRecordDTO searchForRecordDTO)throws SQLException;

	// 날짜비교
	public Boolean compareDate(SearchForRecordDTO searchForRecordDTO, List budgetDate )throws SQLException, ParseException;

	// 아이디랑 타입으로 나눠서 예산, 예산외 기록들 가져오기 
	public RecordPageDTO selectAllRecord(SearchForRecordDTO searchForRecordDTO)throws SQLException;

	// 내역 수정
	public void modifyRecord(RecordModifyDTO recordModifyDTO, MultipartFile file) throws Exception; 
	// 저장할 파일 이름 생성
	public String getSaveFileName(String originName) throws Exception;
	// 파일 읽는 메서드
	public boolean writeFile(MultipartFile mf, String saveFileName)throws IOException;
}
