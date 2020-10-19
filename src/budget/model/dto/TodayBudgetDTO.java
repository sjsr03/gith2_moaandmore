package budget.model.dto;

import java.sql.Timestamp;

public class TodayBudgetDTO {
	private String id;
	private int category_no;
	private int category_today;
	private Timestamp reg;
	private int budget_no;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCategory_no() {
		return category_no;
	}
	public void setCategory_no(int category_no) {
		this.category_no = category_no;
	}
	public int getCategory_today() {
		return category_today;
	}
	public void setCategory_today(int category_today) {
		this.category_today = category_today;
	}
	public Timestamp getReg() {
		return reg;
	}
	public void setReg(Timestamp reg) {
		this.reg = reg;
	}
	public int getBudget_no() {
		return budget_no;
	}
	public void setBudget_no(int budget_no) {
		this.budget_no = budget_no;
	}
	
	
	
}
