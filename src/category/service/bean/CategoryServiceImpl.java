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
	
	@Override
	public List selectAllById(String id) throws SQLException {
		List list = categoryDAO.selectAllById(id); 
		return list;
	}

	@Override
	public List selectAllIncomeCategoryById(String id) throws SQLException {
		
			List list = categoryDAO.selectAllIncomeCategoryById(id);
		
		
		return list;
	}

	
	
	@Override
	public void addIncomeCategory(String category_name,String id) throws SQLException {
		
		
		
		categoryDAO.addIncomeCategory(category_name,id);
		
		
	}

	@Override
	public void addOutcomeCategory(String category_name,String id) throws SQLException {
		
		
		
		categoryDAO.addOutcomeCategory(category_name,id); 
	}
}
