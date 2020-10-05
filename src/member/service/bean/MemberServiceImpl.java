package member.service.bean;

import java.io.File;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import member.model.dao.MemberDAOImpl;
import member.model.dto.MemberDTO;

public class MemberServiceImpl implements MemberService {
	
	
	@Autowired
	private MemberDAOImpl memberDAO = null;
	
	@Override
	public void insertMember(MemberDTO dto,MultipartHttpServletRequest request) throws SQLException {
		
		
			
			MultipartFile mf = null;
			String newName = null;
			try {
				
			mf = request.getFile("image");
			String path = request.getRealPath("save");
			
			
			String orgName = mf.getOriginalFilename(); 
			
			
			String imgName = orgName.substring(0, orgName.lastIndexOf('.'));
		
			String ext = orgName.substring(orgName.lastIndexOf('.'));
			long date = System.currentTimeMillis();
			 newName = imgName+date+ext; 
		
			
			
			
			String imgPath = path + "\\" + newName;
			File copyFile = new File(imgPath);
			mf.transferTo(copyFile);
			
			
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			dto.setProfile_img(newName);
			dto.setId(dto.getId());
			dto.setPw(dto.getPw());
			dto.setNickname(dto.getNickname());
			
			
			
			memberDAO.insertMember(dto);
			
			
	}
		
		
		
		
		
		
	
	@Override
	public int confirmId(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int deleteMember(MemberDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int idPwCheck(String id, String pw) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void logout(String sessionName) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modifyMember(MemberDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public MemberDTO selectOne(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
