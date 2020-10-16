package ranking.model.dto;

public class TotalRankDTO {

	private int total_rank; // 10등까지 
	private String nickname;
	
	
	public TotalRankDTO() {}
	
	public TotalRankDTO(int total_rank, String nickname) {
		super();
		this.total_rank = total_rank;
		this.nickname = nickname;
	}
	public int getTotal_rank() {
		return total_rank;
	}
	public void setTotal_rank(int total_rank) {
		this.total_rank = total_rank;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
