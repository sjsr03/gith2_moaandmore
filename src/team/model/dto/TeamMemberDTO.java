package team.model.dto;

public class TeamMemberDTO {
	private int team_no;
	private String id;
	private String nickname;
	private int saving;
	private int final_rank;
	private int tmp_rank; //db에는 없는 컬럼. 임시 등수용
	private int is_join;
	
	
	

	public TeamMemberDTO() {};

	public TeamMemberDTO(int team_no, String id, String nickname, int saving, int final_rank, int tmp_rank, int is_join) {
		super();
		this.team_no = team_no;
		this.id = id;
		this.nickname = nickname;
		this.saving = saving;
		this.final_rank = final_rank;
		this.tmp_rank = tmp_rank;
		this.is_join = is_join;
	}
	
	public int getIs_join() {
		return is_join;
	}

	public void setIs_join(int is_join) {
		this.is_join = is_join;
	}

	public int getTmp_rank() {
		return tmp_rank;
	}
	public void setTmp_rank(int tmp_rank) {
		this.tmp_rank = tmp_rank;
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


	public int getFinal_rank() {
		return final_rank;
	}


	public void setFinal_rank(int final_rank) {
		this.final_rank = final_rank;
	}

	@Override
	public String toString() {
		return "TeamMemberDTO [team_no=" + team_no + ", id=" + id + ", nickname=" + nickname + ", saving=" + saving
				+ ", final_rank=" + final_rank + ", tmp_rank=" + tmp_rank + ", is_join=" + is_join + "]";
	}

}
