package closing.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import closing.model.dto.ClosingAccountCommentDTO;
import closing.model.dto.ClosingAccountDTO;

@Repository
public class ClosingAccountDAOImpl implements ClosingAccountDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession = null;
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int getClosingArticleCount() throws SQLException {
		int count = sqlSession.selectOne("closing.countClosingAccountAll");
		
		return count;
	}

	@Override
	public List getClosingArticles(int start, int end) throws SQLException {
		HashMap map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		
		List list = sqlSession.selectList("closing.selectClosingAccountAll", map);
		
		return list;
	}

	@Override
	public ClosingAccountDTO getClosingArticleOne(int article_no) throws SQLException {
		return sqlSession.selectOne("closing.selectOneClosingAccount", article_no);
	}
	
	@Override
	public List getClosingCommentArticles(int article_no, int start, int end) throws SQLException {
		HashMap map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("article_no", article_no);
		
		List list = sqlSession.selectList("closing.selectClosingAccountCommentAll", map);
		
		return list;
	}
	
	@Override
	public int getClosingCommentArticleCount(int article_no) throws SQLException{
		int count = sqlSession.selectOne("closing.countClosingAccountCommentAll", article_no);
		
		return count;
	}

	@Override
	public void insertClosingAccountComment(ClosingAccountCommentDTO dto) throws SQLException {
		sqlSession.insert("closing.insertClosingAccountComment", dto);
	}

}
