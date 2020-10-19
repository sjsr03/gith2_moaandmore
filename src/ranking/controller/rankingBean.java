package ranking.controller;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
@RequestMapping("ranking")
public class rankingBean {
	
	@Autowired
	SqlSessionTemplate sqlSession = null;
	
	
	@RequestMapping("getRealTimeRanking.moa")
	public String getRealTimeRanking() {
		int res = sqlSession.selectOne("totalRank.checkUpdateReg");
		
		if(res == 0) {//업데이트 안된 상태
			
		}
		
		
		return "realTimeRanking";
	}
	
}
