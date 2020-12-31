package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.JDBCUtil;

public class ManagerGradeDao {
	private static ManagerGradeDao instance;
	private ManagerGradeDao(){}
	public static ManagerGradeDao getInstance() {
		if(instance == null){
			instance = new ManagerGradeDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> lprodList() {
		String sql = "SELECT * FROM GRADE ORDER BY GRADE_ID";
		return jdbc.selectList(sql);
	}
	public int lprodInsert(String gradeId, String gradeName) {
		String sql = "INSERT INTO GRADE VALUES (?,?)";
		List<Object> param = new ArrayList<>();
		param.add(gradeId);
		param.add(gradeName);
		return jdbc.update(sql, param);
	}
	public int lprodDelete(String gradeId) {
		String sql = "DELETE FROM GRADE WHERE GRADE_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(gradeId);
		return jdbc.update(sql, param);	
	}
}
