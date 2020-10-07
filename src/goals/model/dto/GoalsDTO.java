package goals.model.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class GoalsDTO {
	private int goal_no;
	private String id;
	private String subject;//목표명
	private int target_money; //목표금액
	private int saving; //모은돈
	private Timestamp start_day;// 시작날짜
	private Timestamp end_day;
	private char public_ch; //개인0 그룹1
	private char public_type;// 비공개0 공개0
	private int team_no;//fk. 그룹목표인 경우 id, 개인인경우 -1
	
	public GoalsDTO(int goal_no, String id, String subject, int target_money, int saving, Timestamp start_day,
			Timestamp end_day, char public_ch, char public_type, int team_no) {
		super();
		this.goal_no = goal_no;
		this.id = id;
		this.subject = subject;
		this.target_money = target_money;
		this.saving = saving;
		this.start_day = start_day;
		this.end_day = end_day;
		this.public_ch = public_ch;
		this.public_type = public_type;
		this.team_no = team_no;
	}
	
	public GoalsDTO() {
		
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getTarget_money() {
		return target_money;
	}
	public void setTarget_money(int target_money) {
		this.target_money = target_money;
	}
	public int getSaving() {
		return saving;
	}
	public void setSaving(int saving) {
		this.saving = saving;
	}
	
	public Timestamp getStart_day() {
		return start_day;
	}
	public void setStart_day(Timestamp start_day) {
		this.start_day = start_day;
	}
	public Timestamp getEnd_day() {
		return end_day;
	}
	public void setEnd_day(Timestamp end_day) {
		this.end_day = end_day;
	}
	public char getPublic_ch() {
		return public_ch;
	}
	public void setPublic_ch(char public_ch) {
		this.public_ch = public_ch;
	}
	public char getPublic_type() {
		return public_type;
	}
	public void setPublic_type(char public_type) {
		this.public_type = public_type;
	}
	public int getTeam_no() {
		return team_no;
	}
	public void setTeam_no(int team_no) {
		this.team_no = team_no;
	}
	@Override
	public String toString() {
		return "GoalsDTO [goal_no=" + goal_no + ", id=" + id + ", subject=" + subject + ", target_money=" + target_money
				+ ", saving=" + saving + ", start_day=" + start_day + ", end_day=" + end_day + ", public_ch="
				+ public_ch + ", public_type=" + public_type + ", team_no=" + team_no + "]";
	}
	
	
	

	
}
