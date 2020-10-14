package budget.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.RecordGoalsDTO;
import budget.model.dto.RecordTransferDTO;
import budget.model.dto.TotalBudgetDetailDTO;

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
	}
	
	@Override
	public void insertRecordGoals(RecordGoalsDTO dto) {
		sqlSession.insert("recordGoals.insertRecordGoal",dto);
	};
	
	@Override
	public void updateRecordTBD(TotalBudgetDetailDTO target, List fromList) {
		sqlSession.update("recordTransfer.updatePlusRecordTBD", target);
		
		for (Object obj : fromList) {
			TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO) obj;
			sqlSession.update("recordTransfer.updateMinusRecordTBD", dto);
		}
	}
}
