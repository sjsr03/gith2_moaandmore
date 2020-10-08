package budget.model.dto;

import java.sql.Timestamp;

public class BudgetDTO {
	// 예산 지출 내역 DTO
	private int budget_outcome_no;
	private int budget_no;
	private String id;
	private int category_no;
	private int amount;
	private Timestamp date;
	
	public int getBudget_outcome_no() {
		return budget_outcome_no;
	}
	public void setBudget_outcome_no(int budget_outcome_no) {
		this.budget_outcome_no = budget_outcome_no;
	}
	public int getBudget_no() {
		return budget_no;
	}
	public void setBudget_no(int budget_no) {
		this.budget_no = budget_no;
	}
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	
}
