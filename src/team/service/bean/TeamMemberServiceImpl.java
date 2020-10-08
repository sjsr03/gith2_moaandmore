package team.service.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import member.model.dao.MemberDAO;
import team.model.dao.TeamMemberDAO;
import team.model.dto.TeamDTO;
import team.model.dto.TeamMemberDTO;
@Service
public class TeamMemberServiceImpl implements TeamMemberService{
	
	@Autowired
	private MemberDAO memDao = null; 
	
	@Autowired
	private TeamMemberDAO teamMemDao = null; 
	

	@Override
	public void insertAll(TeamDTO teamDTO, String nicknames) throws SQLException {
		String[] nickList = nicknames.split(",");
		
		for(int i=0;i<nickList.length;i++) {
			if(teamDTO.getLeader().equals(nickList[i])) {
				nickList[i] = "";
			}
		}
		
		ArrayList<String> realNickList = new ArrayList<String>();
		ArrayList<String> realIdList = new ArrayList<String>();
		
		realNickList.add(teamDTO.getLeader());
		
		for(int i=0;i<nickList.length;i++) {
			if(!nickList[i].equals("")) {
				 realNickList.add(nickList[i]);
				 realIdList.add(memDao.selectOneByNick(realNickList.get(i)));
			}
		}
		
		
		for(int i=0;i<realNickList.size();i++) {
			TeamMemberDTO tmp = new TeamMemberDTO(teamDTO.getTeam_no(), realIdList.get(i), realNickList.get(i), 0);
			teamMemDao.insertOne(tmp);
		}
		
	}

}
