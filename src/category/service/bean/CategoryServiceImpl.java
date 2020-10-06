package category.service.bean;

import java.sql.SQLException;
import java.util.List;

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
}
