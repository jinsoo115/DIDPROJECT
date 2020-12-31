package dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class QnaDao {

	private static QnaDao instance;
	private QnaDao(){}
	public static QnaDao getInstance() {

		if(instance == null){
			instance = new QnaDao();
		}
		return instance;
	}
	static JDBCUtil jdbc = JDBCUtil.getInstance();
	public static int insertQuestion;
	public List<Map<String, Object>> showList() {
		String sql = "select *"
				+ " from qna"
				+ " order by qa_no desc";

		return jdbc.selectList(sql);
	}
	public Map<String, Object> check(Object object) {
		String sql = "SELECT MANAGER_ID"
				+ " FROM MANAGER"
				+ " WHERE MANAGER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(object);
		return jdbc.selectOne(sql, param);
	}

	public Map<String, Object> Match(String memberpassword, int currentQnaNo) {
		String sql = "SELECT *"
				+ " FROM QNA"
				+ " WHERE MEM_ID = ?"
				+ " and Q_PW = ?"
				+ " and qa_no = ?";

		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(memberpassword);
		param.add(currentQnaNo);
		return jdbc.selectOne(sql, param);
	}

	public Map<String, Object> MatchAll(int currentQnaNo, String memberpassword){
		String sql = "SELECT *"
				+ " FROM QNA"
				+ " WHERE qa_no = ?"
				+ " and Q_PW = ?";

		List<Object> param = new ArrayList<>();
		param.add(currentQnaNo);
		param.add(memberpassword);
		return jdbc.selectOne(sql, param);
	}
	
	public Map<String, Object> MathchAlluser(int currentQnaNo,
			String memberpassword) {
		String sql = "SELECT *"
				+ " FROM QNA"
				+ " WHERE qa_no = ?"
				+ " and Q_PW = ?"
				+ " and mem_id = ?";

		List<Object> param = new ArrayList<>();
		param.add(currentQnaNo);
		param.add(memberpassword);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectOne(sql, param);
	}

	public int questionUpdate(String title, String content, int currentQnaNo) {
		String sql = "UPDATE QNA"
				+ " SET Q_TITLE = ?, Q_CONTENT = ?"
				+ " WHERE QA_NO = ?"
				+ " AND MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(currentQnaNo);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}

	public int questionDelete(int currentQnaNo){
		String sql = "DELETE QNA"
				+ " WHERE QA_NO = ?"
				+ " AND MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(currentQnaNo);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}

	public int questionInsert(String title, String content, String password) {
		String sql = "insert into qna(qa_no, q_title, q_content, mem_id, q_date, q_pw)"
				+ " values((select nvl(max(qa_no),0)+1 from qna), ? , ?, "
				+ " ?,to_char(sysdate, 'YYYY/MM/DD'), ?)";
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(content);
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(password);
		return jdbc.update(sql, param);
	}
	public Map<String, Object> managerRead(int managerQnaNo) {
		String sql = "select *"
				+ " from qna"
				+ " where qa_no = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(managerQnaNo);
		return jdbc.selectOne(sql, param);
	}
	public int answerInsert(String content, int managerQnaNo) {
		String sql = "update qna"
				   + " set manager_id = ?, a_answer = ?, a_date = to_char(sysdate, 'YYYY/MM/DD')"
				   + " where qa_no = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MANAGER_ID"));
		param.add(content);
		param.add(managerQnaNo);
		return jdbc.update(sql, param);
	}
	public Map<String, Object> checkpassword(int currentQnaNo) {
		String sql = "select q_pw"
				+ " from qna"
				+ " where qa_no = ?";
		List<Object> param = new ArrayList<>();
		param.add(currentQnaNo);
		
		return jdbc.selectOne(sql, param);
	}
	public int answerDelete(int ManagerQnaNo) {
		String sql = "DELETE QNA"
				+ " WHERE QA_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(ManagerQnaNo);
		return jdbc.update(sql, param);
	}
	
	public int answerUpdate(String title, int ManagerQnaNo) {
		String sql = "update qna"
				+ " set a_answer = ?"
				+ " where qa_no = ?";
		List<Object> param = new ArrayList<>();
		param.add(title);
		param.add(ManagerQnaNo);
		return jdbc.update(sql, param);
	}

	
}//
