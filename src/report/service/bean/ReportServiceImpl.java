package report.service.bean;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import budget.model.dao.RecordGoalsDAO;
import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.RecordGoalsDTO;
import budget.model.dto.TotalBudgetDTO;
import goals.model.dao.GoalsDAO;
import goals.model.dto.GoalsDTO;
import report.model.dao.LinearRegression;
import report.model.dao.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDAO reportDAO = null;
	@Autowired
	private TotalBudgetDAO totalBudgetDAO = null;
	@Autowired
	private RecordGoalsDAO recordGoalsDAO = null;
	@Autowired
	private GoalsDAO goalsDAO = null;
	
	@Override
	public List selectAllOrderByReg(String id) {
		return reportDAO.selectAllOrderByReg(id);
	}
	
	@Override
	public int selectOutcomeSumByReg(int budget_no, String reg) {
		HashMap map = new HashMap();
		map.put("budget_no", budget_no);
		map.put("reg", reg);
		return reportDAO.selectOutcomeSumByReg(map);
	}
	
	@Override
	public int selectOutcomeSumByRegAndId(String id, String reg) {
		HashMap map = new HashMap();
		map.put("id", id);
		map.put("reg", reg);
		return reportDAO.selectOutcomeSumByRegAndId(map);
	}
	
	@Override
	public HashMap selectLabelDataList(TotalBudgetDTO TBdto) {
		HashMap map = new HashMap();

		//예산 시작일~종료일 배열로
		Date day = new Date(TBdto.getStart_day().getTime());
		Date endDay = new Date(TBdto.getEnd_day().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long lt = endDay.getTime()-day.getTime();
		int period = Math.round((lt)/(1000*60*60*24)) + 1;
		double daily = Math.round(TBdto.getBudget()/period);
		double Tsum = 0;
		double max = 0;
		
		String labelList = "[";
		String dataList = "[";
		
		while(day.before(endDay)) {
			String tmp = "\"" + (day.getMonth()+1) + "/" + day.getDate() + "\", ";
			double sum = selectOutcomeSumByReg(TBdto.getBudget_no(), sdf.format(day));
			Tsum += sum;
					
			labelList += tmp;
//			int amount = (sum/daily)*100;
			if(sum > max) {
				max = sum;
			}
			dataList += sum + ", ";
			
			day.setDate(day.getDate()+1);
		}
		
		labelList = labelList.substring(0, labelList.length()-2);
		labelList += "]";
		
		dataList = dataList.substring(0, dataList.length()-2);
		dataList += "]";
		
		map.put("labelList", labelList);
		map.put("dataList", dataList);
		map.put("daily", (int)daily);
		map.put("dailyAvg", Tsum/period);
		map.put("Tsum", Tsum);
		map.put("max", ((int)(max/10000)+1)*10000);
		
		
		//top3 가져오기
		HashMap top3Map = reportDAO.selectTop3(TBdto.getBudget_no());
		List countList = (List) top3Map.get("countMap");
		List amountList = (List) top3Map.get("amountMap");
		
		map.put("countList", countList);
		map.put("amountList", amountList);
		
		
		return map;
	}
	
	@Override
	public int checkBeforeExpectation(String id) throws SQLException {
		int result = 0;
		
		String firstStartDay = reportDAO.selectFirstStartDay(id);
		Date firstStartDate = Timestamp.valueOf(firstStartDay);
		firstStartDate.setDate(firstStartDate.getDate()+2);
		Date today = new Date();
		if(firstStartDate.after(today)) {
			result = 1;
		} else {
			result = 0;
		}
		
			
		
		
		return result;
	}
	
	
	@Override
	public HashMap expectOutcome(String id) throws SQLException {
		HashMap returnMap = new HashMap();
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date firstStartDay = Timestamp.valueOf(reportDAO.selectFirstStartDay(id));
		Date today = new Date();
		
		List outcomeDataList = new ArrayList();
		List outcomeDataDateList = new ArrayList();
		String outcomeDataX = "[ ";
		String outcomeDataY = "[ ";
		
		while(firstStartDay.before(today)) {
			String date = sdf.format(firstStartDay);
			int outcome = selectOutcomeSumByRegAndId(id, sdf.format(firstStartDay));
			
			outcomeDataX += " \'" + date + "\', ";
			outcomeDataY += " \'" + outcome + "\', ";
			
			
			outcomeDataList.add((float)outcome);
			outcomeDataDateList.add((float)firstStartDay.getTime());
			
			
			firstStartDay.setDate(firstStartDay.getDate()+1);
		}
		
		
		outcomeDataX += " ] ";
		outcomeDataY += " ] ";
		
		returnMap.put("outcomeDataX", outcomeDataX);
		returnMap.put("outcomeDataY", outcomeDataY);
		
		
		//그래프 데이터 설정 끝(지출액)
		
		//현재 예산의 예상지출액 추정
		LinearRegression lr = new LinearRegression(outcomeDataDateList, outcomeDataList);
		
		TotalBudgetDTO TBdto = totalBudgetDAO.selectCurrentOne(id);
		Date startDay = TBdto.getStart_day();
		Date endDay = TBdto.getEnd_day();
		Double predictAmountF = 0.0;
		
		
		while(startDay.before(endDay)) {
			predictAmountF += lr.predictValue((float)startDay.getTime());
			
			
			startDay.setDate(startDay.getDate()+1);
		}
		
		int predictAmount = (int)Math.round(predictAmountF);
		
		returnMap.put("predictAmount",predictAmount);
		
		
		return returnMap;
	}
	
	@Override
	public HashMap expectGoals(String id) throws SQLException {
		HashMap returnMap = new HashMap();
		List goalNumList = selectNumAndSubListById(id);
		List predictedGoalsNo = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(goalNumList.size() == 0) {
			return null;
		} else {
			
			for(Object obj:goalNumList) {
				
				int goal_no = (int)obj;
				
				GoalsDTO goalsDTO = goalsDAO.selectOne(goal_no);
				if(goalsDTO.getTarget_money() == goalsDTO.getSaving()) {
					break;
				}
				if(goalsDTO.getPublic_ch() == 1) {	//그룹 목표일 경우(종료일자 정해져있음)
					if((goalsDTO.getEnd_day().getTime())<(new Date().getTime())) {	//이미 끝난 목표면 종료
						break;
					}
				}
				
				
				
				List goalList = selectAllByIdAndNum(id, goal_no);
				if(goalList.size() < 2) {
					break;
				}
				String goalX = "[";
				String goalY = "[";
				
				List goalXList = new ArrayList();	//선형회귀용 데이터리스트
				List goalYList = new ArrayList();	//선형회귀용 데이터리스트
				
				for(Object obj2 : goalList) {
					RecordGoalsDTO RGdto = (RecordGoalsDTO) obj2;
					goalY += "\'" + RGdto.getAmount() + "\',";
					goalX += "\'" + sdf.format(RGdto.getReg()) + "\',";
					
					goalXList.add((float)(RGdto.getReg().getTime() / (1000*60*60*24)));
					goalYList.add((float)RGdto.getAmount());
				}
				
				LinearRegression lr = new LinearRegression(goalXList, goalYList);
				int restAmount = goalsDTO.getTarget_money() - goalsDTO.getSaving();
				int testAmount = 0;
				
				float todayDate = (float)(new Date().getTime()/(1000*60*60*24));
				
				while(testAmount < restAmount) {
					todayDate += 1;
					testAmount += lr.predictValue(todayDate);
					System.out.println(testAmount);
				}
				
				Date predictedDate = new Date((long)todayDate*(1000*60*60*24));
				
				HashMap map = new HashMap();
				map.put("goal_no", goal_no);
				map.put("goalX", goalX);
				map.put("goalY", goalY);
				map.put("subject", goalsDTO.getSubject());
				map.put("predictedDate", predictedDate);
				predictedGoalsNo.add(goal_no);
				
				returnMap.put(goal_no, map);
			}
			returnMap.put("predictedGoalsNo", predictedGoalsNo);
			
			return returnMap;
		}
	}
	
	@Override
	public List selectAllByIdAndNum(java.lang.String id, int goal_no) {
		HashMap map = new HashMap();
		map.put("id", id);
		map.put("goal_no", goal_no);
		
		List list = recordGoalsDAO.selectAllByIdAndNum(map);
		return list;
	}
	
	@Override
	public List selectNumAndSubListById(String id) throws SQLException {
		List list = new ArrayList();
		List listNum = recordGoalsDAO.selectNumListById(id);
		
		for (int i = 0; i < listNum.size(); i++) {
			GoalsDTO dto = goalsDAO.selectOne((int)listNum.get(i));
			HashMap map = new HashMap();
			map.put(dto.getGoal_no(), dto.getSubject());
			list.add(map);
		}
		return list;
	}
}
