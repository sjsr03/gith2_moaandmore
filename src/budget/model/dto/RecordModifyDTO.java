package budget.model.dto;

import java.io.File;
import java.sql.Timestamp;

public class RecordModifyDTO {
	private int budget_no;
	// budget_no 는 예산안의 구분번호!!!
	private int uniqueNum;
	// uniqueNum 은 예산 or 지출 or 수입내역의 구분번호(시퀀스값)
	private String id;
	private int income_category_no;
	private int outcome_category_no;
	private int budget_category_no;
	private int amount;
	private String time;
	private String date;
	private String content;
	private String memo;
	private File img;
	public File getImg(){
		return img;
	}
	public void setImg(File img) {
		this.img = img;
	}
	private int nobudget_no;
	private String type;
	
	
	public int getUniqueNum() {
		return uniqueNum;
	}
	public void setUniqueNum(int uniqueNum) {
		this.uniqueNum = uniqueNum;
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
	public int getBudget_category_no() {
		return budget_category_no;
	}
	public void setBudget_category_no(int budget_category_no) {
		this.budget_category_no = budget_category_no;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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

	public int getBudget_no() {
		return budget_no;
	}
	public void setBudget_no(int budget_no) {
		this.budget_no = budget_no;
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
	@Override
	public String toString() {
		return "RecordModifyDTO [uniqueNum=" + uniqueNum + ", id=" + id + ", income_category_no=" + income_category_no
				+ ", outcome_category_no=" + outcome_category_no + ", budget_category_no=" + budget_category_no
				+ ", amount=" + amount + ", time=" + time + ", date=" + date + ", content=" + content + ", memo=" + memo
				+ ", img=" + img + ", nobudget_no=" + nobudget_no + ", type=" + type + "]";
	}

	
	
}
