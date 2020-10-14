package budget.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import budget.model.dto.TotalBudgetDTO;

public interface TotalBudgetDAO {

	public int setBudget(TotalBudgetDTO total) throws SQLException;
	public TotalBudgetDTO selectCurrentOne(String id) throws SQLException;
	
	// 아이디, 날짜로 해당 예산 번호 가져오기
	public int selectBudgetNum(HashMap map) throws SQLException;
	
	//총예산 금액 변경
	public void updateTotalBudget(TotalBudgetDTO dto) throws SQLException;
	
	// 회원의 현재 예산의 마지막날, 지난예산의 시작날짜 가져오기
	public List selectBudgetDate(String id)throws SQLException;
	
	//직전예산정보 가져오기
	public TotalBudgetDTO selectLastTB(String id) throws SQLException;
	
	//예산 번호로 전체 정보 가져오기
	public TotalBudgetDTO selectOneByNum(int budget_no) throws SQLException;
	
}
