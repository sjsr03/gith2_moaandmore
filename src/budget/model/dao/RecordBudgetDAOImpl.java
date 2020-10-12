package budget.model.dao;

import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;

@Repository
public class RecordBudgetDAOImpl implements RecordBudgetDAO {

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	// 예산지출 내역 입력
	@Override
	public int insertBudget(BudgetDTO budgetDTO) throws SQLException {
		System.out.println("예산 사용 금액 : " +budgetDTO.getAmount());
		System.out.println("예산안 구분번호 : " +budgetDTO.getBudget_no());
		System.out.println("예산 카테고리  구분번호: " +budgetDTO.getCategory_no());
		System.out.println("아이디  : " +budgetDTO.getId());
		System.out.println("날짜 ㅣ " + budgetDTO.getDate() );
		
		System.out.println("타입먼데 : " + budgetDTO.getDate().getClass().getName());
		
		
		sqlSession.insert("record.insertBudget", budgetDTO);
		System.out.println("시퀀스값111111111 : " + budgetDTO.getBudget_outcome_no());
		// budget 테이블의 예산 구분번호 리턴(방금 +1된 시퀀스값)
		int budget_outcome_no = budgetDTO.getBudget_outcome_no();
		System.out.println("시퀀스값 : " + budget_outcome_no);
		return budget_outcome_no;
	}
	// 예산 지출 세부내역 입력
	@Override
	public void insertBudgetDetail(BudgetDetailDTO budgetDetailDTO) throws SQLException {
		sqlSession.insert("record.insertBudgetDetail", budgetDetailDTO);
		
		
	}

}
