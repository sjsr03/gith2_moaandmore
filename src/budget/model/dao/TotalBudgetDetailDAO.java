package budget.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import budget.model.dto.TotalBudgetDetailDTO;

public interface TotalBudgetDetailDAO {

	
	//예산 세부내용 작성
	public void insertTotalBudgetDetail(List total_budget_detail);
	
	//총예산 고유번호에 해당하는 예산 세부내용 가져오기
	public List selectAllbyBudgetNum(int num);
	
	// 예산 번호로 해당 예산 카테고리번호 리스트 가져오기
	public List selectBudgetCategoryNums(int budgetNum) throws SQLException;
	
	//예산 세부내용 수정
	public void updateTotalBudgetDetail(List total_budget_detail);
	
	//카테고리별 현재값에서 액수 빼기
	public void updateMinusCurrent(TotalBudgetDetailDTO TBDdto);
}
