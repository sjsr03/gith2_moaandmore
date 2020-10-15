package budget.model.dto;

public class TotalBudgetDetailDTO {
	private int category_no;
	private int budget_no;
	private int category_budget;
	private int category_current;
	public int getCategory_no() {
		return category_no;
	}
	public void setCategory_no(int category_no) {
		this.category_no = category_no;
	}
	public int getBudget_no() {
		return budget_no;
	}
	public void setBudget_no(int budget_no) {
		this.budget_no = budget_no;
	}
	public int getCategory_budget() {
		return category_budget;
	}
	public void setCategory_budget(int category_budget) {
		this.category_budget = category_budget;
	}
	public int getCategory_current() {
		return category_current;
	}
	public void setCategory_current(int category_current) {
		this.category_current = category_current;
	}
	
	
}
