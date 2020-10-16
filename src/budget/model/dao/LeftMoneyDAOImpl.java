package budget.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.HashAttributeSet;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LeftMoneyDAOImpl implements LeftMoneyDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	@Override
	//회원의 남은 돈 정보 가져오기
	public List selectAllById(String id) throws SQLException {
		List list = sqlSession.selectList("leftMoney.selectAllById", id);
		return list;
	}
	@Override
	public String selectLastLoginReg(String id) throws SQLException {
		String reg = sqlSession.selectOne("leftMoney.selectLastLoginReg", id);
		return reg;
	}
	
	@Override
	public void insertZero(int budget_no, int category_no, String id) {
		HashMap map = new HashMap();
		map.put("budget_no", budget_no);
		map.put("category_no", category_no);
		map.put("id", id);
		
		sqlSession.insert("leftMoney.insertZero", map);
	}
	
	@Override
	public void updateLeftMoney(int amount, int category_no, String id) {
		HashMap map = new HashMap();
		map.put("amount", amount);
		map.put("category_no", category_no);
		map.put("id", id);
		
		sqlSession.update("leftMoney.updateLeftMoney", map);
	}
	
	@Override
	public int selectCurrentLeftMoneySum(String id) {
		if (sqlSession.selectOne("leftMoney.selectCurrentLeftMoneySum", id) == null ) {
			return 0; 
		} else {
			return sqlSession.selectOne("leftMoney.selectCurrentLeftMoneySum", id);
		}
	}
}
