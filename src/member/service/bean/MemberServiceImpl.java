package member.service.bean;


import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import category.model.dao.CategoryDAO;
import member.model.dao.MemberDAOImpl;
import org.springframework.stereotype.Service;
import member.model.dao.MemberDAO;
import member.model.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	

	@Autowired
	private MemberDAO memberDAO = null;
	
	@Autowired
	private CategoryDAO categoryDAO = null;
	
	@Override
	public void insertMember(MemberDTO dto,MultipartHttpServletRequest request) throws SQLException {
		
			
			
				MultipartFile mf = null;
				String newName = null;
				try { 
					
				mf = request.getFile("image");
				if(mf.getSize() >0) {
					String path = request.getRealPath("save");
					String orgName = mf.getOriginalFilename(); 
					String imgName = orgName.substring(0, orgName.lastIndexOf('.'));
					String ext = orgName.substring(orgName.lastIndexOf('.'));
					long date = System.currentTimeMillis();
					 newName = imgName+date+ext; 
				
					String imgPath = path + "\\" + newName;
					File copyFile = new File(imgPath);
					mf.transferTo(copyFile);
				
					dto.setProfile_img(newName);
					dto.setId(dto.getId());
					dto.setPw(dto.getPw());
					dto.setNickname(dto.getNickname());
				//이미지가 안들어 왔으면 
				}else {
					
					dto.setProfile_img("defaultImg.gif");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			
			
			memberDAO.insertMember(dto);
			categoryDAO.outcomeInsertCategory(dto.getId());
			categoryDAO.incomeInsertCategory(dto.getId());
			
			
	}
		
		
	
	@Override
	public void deleteMember(String id) throws SQLException {
		memberDAO.deleteMember(id);	
	}
	@Override
	public int idPwCheck(String id, String pw) throws SQLException {
		int result = memberDAO.idPwCheck(id, pw);
		return result;
	}
	@Override
	public void logout(String sessionName) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modifyMember(MemberDTO dto,MultipartHttpServletRequest request,String eximage) throws SQLException {
		
		
		
		MultipartFile mf = null;
		String newName = null;
		try {
		
			mf = request.getFile("image");
		
			//사진수정
			if(mf.getSize() >0) {
				
				String path = request.getRealPath("save");
				String orgName = mf.getOriginalFilename(); 
				String imgName = orgName.substring(0, orgName.lastIndexOf('.'));
				String ext = orgName.substring(orgName.lastIndexOf('.'));
				long date = System.currentTimeMillis();
				 newName = imgName+date+ext; 
			
				
				String imgPath = path + "\\" + newName;
				File copyFile = new File(imgPath);
				mf.transferTo(copyFile);
				
				dto.setProfile_img(newName);
			
			// 이전 사진 불러오기
			}else {
				dto.setProfile_img(eximage);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		memberDAO.modifyMember(dto);
		
	}
	
	
	@Override
	public MemberDTO selectOne(String id) throws SQLException {

		MemberDTO dto = memberDAO.selectOne(id);
		
	
		
		
		return dto;

	}



	@Override
	public String selectOneByNick(String nickname) throws SQLException {
		return memberDAO.selectOneByNick(nickname);
	}
	
	
	@Override
	public void updateClose(String id) throws SQLException {
		
	}


	//아이디 중복검사 
	@Override
	public int userIdCheck(String user_id) throws SQLException {
		
		
		 int checkId= memberDAO.checkOverId(user_id);
		
		return checkId;
	}


	//닉네임 중복검사
	@Override
	public int nicknameCheck(String nickname) throws SQLException {
			
		int checkNick= memberDAO.checkOverNick(nickname);
		
		return checkNick;
	}


	// 카카오로 로그인했을 때 아이디 유무 체크  비밀번호 반환
	@Override
	public String socialIdCheck(MemberDTO dto) throws SQLException {
		
		String pw ="";
		int checkId = memberDAO.socialIdCheck(dto.getId());
		if(checkId == 0) { // 아이디가 없으면 회원가입
			dto.setProfile_img("defaultImg.gif");
			dto.setPw("0000");
			memberDAO.insertMember(dto);
			categoryDAO.outcomeInsertCategory(dto.getId());
			categoryDAO.incomeInsertCategory(dto.getId());
			pw ="0000";
		}else if(checkId ==1) { // 아이디가 있으면 비밀번호 가져와서 로그인
			pw = memberDAO.getPwById(dto.getId());
			
		}
		return pw;
	}
}
