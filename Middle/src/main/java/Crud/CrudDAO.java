package Crud;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CrudDAO {
	@Autowired @Qualifier("hanul") SqlSession sql;
	
	public List<CrudVO> select(){
		return sql.selectList("crud.select");
	}
	
	public int insert(CrudVO vo) {
		return sql.insert("crud.insert",vo);
	}
	
	public int update(CrudVO vo) {
		return sql.update("crud.update", vo);
	}
	
	public int delete(CrudVO vo) {
		return sql.delete("crud.delete", vo);
	}
	
}
