package budget.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;
import budget.model.dto.SearchForRecordDTO;

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
	
	// 예산외 수입or지출 아이디와 날짜로 개수가져오기 
	@Override
	public int CountAllNoBudgetById(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		int count = 0;
		System.out.println("디에이오에서~~");
		System.out.println("아이디"+searchForRecordDTO.getId());	
		System.out.println("날짜"+searchForRecordDTO.getSerachDate());
		System.out.println("타입"+searchForRecordDTO.getType());
		count = sqlSession.selectOne("record.countNoBudgetRecord", searchForRecordDTO);
		System.out.println("count ::: " + count);
		
		return count;
	}
	@Override
	public List selectAllNoBudget(SearchForRecordDTO searchForRecordDTO)throws SQLException {
		List noBudgetRocordList = new ArrayList();
		
		noBudgetRocordList = sqlSession.selectList("record.selectNoBudgetRecord", searchForRecordDTO);
		//System.out.println("노버겟~리스트~~ : " + noBudgetRocordList.size());
		return noBudgetRocordList;
	}
}
