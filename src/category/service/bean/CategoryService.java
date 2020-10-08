package category.service.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface CategoryService {

	//회원 한명의 지출카테고리 전체 불러오기
	public List selectAllById(String id) throws SQLException;

	//회원 한명의 수입카테고리 전체 불러오기
	public List selectAllIncomeCategoryById(String id) throws SQLException;
	//수입 카테고리 추가하기
	public void addIncomeCategory(String category_name,String id) throws SQLException;
	//지출 카테고리 추가하기
	public void addOutcomeCategory(String category_name, String id) throws SQLException;
	//지출 카테고리 이름 수정하기
	public void updateoutcomeCategory(int category_no, String newName,String id) throws SQLException;
	//수입 카테고리 이름 수정하기
	public void updateincomeCategory(int category_no, String newName,String id) throws SQLException;
	// 카테고리 번호로 카테고리 이름 list 뽑아오기
	public HashMap selectBudgetCategoryNames(List categoryNums) throws SQLException;
	//지출 카테고리 삭제하기
	public void deleteOutcomeCategory(int category_no,String id)throws SQLException;	
	//수입 카테고리 삭제하기
	public void deleteIncomeCategory(int cateogry_no,String id)throws SQLException;
	//회원 한명의 수출 카테고리명 다 가져오기
	public List selectOutcomeCategoryNamesbyId(String id) throws SQLException;
	//회원 한명의 수입 카테고리명 다 가져오기
	public List selectIncomeCategoryNamesbyId(String id) throws SQLException;
	//카테고리 넘으로 budget테이블,nobudget테이블 개수 가져오기
	public int selectCategoryInfo(int category_no,String id)throws SQLException;

}
