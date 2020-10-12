package budget.model.dao;

import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;

@Repository
public class RecordNoBudgetDAOImpl implements RecordNoBudgetDAO{

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	// 예산 외 수입지출 내역입력
	@Override
	public int insertNoBudget(NoBudgetDTO noBudgetDTO) throws SQLException {
		sqlSession.insert("record.insertNoBudget", noBudgetDTO);
		
		// noBudget 테이블의 예산 구분번호 리턴(방금 +1된 시퀀스값)
		int nobudget_no = noBudgetDTO.getNobudget_no();
		return nobudget_no;
	}
	// 예산 외 수입지출 상세 내역 입력
	@Override
	public void insertNoBudgetDetailDTO(NoBudgetDetailDTO noBudgetDetailDTO) throws SQLException {
		sqlSession.insert("record.insertNoBudgetDetail", noBudgetDetailDTO);
		
	}

}
