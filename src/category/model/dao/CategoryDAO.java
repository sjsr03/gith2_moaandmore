package category.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {

	//회원 한 명의 카테고리 전체 가져오기
	public List selectAllById(String id) throws SQLException;
}
