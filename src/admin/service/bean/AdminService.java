package admin.service.bean;

import java.sql.SQLException;
import java.util.List;

import admin.model.dto.MemberListDTO;

public interface AdminService {
	
	// 전체 회원 정보 가져오기
	public MemberListDTO selectAll(String pageNum) throws SQLException;
}
