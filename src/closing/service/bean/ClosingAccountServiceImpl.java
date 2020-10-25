package closing.service.bean;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import closing.model.dao.ClosingAccountDAO;
import closing.model.dto.ClosingAccountCommentDTO;
import closing.model.dto.ClosingAccountDTO;

@Service
public class ClosingAccountServiceImpl implements ClosingAccountService{
	
	@Autowired
	private ClosingAccountDAO closingDao = null; 

	@Override
	public int getClosingArticleCount() throws SQLException {
		return closingDao.getClosingArticleCount();
	}

	@Override
	public List getClosingArticles(int start, int end) throws SQLException {
		return closingDao.getClosingArticles(start, end);
	}

	@Override
	public ClosingAccountDTO getClosingArticleOne(int article_no) throws SQLException {
		return closingDao.getClosingArticleOne(article_no);
	}
	
	@Override
	public List getClosingCommentArticles(int article_no, int start, int end) throws SQLException {
		return closingDao.getClosingCommentArticles(article_no, start, end);
	}
	
	@Override
	public int getClosingCommentArticleCount(int article_no) throws SQLException{
		return closingDao.getClosingCommentArticleCount(article_no);
	}

	@Override
	public void insertClosingAccountComment(ClosingAccountCommentDTO dto) throws SQLException {
		closingDao.insertClosingAccountComment(dto);
	}

	@Override
	public void insertClosingAccount(ClosingAccountDTO dto) throws SQLException {
		closingDao.insertClosingAccount(dto);
	}

}
