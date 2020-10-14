package budget.model.dao;

import java.util.List;

import budget.model.dto.RecordGoalsDTO;
import budget.model.dto.TotalBudgetDetailDTO;

public interface RecordTransferDAO {
	//전환 기록 남기기
	public void insertRecordTransfer(List list);
	//목표에 전환기록 남기기
	public void insertRecordGoals(RecordGoalsDTO dto);
	//예산에 전환하기
	public void updateRecordTBD(TotalBudgetDetailDTO target, List fromList);
}
