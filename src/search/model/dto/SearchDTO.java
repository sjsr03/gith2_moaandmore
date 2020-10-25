package search.model.dto;

import java.sql.Timestamp;

public class SearchDTO {
	private int budget_no;
	private int category_no;
	private int amount;
	private Timestamp reg;
	private String content;
	
	public SearchDTO() {}
	
	public SearchDTO(int budget_no, int category_no, int amount, Timestamp reg, String content) {
		super();
		this.budget_no = budget_no;
		this.category_no = category_no;
		this.amount = amount;
		this.reg = reg;
		this.content = content;
	}

	public int getBudget_no() {
		return budget_no;
	}
	public void setBudget_no(int budget_no) {
		this.budget_no = budget_no;
	}
	public int getCategory_no() {
		return category_no;
	}
	public void setCategory_no(int category_no) {
		this.category_no = category_no;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Timestamp getReg() {
		return reg;
	}
	public void setReg(Timestamp reg) {
		this.reg = reg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SearchDTO [budget_no=" + budget_no + ", category_no=" + category_no + ", amount=" + amount + ", reg="
				+ reg + ", content=" + content + "]";
	}
	
}
