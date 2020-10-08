package budget.service.bean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import budget.model.dto.BudgetDTO;
import budget.model.dto.BudgetDetailDTO;
import budget.model.dto.NoBudgetDTO;
import budget.model.dto.NoBudgetDetailDTO;

@Service
public class RecordServiceImpl implements RecordService{

	// 수입/지출내역 insert 메서드  
	@Override
	public void insertRecord(MultipartHttpServletRequest request, BudgetDTO budgetDTO, BudgetDetailDTO budgetDetailDTO, NoBudgetDTO noBudgetDTO, NoBudgetDetailDTO noBugetDetailDTO, Timestamp newReg) throws SQLException, IOException {
		
		// 이미지 저장처리 
		MultipartFile mf = request.getFile("image");
		String path = request.getRealPath("save"); // 저장할 폴더 경로
		String orgName = mf.getOriginalFilename(); 
		String imgName = orgName.substring(0, orgName.lastIndexOf('.'));
		
		// 이미지 파일 확장자만 추출
		String ext = orgName.substring(orgName.lastIndexOf('.'));
		System.out.println("subString 확장자 이름 : " + ext);
		
		// 이름에 실행되는 시간 넣어주기 
		long date = System.currentTimeMillis();
		// 새로운 이름 만들기
		String newName = imgName+date+ext;
		System.out.println(newName);
		
		// imgPath 다시 만들어주기(newName사용)
		String imgPath = path + "\\" + newName;
		System.out.println("newName으로 새로만든 imgPath : " + imgPath);
		
		// 저장해주기
		File file = new File(imgPath);
		File copyFile = new File(imgPath);
		
		mf.transferTo(copyFile);
		
		// dto에 세팅 
		String type = request.getParameter("type");
		if(type.equals("outcome") || type.equals("income")){
			budgetDetailDTO.setImg(newName);
			System.out.println("되낭" + budgetDetailDTO.getImg());
		}else {
			noBugetDetailDTO.setImg(newName);
			System.out.println("예산 외 되냥 " + budgetDetailDTO.getImg());
		}
		
	}

}
