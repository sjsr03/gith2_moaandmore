package ranking.controller;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ranking.model.dto.TotalRankDTO;

@EnableWebMvc
@Controller
@RequestMapping("ranking")
public class rankingBean {
	
	@Autowired
	SqlSessionTemplate sqlSession = null;
	
	
	@RequestMapping("getRealTimeRanking.moa")
	public String getRealTimeRanking(Model model) {
	
		
		return "realTimeRanking";
		
	}
	@RequestMapping("getRealTimeRankingPro.moa")
	public @ResponseBody List getRealTimeRankingPro(Model model) {
		
		//1이면 어제날짜, 0이면 오늘날짜
		int res = sqlSession.selectOne("totalRank.checkUpdateReg");
	
		if(res == 1) {//업데이트 안된 상태. total_rank테이블 지우고, 새로 insert
			sqlSession.delete("totalRank.deleteAll");
			sqlSession.insert("totalRank.insertAll");
			sqlSession.update("totalRank.updateReg"); 
		}
		
		
		List<TotalRankDTO> list= sqlSession.selectList("totalRank.selectAll");	
		
		List nickList = new ArrayList();
		for(int i = 0 ; i <list.size(); i++) {
			String nick = sqlSession.selectOne("member.getNickname", list.get(i).getNickname());
			nickList.add(nick);
			
		}
		
		return nickList;
	}
	
}
