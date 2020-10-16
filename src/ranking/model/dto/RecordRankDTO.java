package ranking.model.dto;

public class RecordRankDTO {

	private String id;
	private int cnt;
	private int rank_sum;
	
	public RecordRankDTO() {}
	
	public RecordRankDTO(String id, int cnt, int rank_sum) {
		super();
		this.id = id;
		this.cnt = cnt;
		this.rank_sum = rank_sum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getRank_sum() {
		return rank_sum;
	}

	public void setRank_sum(int rank_sum) {
		this.rank_sum = rank_sum;
	}

	@Override
	public String toString() {
		return "RecordRankDTO [id=" + id + ", cnt=" + cnt + ", rank_sum=" + rank_sum + "]";
	}	
}
