package team.model.dto;

import java.sql.Timestamp;

public class TeamDTO {
	private Integer team_no;
	private String subject;
	private String content;
	private Integer amount;
	private Timestamp start_day;
	private Timestamp end_day;
	private String leader;
	private Integer people;
	private Integer status;
	private Integer isopen;
	private Integer password;
	
	
	public Integer getTeam_no() {
		return team_no;
	}
	public void setTeam_no(Integer team_no) {
		this.team_no = team_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
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
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public Integer getPeople() {
		return people;
	}
	public void setPeople(Integer people) {
		this.people = people;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsopen() {
		return isopen;
	}
	public void setIsopen(Integer isopen) {
		this.isopen = isopen;
	}
	public Integer getPassword() {
		return password;
	}
	public void setPassword(Integer password) {
		this.password = password;
	}
}
