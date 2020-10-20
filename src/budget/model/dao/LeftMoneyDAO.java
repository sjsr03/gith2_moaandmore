package budget.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface LeftMoneyDAO {
	//회원의 남은 돈 정보 가져오기
	public List selectAllById(String id) throws SQLException;
	
	//마지막 로그인날짜(남은돈 계산날짜)
	public String selectLastLoginReg(String id) throws SQLException;
	
	//예산생성시 남은돈 0 레코드 추가
	public void insertZero(int budget_no, int category_no, String id);
	
	//해당 카테고리에 남은돈 추가
	public void updateLeftMoney(int amount, int category_no, String id);
	
	//회원의 현재 남은 돈 합산 
	public int selectCurrentLeftMoneySum(String id);
	
	//leftmoney의 reg를 (총예산정보의 시작일로)
	public void updateRegToStartDay(String id);
	
}
