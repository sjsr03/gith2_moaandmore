package budget.model.dao;

import java.util.List;

public interface BudgetDetailDAO {

	
	//예산 세부내용 작성
	public void insertBudgetDetail(List budget_detail);
	
	//총예산 고유번호에 해당하는 예산 세부내용 가져오기
	public List selectAllbyBudgetNum(int num);
}
