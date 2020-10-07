package budget.service.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import budget.model.dao.BudgetDetailDAO;
import budget.model.dao.LeftMoneyDAO;
import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.TotalBudgetDTO;
import category.model.dao.CategoryDAO;

@Service
public class BudgetServiceImpl implements BudgetService {

	@Autowired
	private TotalBudgetDAO totalBudgetDAO = null;
	@Autowired
	private CategoryDAO categoryDAO = null;
	@Autowired
	private BudgetDetailDAO budgetDetailDAO = null;
	@Autowired
	private LeftMoneyDAO leftMoneyDAO = null;
	
	//신규 예산 설정
	@Override
	public void setBudget() throws SQLException {
	    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

		String id = (String) request.getSession().getAttribute("memId");
		
		int totalBudget = Integer.parseInt(request.getParameter("totalBudget"));
		int period = Integer.parseInt(request.getParameter("period"));
		Calendar date = Calendar.getInstance();
		
		//시간 0으로 설정
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = sdf.format(date.getTime());
		
		if(period == 30) {	//한달일경우
			int firstOfMonth = Integer.parseInt(request.getParameter("firstOfMonth"));
			if(date.DATE > firstOfMonth) {	//설정한 월 시작일이 이번달 기준 이미 지난 경우 = 다음달 월 시작일 전날까지
				date.add(Calendar.MONTH, 1);
				date.set(Calendar.DATE, firstOfMonth);
			} else { //설정한 월 시작일이 아직 안 지난 경우 = 이번달 월 시작일 전날까지
				date.set(Calendar.DATE, firstOfMonth);
			}
			date.add(Calendar.SECOND, -1);
		} else if (period == 7) {	//일주일일 경우
			date.add(Calendar.DATE, 7);
			date.add(Calendar.SECOND, -1);
		} else if (period == 14) {	//2주일 경우
			date.add(Calendar.DATE, 14);
			date.add(Calendar.SECOND, -1);
		}
		
		String ed = sdf.format(date.getTime());

		Timestamp start_day = Timestamp.valueOf(sd);
		Timestamp end_day = Timestamp.valueOf(ed);
		
		
		TotalBudgetDTO TBdto = new TotalBudgetDTO();
		TBdto.setId(id);
		TBdto.setBudget(totalBudget);
		TBdto.setPeriod(period);
		TBdto.setClose(0);
		TBdto.setStart_day(start_day);
		TBdto.setEnd_day(end_day);
		
		//DB에 총예산설정 넣은 후 해당 총예산의 고유번호 리턴
		int budget_no = totalBudgetDAO.setBudget(TBdto);
		
		
		////////////////여기까지 총예산 설정////////////////////
		
		
		String[] category_name = request.getParameterValues("category_name");
		String[] amount = request.getParameterValues("amount");
		
		List budget_detail = new ArrayList();
		for(int i = 0; i < category_name.length; i++) {
			BudgetDetailDTO BDdto = new BudgetDetailDTO();
			BDdto.setBudget_no(budget_no);
			BDdto.setCategory_budget(Integer.parseInt(amount[i]));
			BDdto.setCategory_no(categoryDAO.selectNumByName(category_name[i], id));
			
			budget_detail.add(BDdto);
		}
		
		budgetDetailDAO.insertBudgetDetail(budget_detail);
		
	}
	
	@Override
	public TotalBudgetDTO selectCurrentOne(String id) throws SQLException {
		
		return totalBudgetDAO.selectCurrentOne(id);
	}
	@Override
	public List selectAllbyBudgetNum(int num) throws SQLException {
		
		return budgetDetailDAO.selectAllbyBudgetNum(num);
	}

	// 날짜랑 아이디로 해당 예산 번호 꺼내오기 
	@Override
	public int selectBudgetNum(String id, Timestamp dateTime) throws SQLException {
		HashMap map = new HashMap();
		map.put("id", id);
		map.put("dateTime", dateTime);
		int budgetNum = totalBudgetDAO.selectBudgetNum(map);
		return budgetNum;
	}
	
	@Override
	public List selectLeftMoneyById(String id) throws SQLException {
		List list = leftMoneyDAO.selectAllById(id);
		return list;
	}
	
	// 예산번호로 해당 예산 카테고리 번호 가져오기 list로 가져오기
	@Override
	public List selectBudgetCategoryNums(int budgetNum) throws SQLException {
		List categoryList = budgetDetailDAO.selectBudgetCategoryNums(budgetNum);
		
		return categoryList;
	}
  

}
