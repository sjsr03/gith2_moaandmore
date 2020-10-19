package budget.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import budget.model.dto.RecordGoalsDTO;
import budget.model.dto.RecordTransferDTO;
import budget.model.dto.TotalBudgetDetailDTO;
import goals.model.dto.GoalsDTO;
import team.model.dto.TeamMemberDTO;

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
		GoalsDTO Gdto = sqlSession.selectOne("goals.selectOne", dto.getGoal_no());
		int public_ch = Gdto.getPublic_ch();
		if(public_ch == 1) { //그룹목표인경우
			TeamMemberDTO TMdto = new TeamMemberDTO();
			TMdto.setId(dto.getId());
			TMdto.setSaving(dto.getAmount());
			TMdto.setTeam_no(Gdto.getTeam_no());
			
			sqlSession.update("recordGoals.updateTeamMemberSaving", TMdto);
		}
	};
	
	@Override
	public void updateRecordTBD(TotalBudgetDetailDTO target) {
		sqlSession.update("recordTransfer.updatePlusRecordTBD", target);
	}
	
	@Override
	public int selectLeftMoneySum(String id) {
		if(sqlSession.selectOne("recordTransfer.selectLeftMoneySum", id) == null) {
			return 0;
		} else {
			return sqlSession.selectOne("recordTransfer.selectLeftMoneySum", id);
		}
	}
}
