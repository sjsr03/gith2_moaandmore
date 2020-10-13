package report.service.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import report.model.dao.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDAO reportDAO = null;
	
	@Override
	public List selectAllOrderByReg(String id) {
		return reportDAO.selectAllOrderByReg(id);
	}
}
