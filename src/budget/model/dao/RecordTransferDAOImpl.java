package budget.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.RecordTransferDTO;

@Repository
public class RecordTransferDAOImpl implements RecordTransferDAO {

	@Autowired
	private SqlSessionTemplate sqlSession = null;
	
	@Override
	public void insertRecordTransfer(List list) {
		for(Object obj:list) {
			RecordTransferDTO RTdto = (RecordTransferDTO) obj;
			sqlSession.insert("recordTransfer.insertRecordTransfer",RTdto);
			sqlSession.update("leftMoney.updateMinusLeftMoney",RTdto);
		}
		
		if(((RecordTransferDTO)list.get(0)).getTarget_table().equals("budget")) {	//예산에 추가
			
		} else {	//목표에 추가
			
		}
		
	}
}
