package budget.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LeftMoneyDAOImpl implements LeftMoneyDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	//회원의 남은 예산 정보 가져오기
	public List selectAllById(String id) throws SQLException {
		List list = sqlSession.selectList("leftMoney.selectAllById", id);
		return list;
	}
}
