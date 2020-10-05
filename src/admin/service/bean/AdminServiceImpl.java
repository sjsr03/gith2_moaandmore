package admin.service.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import admin.model.dao.AdminDAOImpl;
import admin.model.dto.MemberListDTO;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminDAOImpl adminDAO = null;
	
	// 전체 회원수 범위로 잘라서 list로 가져오고 관련 변수들 memberListDTO에 통으로 담아주기
	@Override
	public MemberListDTO selectAll(String pageNum) throws SQLException {
		MemberListDTO memberList = new MemberListDTO();
		
		if(pageNum == null) {
			pageNum = "1";
		}
		// 페이지 정보 담기
		int pageSize = 10;
		int currPage = Integer.parseInt(pageNum);
		int startRow = (currPage - 1) * pageSize + 1;
		int endRow = currPage * pageSize + 1;
		int count = 0;
		int number = 0;
		
		List members = null;
		// 전체 회원 수 가져오기 
		count = adminDAO.countAll();
		System.out.println("서비스에서 count : " + count);
		if(count > 0) {	// 회원이 한명이라도 있으면 selectAll()호출	
			members = new ArrayList();
			members = adminDAO.selectAll(startRow, endRow);
		}
		number = count - (currPage-1) * pageSize;
		
		// 회원 목록 페이지 관련 변수들 + members DTO에 통으로 담아서 리턴
		memberList.setPageNum(pageNum);
		memberList.setCurrPage(currPage);
		memberList.setEndRow(endRow);
		memberList.setNumber(number);
		memberList.setPageNum(pageNum);
		memberList.setPageSize(pageSize);
		memberList.setStartRow(startRow);
		memberList.setMembers(members);
		memberList.setCount(count);
		return memberList;
	}

	
}
