package ranking.service;

import java.util.List;

import ranking.model.dto.RecordRankDTO;

public interface RankingService {

	//record_rank테이블의 cnt, rank_sum 업데이트
	public void updateRecordRank(RecordRankDTO dto);
	
	//tatal_rank테이블 업데이트 
	public void updateTotalRank();
}
