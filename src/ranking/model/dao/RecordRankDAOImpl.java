package ranking.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ranking.model.dto.RecordRankDTO;


@Repository
public class RecordRankDAOImpl implements RecordRankDAO {

	@Autowired
	SqlSessionTemplate sqlSession =null;
	
	
	@Override
	public void updateOne(RecordRankDTO dto) {
		
	}

}
