package main.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import budget.model.dao.ReportDAO;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	private ReportDAO reportDAO = null;
	
	
	@Override
	//현재 예산에서 총 사용액
	public int selectOutcomeSumByBudgetId(int num) {
		int sum = reportDAO.selectOutcomeSumByBudgetId(num);
		return sum;
	}
	

}
