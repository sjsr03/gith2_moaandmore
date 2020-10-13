package report.service.bean;

import java.util.HashMap;
import java.util.List;

import budget.model.dto.TotalBudgetDTO;

public interface ReportService {

	//회원의 모든 예산정보를 날짜 내림차순으로 정렬해서 가져오기
	public List selectAllOrderByReg(String id);
	
	//날짜와 예산번호로 지출액 합산
	public int selectOutcomeSumByReg(int budget_no, String reg);
	
	//labelList, dataList 반환
	public HashMap selectLabelDataList(TotalBudgetDTO dto);
}
