package ranking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ranking.model.dao.RecordRankDAOImpl;
import ranking.model.dto.RecordRankDTO;


@Service
public class RankingServiceImpl implements RankingService{
	
	
	@Autowired
	RecordRankDAOImpl recordRankDAO = null;

	
	
	@Override
	public void updateRecordRank(RecordRankDTO dto) {
		
		
		recordRankDAO.updateOne(dto);
		
	}

	@Override
	public void updateTotalRank() {
		// TODO Auto-generated method stub
		
	}

}
