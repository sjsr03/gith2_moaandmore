package budget.service.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import budget.model.dao.TotalBudgetDetailDAO;
import budget.model.dao.LeftMoneyDAO;
import budget.model.dao.RecordBudgetDAO;
import budget.model.dao.RecordTransferDAO;
import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.TotalBudgetDetailDTO;
import budget.model.dto.RecordGoalsDTO;
import budget.model.dto.RecordTransferDTO;
import budget.model.dto.TotalBudgetDTO;
import category.model.dao.CategoryDAO;
import goals.model.dao.GoalsDAOImpl;
import member.model.dao.MemberDAO;
import report.model.dao.ReportDAO;

@Service
public class BudgetServiceImpl implements BudgetService {

	private static final String String = null;
	@Autowired
	private TotalBudgetDAO totalBudgetDAO = null;
	@Autowired
	private MemberDAO memberDAO = null;
	@Autowired
	private CategoryDAO categoryDAO = null;
	@Autowired
	private TotalBudgetDetailDAO totalBudgetDetailDAO = null;
	@Autowired
	private LeftMoneyDAO leftMoneyDAO = null;
	@Autowired
	private RecordTransferDAO recordTransferDAO = null;
	@Autowired
	private RecordBudgetDAO recordBudgetDAO = null;
	@Autowired
	private ReportDAO reportDAO = null;
	@Autowired
	private GoalsDAOImpl goalsDAO = null;
	
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
			if(date.get(date.DATE) >= firstOfMonth) {	//설정한 월 시작일이 이번달 기준 이미 지난 경우 = 다음달 월 시작일 전날까지
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
		int actualPeriod = (int)Math.ceil((end_day.getTime()-start_day.getTime())/(24*60*60*1000));
		TBdto.setTotal_budget_current(totalBudget*(actualPeriod-1)/actualPeriod);
		
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
			BDdto.setCategory_current(BDdto.getCategory_budget()*(actualPeriod-1)/actualPeriod);
			
			total_budget_detail.add(BDdto);
			leftMoneyDAO.insertZero(budget_no, BDdto.getCategory_no(), id);
		}
		
		totalBudgetDetailDAO.insertTotalBudgetDetail(total_budget_detail);
		
	}
	
	@Override
	public void updateNewTB(String id) throws SQLException {
		while(selectOutClose(id)!=null) {
		
			TotalBudgetDTO outDate = selectOutClose(id);
			
			if(outDate != null) {	//기존 예산이 종료되어야한다면
				TotalBudgetDTO newTB = new TotalBudgetDTO();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int period = outDate.getPeriod();
				
				newTB.setBudget(outDate.getBudget());
				Calendar startday = Calendar.getInstance();
				startday.setTimeInMillis(outDate.getEnd_day().getTime()+1000);
				
				newTB.setStart_day(Timestamp.valueOf(sdf.format(startday.getTime())));
				
				if(period == 30) {	//한달일경우
					startday.add(Calendar.MONTH, 1);
					startday.add(Calendar.SECOND, -1);
				} else if (period == 7) {	//일주일일 경우
					startday.add(Calendar.DATE, 7);
					startday.add(Calendar.SECOND, -1);
				} else if (period == 14) {	//2주일 경우
					startday.add(Calendar.DATE, 14);
					startday.add(Calendar.SECOND, -1);
				}
				newTB.setEnd_day(Timestamp.valueOf(sdf.format(startday.getTime())));
				
				int actualPeriod = (int)Math.ceil((newTB.getEnd_day().getTime()-newTB.getStart_day().getTime())/(24*60*60*1000));
				newTB.setTotal_budget_current(newTB.getBudget()*(actualPeriod-1)/actualPeriod);
				newTB.setId(id);
				newTB.setPeriod(period);
				
				//DB에 총예산설정 넣은 후 해당 총예산의 고유번호 리턴
				int budget_no = totalBudgetDAO.setBudget(newTB);
				
				/////총예산설정 갱신/////
				
				
				List TBDList = totalBudgetDetailDAO.selectAllbyBudgetNum(outDate.getBudget_no());
				
				for(Object obj : TBDList) {
					TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO) obj;
					dto.setBudget_no(budget_no);
					dto.setCategory_current(dto.getCategory_budget()*(actualPeriod-1)/actualPeriod);
				}
				
				totalBudgetDetailDAO.insertTotalBudgetDetail(TBDList);
			}
		
		}
		
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
			
			//분배대상의 예산값 늘리기
			TotalBudgetDetailDTO target = new TotalBudgetDetailDTO();
			target.setBudget_no(totalBudgetDAO.selectCurrentOne(id).getBudget_no());
			target.setCategory_budget(sum);
			target.setCategory_no(Integer.parseInt(subSel));
			
			//분배출처의 예산값 줄이기
			List fromList = new ArrayList();
			for (int i = 0; i < categories.length; i++) {
				TotalBudgetDetailDTO from = new TotalBudgetDetailDTO();
				from.setBudget_no(totalBudgetDAO.selectCurrentOne(id).getBudget_no());
				from.setCategory_budget(Integer.parseInt(inputAmount[i]));
				from.setCategory_no(Integer.parseInt(categories[i]));
				
				fromList.add(from);
			}
					
			recordTransferDAO.updateRecordTBD(target, fromList);

		} else {
			RecordGoalsDTO dto = new RecordGoalsDTO();
			dto.setAmount(sum);
			dto.setGoal_no(Integer.parseInt(subSel));
			dto.setId(id);
			
			recordTransferDAO.insertRecordGoals(dto);
			
			//////sojin. goals테이블의 saving에 합산값 더하기
			goalsDAO.updateSaving(Integer.parseInt(subSel), sum);
		}
		
		
		
	}
	

	@Override
	public List selectBudgetDate(String id) throws SQLException {
		List budgetDateTime = new ArrayList();
		budgetDateTime = totalBudgetDAO.selectBudgetDate(id);
		String start = (String)budgetDateTime.get(0);
		String end = (String)budgetDateTime.get(1);
		
		// budgetDate의 값들에서 시간을 뺴서 날짜만 보내주기
		List budgetDate = new ArrayList();
		budgetDate.add(end.substring(0, 10));
		budgetDate.add(start.substring(0, 10));		
		return budgetDate;
	}


	@Override
	public TotalBudgetDTO selectLastTB(String id) throws SQLException {
		return totalBudgetDAO.selectLastTB(id);
	}
	
	@Override
	public TotalBudgetDTO selectOneByNum(int budget_no) throws SQLException {
		return totalBudgetDAO.selectOneByNum(budget_no);
	}
	
	// 예산번호로 해당 예산 기록 목록 가져오기
	@Override
	public List selectAllBudgetByNum(int budgetNum, String pageNum) throws SQLException {
		int startRow = 0;
		int endRow = 0;
		
		List budgetRecordList =  recordBudgetDAO.selectAllBudgetByNum(budgetNum, startRow, endRow);
		return budgetRecordList;
	}

	@Override
	public TotalBudgetDTO selectOutClose(String id) throws SQLException {
		return memberDAO.selectOutClose(id);
	}

	@Override
	public void calLeftMoney(String id) throws SQLException {
		//마지막 로그인날짜(남은돈 계산날짜 가져오기)
		String lastD = leftMoneyDAO.selectLastLoginReg(id);
		if(lastD == null) {
			return;
		}
		Date lastDate = new Date(Timestamp.valueOf(lastD).getTime());
		lastDate.setHours(0);
		lastDate.setMinutes(0);
		lastDate.setSeconds(0);
		
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(sdf.format(lastDate).equals(sdf.format(today))) {
			return;
		}

		
		
		
		while(lastDate.before(today)) {
			HashMap data = new HashMap();
			data.put("id", id);
			data.put("dateTime", lastDate);
			
			int budget_no = totalBudgetDAO.selectBudgetNum(data);
			
			TotalBudgetDTO TBdto = selectOneByNum(budget_no);
			List TBDList = selectAllbyBudgetNum(budget_no);
			int period = Math.round((TBdto.getEnd_day().getTime()-TBdto.getStart_day().getTime())/(1000*60*60*24)) + 1;
			
			
			for(int i = 0; i < TBDList.size(); i++) {
				//카테고리 번호와 날짜를 주고, 실제 지출액 가져오기
				TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO) TBDList.get(i);
				
				int dailyBudget = dto.getCategory_budget()/period;  //하루권장예산
				HashMap map = new HashMap();
				map.put("budget_no", dto.getBudget_no());
				map.put("category_no", dto.getCategory_no());
				map.put("reg", sdf.format(lastDate));
				
				int actualOutcomeSum = reportDAO.selectOutcomeSumByCatAndReg(map);
				
				int thisLeftmoney = dailyBudget-actualOutcomeSum;
				
				//해당 카테고리에 남은돈 추가
				leftMoneyDAO.updateLeftMoney(thisLeftmoney, dto.getCategory_no(),id);
				
			}
			
			lastDate.setDate(lastDate.getDate()+1);
		}
		
	}
	@Override
	public List selectTodayBudget(String id) throws SQLException {
		TotalBudgetDTO TBdto = totalBudgetDAO.selectCurrentOne(id);
		long lt = TBdto.getEnd_day().getTime()-TBdto.getStart_day().getTime();
		int period = Math.round((lt)/(1000*60*60*24)) + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List TBDList = totalBudgetDetailDAO.selectAllbyBudgetNum(TBdto.getBudget_no());
		//현재 예산의 카테고리 리스트 불러오기
		
		List returnList = new ArrayList();
		int TRsum = 0;
		int TAsum = 0;
		
		for(Object obj:TBDList) {
			TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO) obj;
			
			int category_no = dto.getCategory_no();
			
			//카테고리번호의 오늘하루 권장 예산
			double recommend = dto.getCategory_current() / period;
			
			HashMap map = new HashMap();
			map.put("budget_no", TBdto.getBudget_no());
			map.put("category_no", category_no);
			map.put("reg", sdf.format(new Date()));
			
			int actual = reportDAO.selectOutcomeSumByCatAndReg(map);
			
			HashMap returnMap = new HashMap();
			returnMap.put("category_no", category_no);
			returnMap.put("recommend", recommend);
			returnMap.put("actual", actual);
			
			returnList.add(returnMap);
			
			TRsum += recommend;
			TAsum += actual;
			
		}
		
		//총예산값 넣기
		HashMap total = new HashMap();
		total.put("category_no", 0);
		total.put("recommend", TRsum);
		total.put("actual", TAsum);
		
		returnList.add(0, total);
		
		return returnList;
	}

	
	@Override
	public int selectLeftMoneySum(java.lang.String id) throws SQLException {
		int LMsum = leftMoneyDAO.selectCurrentLeftMoneySum(id) + recordTransferDAO.selectLeftMoneySum(id);
		return LMsum;
	}
}
	

