package category.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {

	//회원 한 명의 카테고리 전체 가져오기
	public List selectAllById(String id) throws SQLException;
	//기본카테고리 설정(회원가입할때 자동으로)
	public void insertCategory(String id) throws SQLException;
	
}
