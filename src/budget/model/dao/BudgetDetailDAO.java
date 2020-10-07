package budget.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface BudgetDetailDAO {

	
	//예산 세부내용 작성
	public void insertBudgetDetail(List budget_detail);
	
	//총예산 고유번호에 해당하는 예산 세부내용 가져오기
	public List selectAllbyBudgetNum(int num);
	
	// 예산 번호로 해당 예산 카테고리번호 리스트 가져오기
	public List selectBudgetCategoryNums(int budgetNum) throws SQLException;
}
