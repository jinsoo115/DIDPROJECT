package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;


public class NoticeDao {
	private static NoticeDao instance;
	private NoticeDao(){}
	public static NoticeDao getInstance() {
		if(instance == null){
			instance = new NoticeDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> noticeAll() {
		String sql = "SELECT * "
				+ "FROM (SELECT ROWNUM RN, A.* "
				+ "FROM (SELECT * "
				+ "FROM NOTICE) A) "
				+ "WHERE RN BETWEEN (1-1) * 10+1 AND 1*10 "
				+ "ORDER BY BOARD_NO DESC";
		return jdbc.selectList(sql);
	}
	public int noticeInsert(String title, String content, String id ) {
		String sql = "INSERT INTO NOTICE "
				+ "VALUES((SELECT NVL(MAX(BOARD_NO),0)+1 FROM NOTICE),?,?,(TO_CHAR(SYSDATE,'YYYYMMDD')),?)";
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(id);
		return jdbc.update(sql, param);
	}
	public Map<String, Object> noticeRead(int currentBoardNo) {
		String sql = "SELECT * "
				+ "FROM NOTICE "
				+ "WHERE BOARD_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(currentBoardNo);
		return jdbc.selectOne(sql, param);
	}
	public int noticeDelete(int currentBoardNo) {
		String sql = "DELETE FROM NOTICE "
				+ "WHERE BOARD_NO = ? ";
		List<Object> param = new ArrayList<>();
		param.add(currentBoardNo);
		return jdbc.update(sql, param);
	}
	public int noticeUpdate(String title, String content, int currentBoardNo) {
		String sql = "UPDATE NOTICE "
				+ "SET BOARD_TITLE = ?, BOARD_CONTENT = ? "
				+ "WHERE BOARD_NO = ? ";
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(currentBoardNo);
		return jdbc.update(sql, param);
	}
	public Map<String, Object> check() {
		String sql = "SELECT MANAGER_ID "
				+ "FROM MANAGER "
				+ "WHERE MANAGER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MANAGER_ID"));
		return jdbc.selectOne(sql, param);
	}
	
	
}
