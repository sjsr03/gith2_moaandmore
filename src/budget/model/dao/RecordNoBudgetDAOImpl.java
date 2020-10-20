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
		System.out.println("날짜"+searchForRecordDTO.getSearchDate());
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

	
	// 아이디, 타입으로 예산 기록 총 개수 가져오기
	@Override
	public int CountBudgetRecordById(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		System.out.println("dao11111111111");
		int count = sqlSession.selectOne("record.countBudgetRecordById", searchForRecordDTO);
		
		System.out.println("dao1111123123 : "+ count);
		return count;
	}
	// 아이디, 타입으로 예산외 기록 총 개수 가져오기
	@Override
	public int CountNoBudgetRecordById(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		System.out.println("dao22222222222");
		int count = sqlSession.selectOne("record.countNoBudgetRecordById", searchForRecordDTO);
		System.out.println("예산외!!!!!!!!!개수 : " + count);
		return count;
	}

	// 아이디, 타입으로 예산외 총 기록 가져오기 
	@Override
	public List selectNobudgetRecord(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		System.out.println("dao3333333333333");
		List recordList = sqlSession.selectList("record.selectNobudgetRecord", searchForRecordDTO);
		return recordList;
	}
	// 아이디, 타입으로 예산+예산외 총 기록 가져오기 
	@Override
	public List selectAllRecord(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		System.out.println("dao44444");
		System.out.println("startRow!!!!!!! : " + searchForRecordDTO.getStartRow());
		System.out.println("enRow!!!!!!! : " + searchForRecordDTO.getEndRow());
		
		List recordList = sqlSession.selectList("record.selectRecord", searchForRecordDTO);
		System.out.println("dao44에서 사이즈 : " + recordList.size());
		
		return recordList;
	}

	
}
