package budget.model.dto;

import java.sql.Timestamp;

public class NoBudgetRecordDTO {
	// 예산 제외 지출/수입 내역 DTO+DetailDTO
	private int nobudget_no;
	private String id;
	private String type;
	private int category_no;
	private int amount;
	private Timestamp reg;
	private String content;
	private String memo;
	private String img;
	
	public int getNobudget_no() {
		return nobudget_no;
	}
	public void setNobudget_no(int nobudget_no) {
		this.nobudget_no = nobudget_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
}
