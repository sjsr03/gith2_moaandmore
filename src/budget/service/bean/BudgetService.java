package budget.service.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import budget.model.dto.TotalBudgetDTO;

public interface BudgetService {
	
	//예산설정
	public void setBudget() throws SQLException;
	
	//예산 수정
	public void updateBudget() throws SQLException;
	
	//현재 총예산 정보 가져오기
	public TotalBudgetDTO selectCurrentOne(String id) throws SQLException;
	
	//현재 총예산 정보 가져오기
	public List selectAllbyBudgetNum(int num) throws SQLException;
	
	//회원의 남은 예산 정보 가져오기
	public List selectLeftMoneyById(String id) throws SQLException;
	
	// 날짜와 id로 해당 예산 번호 가져오기 
	public int selectBudgetNum(String id, Timestamp dateTime) throws SQLException;
	
	// 예산 번호로 해당 예산 카테고리번호 리스트 가져오기
	public List selectBudgetCategoryNums(int budgetNum) throws SQLException;
	
	//남은 돈 전환
	public void LeftMoneyTransfer() throws SQLException;

	// 아이디로 진행중인 예산의 끝나는 날짜와 지난 예산의 시작 날짜 가져오기
	public List selectBudgetDate(String id) throws SQLException;
	
	
	// 예산 번호로 해당 예산 기록 전부 가져오기
	public List selectAllBudgetByNum(int budgetNum, String pageNum)throws SQLException;
}
