package budget.model.dto;

import java.sql.Timestamp;

public class RecordTransferDTO {
	private int record_no;
	private String id;
	private int category_no;
	private Timestamp reg;
	private int amount;
	private String target_table;
	private int target_no;
	
	public int getRecord_no() {
		return record_no;
	}
	public void setRecord_no(int record_no) {
		this.record_no = record_no;
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
	public Timestamp getReg() {
		return reg;
	}
	public void setReg(Timestamp reg) {
		this.reg = reg;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTarget_table() {
		return target_table;
	}
	public void setTarget_table(String target_table) {
		this.target_table = target_table;
	}
	public int getTarget_no() {
		return target_no;
	}
	public void setTarget_no(int target_no) {
		this.target_no = target_no;
	}
	
	
}
