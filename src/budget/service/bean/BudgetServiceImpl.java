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
import budget.model.dao.TodayBudgetDAO;
import budget.model.dao.TodayBudgetDAOImpl;
import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.TotalBudgetDetailDTO;
import budget.model.dto.RecordGoalsDTO;
import budget.model.dto.RecordTransferDTO;
import budget.model.dto.TodayBudgetDTO;
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
	@Autowired
	private TodayBudgetDAO todayBudgetDAO = null;
	
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
		int actualPeriod = (int)Math.round((end_day.getTime()-start_day.getTime())/(24*60*60*1000));
		TBdto.setTotal_budget_current(totalBudget*(actualPeriod-1)/actualPeriod);
		
		//DB에 총예산설정 넣은 후 해당 총예산의 고유번호 리턴
		int budget_no = totalBudgetDAO.setBudget(TBdto);
		
		////////////////여기까지 총예산 설정////////////////////
		
		
		String[] category_name = request.getParameterValues("category_name");
		String[] amount = request.getParameterValues("amount");
		
		List total_budget_detail = new ArrayList();
		List today_zero = new ArrayList();
		
		for(int i = 0; i < category_name.length; i++) {
			TotalBudgetDetailDTO BDdto = new TotalBudgetDetailDTO();
			BDdto.setBudget_no(budget_no);
			BDdto.setCategory_budget(Integer.parseInt(amount[i]));
			BDdto.setCategory_no(categoryDAO.selectNumByName(category_name[i], id));
			
			int today = (int)(Math.round((double)BDdto.getCategory_budget()/actualPeriod));
			BDdto.setCategory_current(BDdto.getCategory_budget()-today);
			
			total_budget_detail.add(BDdto);
			
			//남은돈 테이블에 0으로 삽입
			leftMoneyDAO.insertZero(budget_no, BDdto.getCategory_no(), id);		//0으로 insert 해주기
			
			
			//하루예산 테이블에 삽입
			TodayBudgetDTO todayDTO = new TodayBudgetDTO();
			todayDTO.setBudget_no(budget_no);
			todayDTO.setCategory_no(BDdto.getCategory_no());
			todayDTO.setCategory_today(today);
			todayDTO.setId(id);
			today_zero.add(todayDTO);
		}
		
		totalBudgetDetailDAO.insertTotalBudgetDetail(total_budget_detail);
		todayBudgetDAO.insertTodayBudget(today_zero);
		
		//////////////여기까지 총예산 세부내용 설정//////////////////
		
	}
	
	@Override
	public void updateNewTB(String id) throws SQLException {
		if(selectOutClose(id)==null) {
			return;
		} else {
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
				
				int actualPeriod = (int)Math.round((newTB.getEnd_day().getTime()-newTB.getStart_day().getTime())/(24*60*60*1000));
				newTB.setTotal_budget_current(newTB.getBudget()*(actualPeriod-1)/actualPeriod);
				newTB.setId(id);
				newTB.setPeriod(period);
				
				
				//기존 총예산 종료처리
//				memberDAO.updateClose(id);
				//DB에 총예산설정 넣은 후 해당 총예산의 고유번호 리턴
				int budget_no = totalBudgetDAO.setBudget(newTB);
				
				/////총예산설정 갱신/////
				
				
				List TBDList = totalBudgetDetailDAO.selectAllbyBudgetNum(outDate.getBudget_no());
				List today_zero = new ArrayList();
				
				for(Object obj : TBDList) {
					TotalBudgetDetailDTO dto = (TotalBudgetDetailDTO) obj;
					dto.setBudget_no(budget_no);
					dto.setCategory_current(dto.getCategory_budget()*(actualPeriod-1)/actualPeriod);
					
					leftMoneyDAO.insertZero(budget_no, dto.getCategory_no(), id);	
					
					int today = (int)(Math.round((double)dto.getCategory_budget()/actualPeriod));
					
					//하루예산 테이블에 삽입
					TodayBudgetDTO todayDTO = new TodayBudgetDTO();
					todayDTO.setBudget_no(budget_no);
					todayDTO.setCategory_no(dto.getCategory_no());
					todayDTO.setCategory_today(today);
					todayDTO.setId(id);
					today_zero.add(todayDTO);
					
				}
				
				totalBudgetDetailDAO.insertTotalBudgetDetail(TBDList);
				todayBudgetDAO.insertTodayBudget(today_zero);
			}
		
		}
		
		//총예산정보가 현재값으로 적절하게 들어갔으면
		//LeftMoney의 reg를 (총예산정보의 시작일로)
		leftMoneyDAO.updateRegToStartDay(id);
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
		
		if(target_table.equals("budget")) {	//예산에 분배하는 거라면
			
			//분배대상의 예산값 늘리기
			TotalBudgetDetailDTO target = new TotalBudgetDetailDTO();
			target.setBudget_no(totalBudgetDAO.selectCurrentOne(id).getBudget_no());
			target.setCategory_current(sum);
			target.setCategory_no(Integer.parseInt(subSel));
			recordTransferDAO.updateRecordTBD(target);

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//마지막 로그인날짜(남은돈 계산날짜 가져오기)
		String lastD = leftMoneyDAO.selectLastLoginReg(id);
		if(lastD == null) {
			return;
		}
		
		long lt = Timestamp.valueOf(lastD).getTime();
		Date lastDate = new Date(lt);
		lastDate.setHours(0);
		lastDate.setMinutes(0);
		lastDate.setSeconds(0);
		
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		
		
		
		if(sdf.format(lastDate).equals(sdf.format(today))) {	//마지막 로그인날짜가 오늘이면 메서드 종료
			return;
		}

		
		
		//현재 총예산 정보 가져오기
		TotalBudgetDTO TBdto = selectCurrentOne(id);
		List TBDList = selectAllbyBudgetNum(TBdto.getBudget_no());
		
		Date startDay = new Date(TBdto.getStart_day().getTime());
		if(startDay.after(lastDate)) {	//마지막 로그인이 현재총예산 시작일보다 전이면
			lastDate = startDay;	//계산은 현재총예산 시작일부터 시작
		}
		
		//계산해야할 일수가 얼마나 되는지?
		int period = (int)(today.getTime()-lastDate.getTime()) / (1000*60*60*24);
		System.out.println("마지막 로그인으로부터 " + period + "일째 지났다");
		//lastDate는 종료까지 며칠 남은 상태였을까
		int lastPeriod = (int)(Math.round((TBdto.getEnd_day().getTime()-lastDate.getTime()) / (1000*60*60*24)));
		System.out.println("endDay : " + TBdto.getEnd_day() + " / long : " + TBdto.getEnd_day().getTime());
		System.out.println("lastDate : " + lastDate + " / long : " + lastDate.getTime());
		System.out.println("마지막 로그인은 종료일까지" + lastPeriod + "일 남은 시점이었다");
		
		for(int i = 0; i < TBDList.size(); i++) {
			TotalBudgetDetailDTO TBDdto = (TotalBudgetDetailDTO) TBDList.get(i);
			//기록된 현재 예산값은
			int current = TBDdto.getCategory_current();
			int dailyBudget = (int)Math.round(current / (lastPeriod-1));
			
			//소비했다고 가정하는 액수
			int assumed = dailyBudget*period;
			System.out.println(TBDdto.getCategory_no() + "번 카테고리에서 " + assumed + "원만큼 사용했다고 추정");
			
			TBDdto.setCategory_current(assumed);
			//현재값에서 추정치만큼 빼기
			totalBudgetDetailDAO.updateMinusCurrent(TBDdto);
			
			//사용한 기록이 있는지 확인(미래 지출기록이 가능하므로 염두)
			//lastDate부터 today까지
			HashMap map = new HashMap();
			map.put("category_no", TBDdto.getCategory_no());
			map.put("startDay", sdf.format(lastDate));
			
			int outcomeSum = recordBudgetDAO.selectSumFromDateAndCatNo(map);
			System.out.println("실제 사용액 : " + outcomeSum + "원");
			
			//남은돈 추가
			int leftMoney = (assumed-outcomeSum);
			System.out.println("남은 돈은 " + leftMoney + "원");
			
			leftMoneyDAO.updateLeftMoney(leftMoney, TBDdto.getCategory_no(), id);
			
		}
		
		//총예산 현재값 계산
		totalBudgetDAO.updateCurrentBudget(TBdto.getBudget_no());
		
		
	}
	@Override
	public List selectTodayBudget(String id) throws SQLException {
		TotalBudgetDTO TBdto = totalBudgetDAO.selectCurrentOne(id);
		long lt = TBdto.getEnd_day().getTime()-TBdto.getStart_day().getTime();
		int period = Math.round((lt)/(1000*60*60*24)) + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//오늘의 예산정보 불러오기
		List todayList = todayBudgetDAO.selectTodayBudgetList(id);
		
		List returnList = new ArrayList();
		int TRsum = 0;
		int TAsum = 0;
		
		for(Object obj:todayList) {
			TodayBudgetDTO dto = (TodayBudgetDTO) obj;
			
			int category_no = dto.getCategory_no();
			
			//카테고리번호의 오늘하루 권장 예산
			double recommend = dto.getCategory_today();
			
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
	
	@Override
	public void calTodayBudget(java.lang.String id) throws SQLException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//마지막 로그인날짜(남은돈 계산날짜 가져오기)
		String lastD = todayBudgetDAO.selectLastLoginReg(id);
		if(lastD == null) {
			return;
		}
		
		long lt = Timestamp.valueOf(lastD).getTime();
		Date lastDate = new Date(lt);
		lastDate.setHours(0);
		lastDate.setMinutes(0);
		lastDate.setSeconds(0);
		
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		
		
		
		if(sdf.format(lastDate).equals(sdf.format(today))) {	//마지막 로그인날짜가 오늘이면 메서드 종료
			return;
		}
		
		
		//현재 진행중인 총예산정보 가져오기
		TotalBudgetDTO TBdto = selectCurrentOne(id);
		List TBDList = totalBudgetDetailDAO.selectAllbyBudgetNum(TBdto.getBudget_no());
		
		//오늘 기준 남은 일수는?
		int period = totalBudgetDAO.calLeftDaysCurrentTB(id);
		System.out.println("현재 남은 일수는 " + period + "일");
		
		for(int i = 0; i<TBDList.size(); i++) {
			TotalBudgetDetailDTO TBDdto = (TotalBudgetDetailDTO)TBDList.get(i);
			
			//카테고리의 현재값
			double catCurrent = TBDdto.getCategory_current();
			//남은 일수로 나눈 하루치 값
			int daily = (int)(Math.round(catCurrent / period));
			
			TodayBudgetDTO todayDTO = new TodayBudgetDTO();
			todayDTO.setBudget_no(TBDdto.getBudget_no());
			todayDTO.setCategory_no(TBDdto.getCategory_no());
			todayDTO.setCategory_today(daily);
			
			//오늘의 예산 업데이트
			todayBudgetDAO.updateTodayBudget(todayDTO);
			
			//카테고리별 예산 현재값에서 차감
			TBDdto.setCategory_current(daily);
			totalBudgetDetailDAO.updateMinusCurrent(TBDdto);
			
			
		}
		//총예산 현재값 계산
		totalBudgetDAO.updateCurrentBudget(TBdto.getBudget_no());
		
	}
	
	@Override
	public int selectSumTodayBudget(java.lang.String id) throws SQLException {
		return todayBudgetDAO.selectSumTodayBudget(id);
	}
}
	

