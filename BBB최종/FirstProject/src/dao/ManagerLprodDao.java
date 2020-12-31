package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class ManagerLprodDao {
	private static ManagerLprodDao instance;
	private ManagerLprodDao(){}
	public static ManagerLprodDao getInstance() {
		if(instance == null){
			instance = new ManagerLprodDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> lprodList() {
		String sql = "SELECT * FROM LPROD ORDER BY LPROD_GU";
		return jdbc.selectList(sql);
	}
	public int lprodInsert(String lprodGu, String lprodName) {
		String sql = "INSERT INTO LPROD VALUES (?,?)";
		List<Object> param = new ArrayList<>();
		param.add(lprodGu);
		param.add(lprodName);
		return jdbc.update(sql, param);
	}
	public int lprodDelete(String lprodGu) {
		String sql = "DELETE FROM LPROD WHERE LPROD_GU = ?";
		List<Object> param = new ArrayList<>();
		param.add(lprodGu);
		return jdbc.update(sql, param);		
	}
	
}
