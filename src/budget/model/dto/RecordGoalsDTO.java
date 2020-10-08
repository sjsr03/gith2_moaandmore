package budget.model.dto;

import java.sql.Timestamp;

public class RecordGoalsDTO {
	private int record_no;
	private int goal_no;
	private String id;
	private Timestamp reg;
	private int amount;
	public int getRecord_no() {
		return record_no;
	}
	public void setRecord_no(int record_no) {
		this.record_no = record_no;
	}
	public int getGoal_no() {
		return goal_no;
	}
	public void setGoal_no(int goal_no) {
		this.goal_no = goal_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	
}
