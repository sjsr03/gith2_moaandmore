package category.service.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import category.model.dao.CategoryDAO;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryDAO categoryDAO = null;
	
	//회원 한명의 지출카테고리 전체 불러오기
	@Override
	public List selectAllById(String id) throws SQLException {
		List list = categoryDAO.selectAllById(id); 
		return list;
	}
	
	//회원 한명의 수입카테고리 전체 불러오기
	@Override
	public List selectAllIncomeCategoryById(String id) throws SQLException {
		
			List list = categoryDAO.selectAllIncomeCategoryById(id);
		
		
		return list;
	}

	//수입 카테고리 추가하기
	@Override
	public void addIncomeCategory(String category_name,String id) throws SQLException {
		
		
		
		categoryDAO.addIncomeCategory(category_name,id);
	}
	
	//지출 카테고리 추가하기
	@Override
	public void addOutcomeCategory(String category_name,String id) throws SQLException {
		
		categoryDAO.addOutcomeCategory(category_name,id); 
	}
	
	//지출 카테고리 이름 수정하기
	@Override
	public void updateoutcomeCategory(int category_no, String newName,String id) throws SQLException {
		
		List outcomeCategoryNames = categoryDAO.selectAllById(id);
		
		categoryDAO.updateoutcomeCategory(category_no,newName,id);
		
		
	}
	//수입 카테고리 이름 수정하기
	@Override
	public void updateincomeCategory(int category_no, String newName, String id) throws SQLException {
		
		categoryDAO.updateincomeCategory(category_no,newName,id);
		
	}
	
	// 카테고리 번호로 카테고리 이름을 뽑아오기
	@Override
	public HashMap selectBudgetCategoryNames(List categoryNums) throws SQLException {
		HashMap categoryNames = categoryDAO.selectBudgetCategoryNames(categoryNums);
		return categoryNames;
	}
	//지출 카테고리 삭제하기
	@Override
	public void deleteOutcomeCategory(int category_no, String id) throws SQLException {
		
		categoryDAO.deleteOutcomeCategory(category_no,id);
		
	}
	//수입 카테고리 삭제하기
	@Override
	public void deleteIncomeCategory(int category_no, String id) throws SQLException {
		
		categoryDAO.deleteIncomeCategory(category_no,id);
		
	}
	
	//회원 한명의 지출카테고리명 가져오기
	@Override
	public List selectOutcomeCategoryNamesbyId(String id) throws SQLException {
		
		List outcomeCategoryNames = categoryDAO.selectOutcomeCategoryNamesbyId(id);
		
		return outcomeCategoryNames;
	}
	
	//회원 한명의 수입 카테고리 가져오기
	@Override
	public List selectIncomeCategoryNamesbyId(String id) throws SQLException {
		
		List incomeCategoryNames = categoryDAO.selectIncomeCategoryNamesbyId(id);
		
		return incomeCategoryNames;
		
	}

	//카테고리 넘으로 budget테이블 정보 가져오기
	@Override
	public int selectCategoryInfo(int category_no,String id) throws SQLException {
		
		int budgetCount = categoryDAO.selectBudgetInfo(category_no,id);
		int nobudgetCount = categoryDAO.selectNobudgetInfo(category_no,id);
		
		
		int exist = 0;
		if(budgetCount >0 || nobudgetCount >0) {
			exist = 1;
		}
		
		return exist;
	}

	
}
