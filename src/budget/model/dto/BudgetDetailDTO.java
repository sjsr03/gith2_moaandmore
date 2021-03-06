package budget.model.dto;

public class BudgetDetailDTO {
	
	private int budget_outcome_no;
	private String content;
	private String memo;
	private String img;

	
	public int getBudget_outcome_no() {
		return budget_outcome_no;
	}
	public void setBudget_outcome_no(int budget_outcome_no) {
		this.budget_outcome_no = budget_outcome_no;
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
	@Override
	public String toString() {
		return "BudgetDetailDTO [budget_outcome_no=" + budget_outcome_no + ", content=" + content + ", memo=" + memo
				+ ", img=" + img + "]";
	}
	
	
}
