package ranking.model.dto;

public class TotalRankDTO {

	private String id; // fk(member테이블의 nickname)
	
	
	public TotalRankDTO() {}
	
	public TotalRankDTO(int total_rank, String id) {
		super();
		this.id = id;
	}
	
	public String getNickname() {
		return id;
	}
	public void setNickname(String id) {
		this.id = id;
	}
	
}
