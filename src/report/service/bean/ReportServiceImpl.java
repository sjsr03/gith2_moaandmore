package report.service.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import budget.model.dto.TotalBudgetDTO;
import report.model.dao.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDAO reportDAO = null;
	
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
		
		String labelList = "[";
		String dataList = "[";
		
		while(day.before(endDay)) {
			String tmp = "\"" + (day.getMonth()+1) + "/" + day.getDate() + "\", ";
			double sum = selectOutcomeSumByReg(TBdto.getBudget_no(), sdf.format(day));
			Tsum += sum;
					
			labelList += tmp;
			dataList += (sum/daily)*100 + ", ";
			
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
		
		
		//top3 가져오기
		reportDAO.selectTop3(TBdto.getBudget_no());
		
		
		
		
		return map;
	}
}
