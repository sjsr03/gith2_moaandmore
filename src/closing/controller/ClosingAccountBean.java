package closing.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import budget.model.dto.TotalBudgetDTO;
import budget.service.bean.BudgetServiceImpl;
import closing.model.dto.ClosingAccountCommentDTO;
import closing.model.dto.ClosingAccountDTO;
import closing.service.bean.ClosingAccountServiceImpl;

@Controller
@RequestMapping("/closing/")
public class ClosingAccountBean {
	
	@Autowired
	private ClosingAccountServiceImpl closingService = null;
	
	@Autowired
	private BudgetServiceImpl budgetService = null;
	
	@RequestMapping("closingAccountList.moa")
	public String viewList(String pageNum, Model model) throws SQLException {
		
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int pageSize = 10;
		int currPage = Integer.parseInt(pageNum);	//페이지 계산을 위해 숫자로 형변환
		int startRow = (currPage-1)*pageSize+1;
		int endRow = currPage*pageSize;
		int number = 0;	//게시판 상의 글번호 뿌려줄 변수 미리 선언
		
		List articleList = null;
		int count = closingService.getClosingArticleCount();
		
		if(count>0) {
			articleList = closingService.getClosingArticles(startRow, endRow);
		}
		
		number = count-(currPage-1)*pageSize;
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", new Integer(pageSize));
		model.addAttribute("currPage", new Integer(currPage));
		model.addAttribute("startRow", new Integer(startRow));
		model.addAttribute("endRow", new Integer(endRow));
		model.addAttribute("number", new Integer(number));
		model.addAttribute("articleList", articleList);
		model.addAttribute("count", new Integer(count));
		
		return "closingAccount/closingAccountList";
	}
	
	@RequestMapping("closingAccountDetail.moa")
	public String ClosingAccountDetail(int article_no, String pageNum, Model model) throws SQLException {
		
		int count = closingService.getClosingCommentArticleCount(article_no);
		int pageSize = 10;
		
		int currPage;
		
		if(pageNum == null) {
			if(count>0) {
				currPage = (count/pageSize)+1;
			}else {
				currPage = 1;
			}
			pageNum = Integer.toString(currPage);
		} else {
			currPage = Integer.parseInt(pageNum);
		}
		
		int startRow = (currPage-1)*pageSize+1;
		int endRow = currPage*pageSize;
		int number = 0;	//게시판 상의 글번호 뿌려줄 변수 미리 선언

		List articleList = null;
		
		if(count>0) {
			articleList = closingService.getClosingCommentArticles(article_no, startRow, endRow);
		}
		
		number = count-(currPage-1)*pageSize;
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageSize", new Integer(pageSize));
		model.addAttribute("currPage", new Integer(currPage));
		model.addAttribute("startRow", new Integer(startRow));
		model.addAttribute("endRow", new Integer(endRow));
		model.addAttribute("number", new Integer(number));
		model.addAttribute("articleList", articleList);
		model.addAttribute("count", new Integer(count));
		
		
		
		ClosingAccountDTO dto = closingService.getClosingArticleOne(article_no);
		
		model.addAttribute("dto", dto);
		
		return "closingAccount/closingAccountDetail";
	}
	
	@RequestMapping("closingAccountDetailPro.moa")
	public String ClosingAccountDetailPro(HttpServletRequest request, int article_no, String content, Model model) throws SQLException{
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("memId");
		
		ClosingAccountCommentDTO dto = new ClosingAccountCommentDTO();
		
		dto.setArticle_no(article_no);
		dto.setContent(content);
		dto.setId(id);
		
		closingService.insertClosingAccountComment(dto);
		
		model.addAttribute("article_no",article_no);
		
		return "closingAccount/closingAccountDetailPro";
	}
	
	@RequestMapping("closingAccountForm.moa")
	public String ClosingAccountForm(HttpServletRequest request, Model model) throws SQLException{
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("memId");
		
		List<TotalBudgetDTO> list = budgetService.selectBudgetAllByID(id);
		
		model.addAttribute("list",list);
		
		return "closingAccount/closingAccountForm";
	}
	
	@RequestMapping("closingAccountFormPro.moa")
	public String ClosingAccountFormPro(String id, String subject,int budget_no, String content, Model model) throws SQLException{
		
		ClosingAccountDTO dto = new ClosingAccountDTO();
		

		dto.setId(id);
		dto.setSubject(subject);
		dto.setBudget_no(budget_no);
		dto.setContent(content);
		
		closingService.insertClosingAccount(dto);
		
		return "closingAccount/closingAccountFormPro";
	}
}
