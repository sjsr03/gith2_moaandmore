package admin.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import admin.model.dto.MemberListDTO;
import admin.model.dto.ModelDTO;
import admin.service.bean.AdminServiceImpl;
import member.service.bean.MemberServiceImpl;

@Controller
@RequestMapping("/admin/") // 클래스 레벨
public class AdminBean {
	
	@Autowired
	private AdminServiceImpl adminService = null;
	
	@Autowired
	private MemberServiceImpl memberService = null;
	
	
	@RequestMapping("memberList.moa")
	public String selectAll(Model model, String pageNum) throws SQLException {
		MemberListDTO memberList = new MemberListDTO();
		memberList = adminService.selectAll(pageNum);
		model.addAttribute("memberList", memberList);
		return "admin/memberList";
	}
	
	@RequestMapping("deleteMember.moa")
	public String deleteMember(String id, String pageNum, Model model) throws SQLException{
		memberService.deleteMember(id);
		model.addAttribute("pageNum", pageNum);
		return "redirect:memberList.moa";
	}
	
	@RequestMapping("groupWaitAdminList.moa")
	public String groupWaitAdminList(String pageNum, Model model) throws SQLException {
		ModelDTO dto = new ModelDTO();
		dto = adminService.selectAllGroupWaitAdminList(pageNum);
		model.addAttribute("modelDTO",dto);
		
		return "admin/groupWaitAdminList";
	}
}
