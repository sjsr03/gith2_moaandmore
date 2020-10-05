package team.model.dao;

import org.mybatis.spring.SqlSessionTemplate;

public class TeamDAOImpl implements TeamDAO{
	
	private SqlSessionTemplate sqlSession = null;
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
}
