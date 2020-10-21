package closing.model.dto;

import java.sql.Timestamp;

public class ClosingAccountDTO {
	private Integer article_no;
	private String content;
	private String subject;
	private String id;
	private Integer budget_no;
	private Timestamp write_day;
	
	
	public Integer getArticle_no() {
		return article_no;
	}
	public void setArticle_no(Integer article_no) {
		this.article_no = article_no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getBudget_no() {
		return budget_no;
	}
	public void setBudget_no(Integer budget_no) {
		this.budget_no = budget_no;
	}
	public Timestamp getWrite_day() {
		return write_day;
	}
	public void setWrite_day(Timestamp write_day) {
		this.write_day = write_day;
	}
}
