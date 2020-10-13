package report.service.bean;

import java.util.List;

public interface ReportService {

	//회원의 모든 예산정보를 날짜 내림차순으로 정렬해서 가져오기
	public List selectAllOrderByReg(String id);
}
