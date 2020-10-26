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
	
	//총지출 데이터 분석
	public HashMap expectOutcome(String id) throws SQLException;
	
	//목표 달성 데이터 분석
	public HashMap expectGoals(String id) throws SQLException;
	
	//아이디와 목표번호로 전환기록 가져오기
	public List selectAllByIdAndNum(String id, int goal_no);
	
	//아이디로 전환기록 있는 목표번호와 이름 리스트 가져오기
	public List selectNumAndSubListById(String id) throws SQLException;
}
