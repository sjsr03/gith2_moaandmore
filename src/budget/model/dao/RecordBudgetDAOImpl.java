package budget.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.SearchForRecordDTO;

@Repository
public class RecordBudgetDAOImpl implements RecordBudgetDAO {

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	// 예산지출 내역 입력
	@Override
	public int insertBudget(BudgetDTO budgetDTO) throws SQLException {

		System.out.println("Budgetcategoryno : " + budgetDTO.getCategory_no());
		sqlSession.update("record.updateCurrentBudget", budgetDTO);	//총예산 현재값에서 차감

		sqlSession.insert("record.insertBudget", budgetDTO);
		// budget 테이블의 예산 구분번호 리턴(방금 +1된 시퀀스값)
		int budget_outcome_no = budgetDTO.getBudget_outcome_no();
		return budget_outcome_no;
	}
	// 예산 지출 세부내역 입력
	@Override
	public void insertBudgetDetail(BudgetDetailDTO budgetDetailDTO) throws SQLException {
		sqlSession.insert("record.insertBudgetDetail", budgetDetailDTO);
				
	}
	
	//////////////////////////////// 내역들 가져오기 //////////////////////////////////
	
	// 예산번호와 날짜로 예산 지출내역 가져오기 
	@Override
	public List selectAllBudgetByNumDate(int budgetNum, int startRow, int endRow, String searchDate) throws SQLException {		
		List budgetRecordList = new ArrayList();
		
		Map para = new HashMap();
		//System.out.println("DAO에서 startRow : " + startRow);
		//System.out.println("DAO에서 endRow : " + endRow);
		para.put("budgetNum", budgetNum);
		para.put("startRow", startRow);
		para.put("endRow", endRow);
		para.put("searchDate", searchDate);
		
		budgetRecordList = sqlSession.selectList("record.selectBudgetRecord", para); 
		return budgetRecordList;
	}
	// 예산 번호와 날짜로 예산 지출 내역의 개수 가져오기
	@Override
	public int countAllBudgetByNumDate(int budgetNum, String searchDate) throws SQLException {
		int count = 0;
		Map para = new HashMap();
		para.put("budgetNum", budgetNum);
		para.put("searchDate", searchDate);
		//System.out.println("DAO countAllBudgetByNumDate 의 budgetNum : " +budgetNum);
		//System.out.println("DAO countAllBudgetByNumDate 의 searchDate :" + searchDate);
		count = sqlSession.selectOne("record.countBudgetRecord", para);
		return count;
	}
	
	// 예산번호, 키워드, 날짜로 예산 지출 내역 가져오기 
	@Override
	public List selectAllBudgetByNumDateKeyword(int budgetNum, int startRow, int endRow, String keyword, String searchDate) throws SQLException {		
		List budgetRecordList = new ArrayList();
		
		Map para = new HashMap();
		//System.out.println("DAO에서 startRow : " + startRow);
		//System.out.println("DAO에서 endRow : " + endRow);
		para.put("budgetNum", budgetNum);
		para.put("startRow", startRow);
		para.put("endRow", endRow);
		para.put("keyword", keyword);
		para.put("searchDate", searchDate);
		
		budgetRecordList = sqlSession.selectList("record.selectBudgetRecordByKeyword", para); 
		return budgetRecordList;
	}
	
	
	// 예산번호, 키워드, 날짜로 예산 지출 내역의 개수 가져오기 
	@Override
	public int countAllBudgetByNumDateKeyword(int budgetNum, String keyword, String searchDate) throws SQLException {
		//System.out.println("키워드 확인 >>>" + keyword);
		int count = 0;
		Map para = new HashMap();
		para.put("budgetNum", budgetNum);
		para.put("keyword", keyword);
		para.put("searchDate", searchDate);
		count = sqlSession.selectOne("record.countBudgetRecordByKeyword", para);
		return count;
	}
	
	@Override
	public int deleteBudgetRecord(int budget_outcome_no) throws SQLException {
		int result = 0;
		result = sqlSession.delete("record.deleteBudgetRecord", budget_outcome_no);
		return result;
	}
	
	@Override
	public int selectSumFromDateAndCatNo(HashMap map) throws SQLException {
		if(sqlSession.selectOne("record.selectSumFromDateAndCatNo", map)==null) {
			return 0;
		} else {
			return sqlSession.selectOne("record.selectSumFromDateAndCatNo", map);
		}
	}
	@Override
	public void modifyBudgetRecord(BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO) throws SQLException {
		int result = 0;
		//System.out.println(budgetDTO.toString());
		//System.out.println(budgetDetailDTO.toString());
		
		sqlSession.selectOne("record.modifyBudgetRecord", budgetDTO);
		sqlSession.selectOne("record.modifyBudgetDetailRecord", budgetDetailDTO);
		
	}
	
	// 아이디로 예산 기록 총 개수 가져오기
	@Override
	public int CountBudgetRecordById(SearchForRecordDTO searchForRecordDTO) throws SQLException {
		int count = sqlSession.selectOne("record.countBudgetRecordById", searchForRecordDTO);
		return count;
	}
	
	// 아이디와 키워드로  예산기록 총 개수 가져오기
	@Override
	public int CountBudgetRecordByIdKeyword(SearchForRecordDTO searchForRecordDTO)throws SQLException{
		int count = sqlSession.selectOne("record.countBudgetRecordByIdKeyword", searchForRecordDTO);
		//System.out.println("budget DAO 키워드 포함 개수 : " + count);
		return count;
	}
	
	@Override
	public BudgetDTO selectRecordByNo(int num) throws SQLException {
		return sqlSession.selectOne("record.selectRecordByNo", num);
	}
	
}
