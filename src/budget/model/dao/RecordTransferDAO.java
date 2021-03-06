package budget.model.dao;

import java.util.List;

import budget.model.dto.RecordGoalsDTO;
import budget.model.dto.TotalBudgetDetailDTO;

public interface RecordTransferDAO {
	//전환 기록 남기기
	public void insertRecordTransfer(List list);
	//목표에 전환기록 남기기
	public void insertRecordGoals(RecordGoalsDTO dto);
	//예산에 전환하기, 카테고리 현재값 반환
	public int updateRecordTBD(TotalBudgetDetailDTO target);
	//회원의 남은돈 합산
	public int selectLeftMoneySum(String id);
}
