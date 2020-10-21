package category.service.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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
	public String addIncomeCategory(String category_name,String id) throws SQLException {
		
		List incomeCategoryNames = categoryDAO.selectIncomeCategoryNamesbyId(id); //ok
		
		String already = "false";
		
		//수입카테고리 이름 겹치는지 검사
		for(int i=0;i<incomeCategoryNames.size(); i++) {
			String incomeCategoryName = (String)incomeCategoryNames.get(i);
			
			if(incomeCategoryName.equals(category_name)) {
				
				already = "true";
				break;
			}
		}
		//이름이 안겹치면 수정 가능
		if(already == "false") {
			categoryDAO.addIncomeCategory(category_name,id);
		}		
		
		
		return already;
	}
	
	//지출 카테고리 추가하기
	@Override
	public String addOutcomeCategory(String category_name,String id) throws SQLException {
		
		List outcomeCategoryNames = categoryDAO.selectOutcomeCategoryNamesbyId(id); //ok
		//지출카테고리 이름 겹치는지 검사
		String already = "false";
		
		for(int i=0;i<outcomeCategoryNames.size(); i++) {
				String outcomeCategoryName = (String)outcomeCategoryNames.get(i);
			
			if(outcomeCategoryName.equals(category_name)) {

				already = "true";
				break;
			}
		}
		
		//이름이 안겹치면 수정 가능
		if(already == "false") {
			categoryDAO.addOutcomeCategory(category_name,id);
		}
		 
	
		return already;
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
	public int deleteOutcomeCategory(int category_no, String id) throws SQLException {
		
		// 각각 해당 테이블에 category_no 내역이 있는지 확인하기 (있으면 삭제 불가) 
		int budgetCount = categoryDAO.selectBudgetInfo(category_no,id);
		int nobudgetCount = categoryDAO.selectNobudgetInfo(category_no,id);
		int totalBudgetDetailcount = categoryDAO.selectTotalBudgetDetailInfo(category_no);
		
		int exist = 0;
		
		if(budgetCount >0 || nobudgetCount >0 || totalBudgetDetailcount >0) {
			exist = 1;
		}
		
		//다른 테이블에 category_no 정보가 없으면 정상적으로 삭제
		if(exist == 0) {
			categoryDAO.deleteOutcomeCategory(category_no,id);
		}else if(exist ==1) {
			System.out.println("삭제불가 "+exist);
		}
		
		
		return exist;
		
		
		
	}
	//수입 카테고리 삭제하기
	@Override
	public int deleteIncomeCategory(int category_no, String id) throws SQLException {
		
		int nobudgetCount = categoryDAO.selectNobudgetInfo(category_no,id);
		int totalBudgetDetailcount = categoryDAO.selectTotalBudgetDetailInfo(category_no);
		
		int exist = 0;
		
		if(nobudgetCount >0 || totalBudgetDetailcount >0) {
			exist = 1;
		}
		
		//다른 테이블에 category_no 정보가 없으면 정상적으로 삭제
		if(exist == 0) {
			categoryDAO.deleteIncomeCategory(category_no,id);
		}else if(exist ==1) {
			System.out.println("삭제불가 "+exist);
		}
		
		
		return exist;
		
		
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
		
		// 각각 해당 테이블에 category_no 내역이 있는지 확인하기 (있으면 삭제 불가) 
		int budgetCount = categoryDAO.selectBudgetInfo(category_no,id);
		int nobudgetCount = categoryDAO.selectNobudgetInfo(category_no,id);
		int totalBudgetDetailcount = categoryDAO.selectTotalBudgetDetailInfo(category_no);
		
		int exist = 0;
		if(budgetCount >0 || nobudgetCount >0 || totalBudgetDetailcount >0) {
			exist = 1;
		}
		
		return exist;
	}

	

	
}
