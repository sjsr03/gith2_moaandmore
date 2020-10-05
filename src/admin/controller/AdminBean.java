package admin.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import admin.model.dto.MemberListDTO;
import admin.service.bean.AdminServiceImpl;

@Controller
@RequestMapping("/admin/") // 클래스 레벨
public class AdminBean {
	
	@Autowired
	private AdminServiceImpl adminService = null;
	
	@RequestMapping("memberList.moa")
	public String selectAll(Model model, String pageNum) throws SQLException {
		MemberListDTO memberList = new MemberListDTO();
		memberList = adminService.selectAll(pageNum);
		System.out.println("컨트롤러에서 count : " + memberList.getCount());
		model.addAttribute("memberList", memberList);
		return "admin/memberList";
	}
}
