package report.service.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import budget.model.dto.TotalBudgetDTO;

public interface ReportService {

	//회원의 모든 예산정보를 날짜 내림차순으로 정렬해서 가져오기
	public List selectAllOrderByReg(String id);
	
	//날짜와 예산번호로 지출액 합산
	public int selectOutcomeSumByReg(int budget_no, String reg);
	
	//날짜와 예산번호로 지출액 합산
	public int selectOutcomeSumByRegAndId(String id, String reg);
	
	//labelList, dataList 반환
	public HashMap selectLabelDataList(TotalBudgetDTO dto);
	
	//데이터 분석 및 추정 가능한지 결과값
	public int checkBeforeExpectation(String id) throws SQLException;
	
	//데이터 분석 자료 해쉬맵으로 리턴
	public HashMap expectation(String id) throws SQLException;
}
