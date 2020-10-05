package admin.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import admin.model.dao.AdminDAOImpl;

@Controller
@RequestMapping("/admin/") // 클래스 레벨
public class AdminBean {
	
	@Autowired
	private AdminDAOImpl adminDAO = null;
	
	@RequestMapping("memberList.moa")
	public String selectAll() throws SQLException {
		adminDAO.selectAll();
		return "admin/memberList";
	}
}
