package budget.model.dto;

import java.sql.Timestamp;

public class AllRecordDTO {
	private int budget_outcome_no;
	private int budget_no;
	private String id;
	private int income_category_no;
	private int outcome_category_no;
	private int amount;
	private Timestamp reg;
	private String content;
	private String memo;
	private String img;
	private int nobudget_no;
	private String type;
	
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
	
	public int getIncome_category_no() {
		return income_category_no;
	}
	public void setIncome_category_no(int income_category_no) {
		this.income_category_no = income_category_no;
	}
	public int getOutcome_category_no() {
		return outcome_category_no;
	}
	public void setOutcome_category_no(int outcome_category_no) {
		this.outcome_category_no = outcome_category_no;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getNobudget_no() {
		return nobudget_no;
	}
	public void setNobudget_no(int nobudget_no) {
		this.nobudget_no = nobudget_no;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
