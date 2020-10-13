package member.service.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.rmi.CORBA.Tie;

import org.springframework.beans.factory.annotation.Autowired;

import budget.model.dto.BudgetDTO;
import member.model.dao.CalendarDAO;

public class CalendarServiceImpl implements CalendarService{

	
	@Autowired
	private CalendarDAO calendarDAO = null;

	
	
	@Override
	public List selectBudgetDatebyId(String id) throws SQLException {
		
		SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		
		List budget = calendarDAO.selectBudgetDatebyId(id);
		
		//return 해줄 중복값 제거된 budget 테이블 총 날짜
		List budgetalldate = new ArrayList();
		
		
		for(int i=0; i<budget.size(); i++) {
			BudgetDTO budgetList = (BudgetDTO)budget.get(i);
			
			Date indata =budgetList.getReg();
			String budgetDate = yyyymmdd.format(indata);
			
		
			if(!budgetalldate.contains(budgetDate)){
		       budgetalldate.add(budgetDate);
			}
			
		}
	
		//System.out.println(budgetalldate);
		return budgetalldate;
	}



	@Override
	public List selectBudgetAmount(String id, List budgetAlldate) throws SQLException {
		
		//budget 테이블 지출 총금액
		List AllBudgetamount = calendarDAO.selectBudgetAmount(id,budgetAlldate);
		
		
		return AllBudgetamount;
		
		
	}
	
	


	
	
	
}
