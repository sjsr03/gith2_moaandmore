package budget.model.dao;

import java.util.List;

import budget.model.dto.TodayBudgetDTO;

public interface TodayBudgetDAO {

	//하루치 예산 레코드 삽입
	public void insertTodayBudget(List list);
	
	//오늘의 예산 업데이트
	public void updateTodayBudget(TodayBudgetDTO dto);
	
	//오늘의 예산 마지막 업데이트 날짜 가져오기
	public String selectLastLoginReg(String id);
	
	//오늘의 예산정보 리스트로 가져오기
  	public List selectTodayBudgetList(String id);
  	
  	//오늘의 예산의 합산
  	public int selectSumTodayBudget(String id);
	
  	//회원의 오늘의 예산 내역 삭제
  	public void deleteTodayBudget(String id);
}
