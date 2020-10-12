package team.model.dto;

public class TeamMemberDTO {
	private int team_no;
	private String id;
	private String nickname;
	private int saving;
	
	public TeamMemberDTO() {};
	
	public TeamMemberDTO(int team_no, String id, String nickname, int saving) {
		super();
		this.team_no = team_no;
		this.id = id;
		this.nickname = nickname;
		this.saving = saving;
	}
	public int getTeam_no() {
		return team_no;
	}
	public void setTeam_no(int team_no) {
		this.team_no = team_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSaving() {
		return saving;
	}
	public void setSaving(int saving) {
		this.saving = saving;
	}
	@Override
	public String toString() {
		return "TeamMemberDTO [team_no=" + team_no + ", id=" + id + ", nickname=" + nickname + ", saving=" + saving
				+ "]";
	}
	
	
	
	
	

}
