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

import budget.model.dao.TotalBudgetDAO;
import budget.model.dto.TotalBudgetDTO;
import report.model.dao.LinearRegression;
import report.model.dao.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDAO reportDAO = null;
	@Autowired
	private TotalBudgetDAO totalBudgetDAO = null;
	
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
	public HashMap expectation(String id) throws SQLException {
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
}
