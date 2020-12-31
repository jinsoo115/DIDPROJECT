package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.JDBCUtil;

public class ManagerDao {
	private static ManagerDao instance;
	private ManagerDao(){}
	public static ManagerDao getInstance() {
		if(instance == null){
			instance = new ManagerDao();
		}
		return instance;
	}
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	public Map<String, Object> selectManager(String id, String password) {
		String sql = "SELECT * "
				+ "FROM MANAGER "
				+ "WHERE MANAGER_ID = ? "
				+ "AND MANAGER_PASSWORD = ?";
		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
}
