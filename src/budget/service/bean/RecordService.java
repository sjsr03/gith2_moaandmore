package budget.service.bean;

import java.sql.SQLException;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface RecordService {

	// 수입지출내역 추가메서드
	public void insertRecord(MultipartHttpServletRequest request) throws SQLException;
}
