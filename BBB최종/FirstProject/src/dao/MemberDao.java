package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class MemberDao {
	private static MemberDao instance;
	private MemberDao(){}
	public static MemberDao getInstance() {
		if(instance == null){
			instance = new MemberDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectAdd(String doroAdd){
		String sql = "SELECT ADD_POST, ADD_NAME "
				+ "FROM ADDER "
				+ "WHERE ADD_NAME LIKE '%' || ? || '%'";
		List<Object> param = new ArrayList<>();
		param.add(doroAdd);
		return jdbc.selectList(sql, param);
		
	}
	
	public Map<String, Object> selectMember(String id, String password) {
		String sql = "SELECT * "
				+ "FROM MEMBER "
				+ "WHERE MEM_ID = ? "
				+ "AND MEM_PASSWORD = ?";
		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
	public int insert(Map<String, Object> param) {
		String sql = "INSERT INTO MEMBER (MEM_ID,MEM_PASSWORD,MEM_NAME,MEM_GENDER,MEM_PHONE,MEM_EMAIL, MEM_ADD1, MEM_ADD2, MEM_POST) "
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		List<Object> p = new ArrayList<>();
		p.add(param.get("MEM_ID"));
		p.add(param.get("MEM_PASSWORD"));
		p.add(param.get("MEM_NAME"));
		p.add(param.get("MEM_GENDER"));
		p.add(param.get("MEM_PHONE"));
		p.add(param.get("MEM_EMAIL"));
		p.add(param.get("MEM_ADD1"));
		p.add(param.get("MEM_ADD2"));
		p.add(param.get("MEM_POST"));
		return jdbc.update(sql, p);
	}
	public List<Map<String, Object>> checkId(String id){
		String sql = "SELECT MEM_ID, MANAGER_ID "
				+ "FROM MEMBER, MANAGER "
				+ "WHERE MEM_ID = ? or MANAGER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(id);
		return jdbc.selectList(sql, param);
	}
	
	
}
