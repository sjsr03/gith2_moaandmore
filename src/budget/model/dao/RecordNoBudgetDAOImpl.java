package budget.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.AllRecordDTO;
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
	
	// 예산외 수입or지출 아이디와 날짜, 타입으로 개수가져오기 
	@Override
	public int CountAllNoBudgetById(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		int count = 0;
		
		count = sqlSession.selectOne("record.countNoBudgetRecord", searchForRecordDTO);
		//System.out.println("count : " + count);
		
		return count;
	}
	
	// 예산 외 수입 or 지출 아이디와 날짜, 타입으로 내역 가져오기  selectNobudgetRecord 과 겹침;
	@Override
	public List selectAllNoBudget(SearchForRecordDTO searchForRecordDTO)throws SQLException {
		List noBudgetRocordList = new ArrayList();
		
		noBudgetRocordList = sqlSession.selectList("record.selectNoBudgetRecord", searchForRecordDTO);
		//System.out.println("노버겟~리스트~~ : " + noBudgetRocordList.size());
		return noBudgetRocordList;
	}
////////////////////////////////////////////////////////////////
	
	// 아이디, 타입, 예산외 기록 총 개수 가져오기
	@Override
	public int CountNoBudgetRecordById(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		
		int count = sqlSession.selectOne("record.countNoBudgetRecordById", searchForRecordDTO);
		//System.out.println("예산외!!!!!!!!!개수 : " + count);
		return count;
	}

	// 아이디로 예산외 총 기록 가져오기(수입+지출 같이 가져옴) 
	@Override
	public List selectNobudgetRecordById(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		
		List recordList = sqlSession.selectList("record.selectNobudgetRecordAllType", searchForRecordDTO);
		return recordList;
	}
	// 아이디, 타입, 날짜로 예산외 내역 총 개수 가져오기(키워드 포함)
	@Override
	public int CountAllNoBudgetByIdKeyword(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		int count = sqlSession.selectOne("record.countNoBudgetRecordByKeyword", searchForRecordDTO);
		return count;
	}
	// 아이디, 타입, 날짜로 예산외 내역 총 기록 가져오기(키워드 포함)
	@Override
	public List selectAllNoBudgetKeyword(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		List recordList = sqlSession.selectList("record.selectNoBudgetRecordByKeyword", searchForRecordDTO);
		return recordList;
	}
	
	/////////////////////////////////////////////////
	
	// 아이디, 타입으로 예산+예산외 총 기록 가져오기 
	@Override
	public List selectAllRecord(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		//System.out.println("startRow!!!!!!! : " + searchForRecordDTO.getStartRow());
		//System.out.println("enRow!!!!!!! : " + searchForRecordDTO.getEndRow());
		
		List recordList = sqlSession.selectList("record.selectRecords", searchForRecordDTO);
		//System.out.println("dao에서 사이즈 체크함 !! : " + recordList.size());
		return recordList;
	}
	// 기록 삭제 
	@Override
	public int DeleteNoBudgetRecord(int number) throws SQLException {
		int result = 0;
		result = sqlSession.delete("record.deleteNoBudgetRecord", number);
		return result;
	}
	@Override
	public void modifyNoBudgetRecord(NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBudgetDetailDTO) throws SQLException {
		int result = 0;
		//System.out.println(noBudgetDTO.toString());
		//System.out.println(noBudgetDetailDTO.toString());
		
		sqlSession.selectOne("record.modifyNoBudgetRecord", noBudgetDTO);
		sqlSession.selectOne("record.modifyNoBudgetDetailRecord", noBudgetDetailDTO);
	}
	// 아이디, 타입으로 예산외 내역 총 개수 가져오기(키워드 O)
	// 수입, 지출, 수입+지출 다  가져올 수 있음
	@Override
	public int CountNoBudgetRecordByIdKeyword(SearchForRecordDTO searchForRecordDTO) throws SQLException {
	
		int count = sqlSession.selectOne("record.countNoBudgetRecordByIdKeyword", searchForRecordDTO);
		//System.out.println("DAO에서 예산 외 내역 개수 체크 : " + count);
		return count;
	}
	// 아이디, 타입으로 예산외 총 기록 가져오기(키워드 O)
	// 수입+지출 내역을 가져옴 
	@Override
	public List selectNobudgetRecordByIdKeyword(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		List recordList = sqlSession.selectList("record.selectNobudgetRecordAllTypeByKeyword", searchForRecordDTO);
		return recordList;
	}

	// 아이디, 타입으로 예산+예산외 총 기록 가져오기 (키워드 O)
	// 수입+지출 내역을 제외한 나머지는 다 가져올 수 있음
	public List selectAllRecordByIdKeyword(SearchForRecordDTO searchForRecordDTO) throws SQLException{
		List recordList = sqlSession.selectList("record.selectRecordsByIdKeyword", searchForRecordDTO);
		return recordList;
	}
}
