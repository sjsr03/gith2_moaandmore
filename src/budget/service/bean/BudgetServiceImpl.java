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

import budget.model.dao.TotalBudgetDetailDAO;
import budget.model.dao.LeftMoneyDAO;
import budget.model.dao.RecordTransferDAO;
import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.TotalBudgetDetailDTO;
import budget.model.dto.RecordGoalsDTO;
import budget.model.dto.RecordTransferDTO;
import budget.model.dto.TotalBudgetDTO;
import category.model.dao.CategoryDAO;

@Service
public class BudgetServiceImpl implements BudgetService {

	@Autowired
	private TotalBudgetDAO totalBudgetDAO = null;
	@Autowired
	private CategoryDAO categoryDAO = null;
	@Autowired
	private TotalBudgetDetailDAO totalBudgetDetailDAO = null;
	@Autowired
	private LeftMoneyDAO leftMoneyDAO = null;
	@Autowired
	private RecordTransferDAO recordTransferDAO = null;
	
	
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
		
		List total_budget_detail = new ArrayList();
		for(int i = 0; i < category_name.length; i++) {
			TotalBudgetDetailDTO BDdto = new TotalBudgetDetailDTO();
			BDdto.setBudget_no(budget_no);
			BDdto.setCategory_budget(Integer.parseInt(amount[i]));
			BDdto.setCategory_no(categoryDAO.selectNumByName(category_name[i], id));
			
			total_budget_detail.add(BDdto);
		}
		
		totalBudgetDetailDAO.insertTotalBudgetDetail(total_budget_detail);
		
	}
	
	
	@Override
	public void updateBudget() throws SQLException {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		int budget_no = Integer.parseInt(request.getParameter("budget_no"));
		int budget = Integer.parseInt(request.getParameter("totalBudget"));
		String id = (String) request.getSession().getAttribute("memId");
		
		
		
		TotalBudgetDTO TBdto = new TotalBudgetDTO();
		TBdto.setBudget_no(budget_no);
		TBdto.setBudget(budget);
		
		totalBudgetDAO.updateTotalBudget(TBdto);
		
		///////////////// 세부 예산 ////////////////////
		
		String[] category_name = request.getParameterValues("category_name");
		String[] amount = request.getParameterValues("amount");
		
		List total_budget_detail = new ArrayList();
		for(int i = 0; i < category_name.length; i++) {
			TotalBudgetDetailDTO BDdto = new TotalBudgetDetailDTO();
			BDdto.setBudget_no(budget_no);
			BDdto.setCategory_budget(Integer.parseInt(amount[i]));
			BDdto.setCategory_no(categoryDAO.selectNumByName(category_name[i], id));
			
			total_budget_detail.add(BDdto);
		}
		
		totalBudgetDetailDAO.updateTotalBudgetDetail(total_budget_detail);
		
	}
	
	@Override
	public TotalBudgetDTO selectCurrentOne(String id) throws SQLException {
		
		return totalBudgetDAO.selectCurrentOne(id);
	}
	@Override
	public List selectAllbyBudgetNum(int num) throws SQLException {
		
		return totalBudgetDetailDAO.selectAllbyBudgetNum(num);
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
		List categoryList = totalBudgetDetailDAO.selectBudgetCategoryNums(budgetNum);
		
		return categoryList;
	}
	
	
	@Override
	public void LeftMoneyTransfer() throws SQLException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String id = (String) request.getSession().getAttribute("memId");
		
		String[] categories = request.getParameterValues("category");
		String[] inputAmount = request.getParameterValues("inputAmount");
		
		String target_table = request.getParameter("target_table");
		String subSel = null;
		String[] subSels = request.getParameterValues("subSel");
		if (target_table.equals("budget")) {
			subSel = subSels[0];
		} else {
			subSel = subSels[1];
		}
		
		int sum = 0;
		
		
		List recordList = new ArrayList();
		for(int i = 0; i < categories.length; i++) {
			RecordTransferDTO RTdto = new RecordTransferDTO();
			RTdto.setAmount(Integer.parseInt(inputAmount[i]));
			RTdto.setCategory_no(Integer.parseInt(categories[i]));
			RTdto.setId(id);
			RTdto.setTarget_table(target_table);
			RTdto.setTarget_no(Integer.parseInt(subSel));
			
			recordList.add(RTdto);
			
			sum += RTdto.getAmount();
		}
		
		recordTransferDAO.insertRecordTransfer(recordList);
		
		
		/////////////////// 기록 삽입 끝///////////////////
		
		if(target_table.equals("budget")) {
			TotalBudgetDetailDTO dto = new TotalBudgetDetailDTO();
			dto.setBudget_no(totalBudgetDAO.selectCurrentOne(id).getBudget_no());
			dto.setCategory_budget(sum);
			dto.setCategory_no(Integer.parseInt(subSel));
			
			recordTransferDAO.updateRecordTBD(dto);
		} else {
			RecordGoalsDTO dto = new RecordGoalsDTO();
			dto.setAmount(sum);
			dto.setGoal_no(Integer.parseInt(subSel));
			dto.setId(id);
			
			recordTransferDAO.insertRecordGoals(dto);
		}
		
		
		
	}
	
  

}
