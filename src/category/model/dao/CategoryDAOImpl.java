package category.model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	

	@Override
	public List selectAllById(String id) throws SQLException {
		List list = sqlSession.selectList("category.selectAllById", id);
		return list;
	}
	
	@Override
	public void insertCategory(String id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int selectNumByName(String name, String id) throws SQLException {
		HashMap map = new HashMap();
		map.put("category_name", name);
		map.put("id", id);
		return sqlSession.selectOne("category.selectNumByName", map);
	}


	@Override
	public List selectAllIncomeCategoryById(String id) throws SQLException {
		List list = sqlSession.selectList("category.selectAllIncomeCategoryById",id);
		
		
		return list;
	}

	
	@Override
	public void outcomeInsertCategory(String id) throws SQLException {
		
		List list = new ArrayList();
		list.add("식비");
		list.add("교통/차량");
		list.add("문화생활");
		list.add("패션/미용");
		list.add("생활용품");
		list.add("주거/통신");
		list.add("건강");
		list.add("교육");
		list.add("경조사/회비");
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("id", id);
		
	
		
		sqlSession.insert("category.insertOutComeCategory",map);
		
		
	}


	@Override
	public void incomeInsertCategory(String id) throws SQLException {
		
		List list = new ArrayList();
		list.add("월급");
		list.add("보너스");
		list.add("용돈");
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("id",id);
		
		sqlSession.insert("category.insertInComeCategory",map);
		
		
	}


	@Override
	public void addIncomeCategory(String category_name,String id) throws SQLException {
		
		HashMap map = new HashMap();
		map.put("category_name", category_name);
		map.put("id",id);
		
		sqlSession.insert("category.addIncomeCategory", map);
		
		
	}

	@Override
	public void addOutcomeCategory(String category_name,String id) throws SQLException {
		
		
		HashMap map = new HashMap();
		map.put("category_name", category_name);
		map.put("id",id);
		
		sqlSession.insert("category.addoutcomeCategory", map);
		
	}

	//지출카테고리 이름 수정하기
	@Override
	public void updateoutcomeCategory(int category_no, String newName,String id) throws SQLException {
	
		HashMap map = new HashMap();
		map.put("category_no",category_no);
		map.put("newName", newName);
		map.put("id", id);
		
		sqlSession.update("category.updateoutcomeCategory",map);
		
	}
	
	//수입카테고리 이름 수정하기
	@Override
	public void updateincomeCategory(int category_no, String newName, String id) throws SQLException {

		HashMap map = new HashMap();
		map.put("category_no",category_no);
		map.put("newName", newName);
		map.put("id", id);
		
		sqlSession.update("category.updateincomeCategory",map);
		
	}
	
	// 카테고리 번호로 카테고리 이름 가져오기 
	@Override
	public HashMap selectBudgetCategoryNames(List categoryNums) throws SQLException {
		
		Collections.sort(categoryNums);
		
		List categoryNames = sqlSession.selectList("category.selectBudgetCategoryNames", categoryNums);
		HashMap categories = new HashMap();
		for(int i = 0; i < categoryNames.size(); i++) {
			categories.put(categoryNums.get(i), categoryNames.get(i));
		}
		return categories;
	}

	//지출 카테고리 삭제하기
	@Override
	public void deleteOutcomeCategory(int category_no, String id) throws SQLException {
		
		Map map = new HashMap();
		
		map.put("category_no",category_no);
		map.put("id", id);
		
		sqlSession.delete("category.deleteOutcomeCategory", map);
		
	}
	//수입 카테고리 삭제하기
	@Override
	public void deleteIncomeCategory(int category_no, String id) throws SQLException {
		Map map = new HashMap();
		
		map.put("category_no",category_no);
		map.put("id", id);
		
		sqlSession.delete("category.deleteIncomeCategory", map);
		
	}

	//회원 한명의 수출 카테고리명 다 가져오기
	@Override
	public List selectOutcomeCategoryNamesbyId(String id) throws SQLException {

		List outcomeCategoryNames = sqlSession.selectList("category.selectOutcomeCategoryName",id);
		
		return outcomeCategoryNames;
	}
	
	//회원 한명의 수입 카테고리명 다 가져오기
	@Override
	public List selectIncomeCategoryNamesbyId(String id) throws SQLException {
		
		List incomeCategoryNames = sqlSession.selectList("category.selectIncomeCategoryName",id);
		
		return incomeCategoryNames;
	}
	
	//budget테이블에 해당 category_no 내역 있는지 확인하기
	@Override
	public int selectBudgetInfo(int category_no, String id) throws SQLException {
		
		Map map = new HashMap();
		map.put("category_no", category_no);
		map.put("id", id);
		
		int count=sqlSession.selectOne("category.selectBudgetInfo", map);
		return count;
	}
	
	//nobudget테이블에 해당 category_no 내역 있는지 확인하기
	@Override
	public int selectNobudgetInfo(int category_no, String id) throws SQLException {
		
		Map map = new HashMap();
		map.put("category_no", category_no);
		map.put("id", id);
		
		int count=sqlSession.selectOne("category.selectNoBudgetInfo", map);
		return count;
	}
	//total_budget_detail테이블에 해당 category_no 내역 있는지 확인하기
	@Override
	public int selectTotalBudgetDetailInfo(int category_no) throws SQLException {
		
		int count=sqlSession.selectOne("category.selectTotalDetailCategoryInfo",category_no);
		
		return count;
		
	}

	
	
}
