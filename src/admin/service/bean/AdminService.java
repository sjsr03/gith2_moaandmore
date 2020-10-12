package admin.service.bean;

import java.sql.SQLException;
import java.util.List;

import org.springframework.ui.Model;

import admin.model.dto.MemberListDTO;
import admin.model.dto.ModelDTO;

public interface AdminService {
	
	// 전체 회원 정보 가져오기
	public MemberListDTO selectAll(String pageNum) throws SQLException;
	
	//승인 대기 그룹들 범위 지정해서 가져오기
	public ModelDTO selectAllGroupWaitAdminList(String pageNum) throws SQLException;
	
	//승인 대기 그룹들 전체 개수 가져오기
	public int countAllGroupWaitAdminList() throws SQLException;
}
