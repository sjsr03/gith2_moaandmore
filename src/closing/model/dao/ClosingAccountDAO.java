package closing.model.dao;

import java.sql.SQLException;
import java.util.List;

import closing.model.dto.ClosingAccountDTO;

public interface ClosingAccountDAO {
	//게시글 개수 검색
	public int getClosingArticleCount() throws SQLException;
	//게시글 범위 지정 가져오기
	public List getClosingArticles(int start, int end) throws SQLException;
	//결산 고유번호로 게시글 하나 가져오기
	public ClosingAccountDTO getClosingArticleOne(int article_no) throws SQLException;
	//댓글 범위 지정 가져오기
	public List getClosingCommentArticles(int article_no, int start, int end) throws SQLException;
	//댓글 개수 검색
	public int getClosingCommentArticleCount(int article_no) throws SQLException;
}
