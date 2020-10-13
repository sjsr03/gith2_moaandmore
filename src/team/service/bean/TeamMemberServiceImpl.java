package team.service.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public List<TeamMemberDTO> selectAllbyTeamNo(int team_no) throws SQLException {
		//String id = (String)RequestContextHolder.getRequestAttributes().getAttribute("memId", RequestAttributes.SCOPE_SESSION);
		return teamMemDao.selectAllByTeam(team_no);
	}
	
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
			}
		}
		
		for(int i=0;i<realNickList.size();i++) {
			realIdList.add(memDao.selectOneByNick(realNickList.get(i)));
		}
		
		
		for(int i=0;i<realNickList.size();i++) {
			System.out.println(i+" : "+teamDTO.getTeam_no()+" "+realIdList.get(i)+" "+ realNickList.get(i));
			TeamMemberDTO tmp = new TeamMemberDTO(teamDTO.getTeam_no(), realIdList.get(i), realNickList.get(i), 0);
			teamMemDao.insertOne(tmp);
		}
		
	}

	@Override
	public void deleteTeamMemberAll(int teamNo) throws SQLException {
		teamMemDao.deleteTeamMemberAll(teamNo);
	}

	@Override
	public List<Integer> getTeamAvgArticles(List<TeamDTO> teamList) throws SQLException {
		List<List<TeamMemberDTO>> member_list = new ArrayList<List<TeamMemberDTO>>();
		List<Integer> avg_list = new ArrayList<Integer>();
		
		for(int i=0;i<teamList.size();i++)
			member_list.add(teamMemDao.selectAllByTeam(teamList.get(i).getTeam_no()));
		
		
		if(member_list != null) {
			for(int i=0;i<member_list.size();i++) {
				int avg = 0;
				if(member_list.get(i) == null) {
					avg_list.add(0);
				} else {
					for(int j=0;j<member_list.get(i).size();j++) {
						avg += (member_list.get(i).get(j).getSaving()*100/teamList.get(i).getAmount());
					}
					
					if(member_list.get(i).size() == 0)
						avg_list.add(0);
					else
						avg_list.add(avg/member_list.get(i).size());
				}
			}
		}else {
			for(int i=0;i<teamList.size();i++)
				avg_list.add(0);
		}
		
		return avg_list;
	}
}