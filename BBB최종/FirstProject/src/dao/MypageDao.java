package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class MypageDao {
	private static MypageDao instance;
	private MypageDao(){}
	public static MypageDao getInstance() {
		if(instance == null){
			instance = new MypageDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public Map<String, Object> selectMember() {
		String sql = "SELECT * "
				+ "FROM MEMBER "
				+ "WHERE MEM_ID = ? ";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectOne(sql, param);
	}
	
	public int Updatephone(String phonenum){
		String sql = "update member"
				+ " set mem_phone = ?"
				+ " where mem_id = ?";
		List<Object> param = new ArrayList<>();
		param.add(phonenum);
		param.add(Controller.loginMember.get("MEM_ID"));
		
		return jdbc.update(sql, param);
	}
	
	public int Updateemail(String email) {
		String sql = "update member"
				+ " set mem_email = ?"
				+ " where mem_id = ?";
		List<Object> param = new ArrayList<>();
		param.add(email);
		param.add(Controller.loginMember.get("MEM_ID"));
		
		return jdbc.update(sql, param);
	}
	
	public List<Map<String, Object>> selectAdd(String doroAdd){
		String sql = "SELECT ADD_POST, ADD_NAME "
				+ "FROM ADDER "
				+ "WHERE ADD_NAME LIKE '%' || ? || '%'";
		List<Object> param = new ArrayList<>();
		param.add(doroAdd);
		return jdbc.selectList(sql, param);
	}
	
	public int Updateaddre(Object postnum, String add2) {
		String sql = "update member"
				+ " set(mem_post, mem_add1) = (select add_post, add_name from adder where add_post = ?),"
				+ " mem_add2 = ?"
				+ " where mem_id = ?";
		List<Object> param = new ArrayList<>();
		param.add(postnum);
		param.add(add2);
		param.add(Controller.loginMember.get("MEM_ID"));
	
		return jdbc.update(sql, param);
	}
	
	public int Updatepassword(String password) {
		String sql = "update member"
				+ " set mem_password = ?"
				+ " where mem_id = ?";
		List<Object> param = new ArrayList<>();
		param.add(password);
		param.add(Controller.loginMember.get("MEM_ID"));
		
		return jdbc.update(sql, param);
	}
	
	public int changemoney(int mileage) {
		String sql = "update member"
				+ " set mem_money = mem_money + ?, "
				+ " mem_mileage = mem_mileage - ?"
				+ " where mem_id = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(mileage);
		param.add(mileage);
		param.add(Controller.loginMember.get("MEM_ID"));
		
		return jdbc.update(sql, param);
	}
	public int unregister() {
		String sql = "DELETE FROM MEMBER "
				+ "WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public List<Map<String, Object>> orderList() {
		String sql = "SELECT D.PROD_NAME, D.PROD_PRICE, C.BQTY, D.PROD_PRICE * C.BQTY AS PRICE "
				+ "FROM (SELECT A.PROD_ID AS PID, A.BASKET_QTY AS BQTY "
				+ "FROM BASKET A, (SELECT CART_NO "
				+ "FROM CART "
				+ "WHERE MEM_ID = ? "
				+ "AND CART_YN = 1) B "
				+ "WHERE A.CART_NO = B.CART_NO) C, PROD D "
				+ "WHERE D.PROD_ID = C.PID";
	
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectList(sql, param);
	}
	public int moneyCharging(int money) {	
		String sql = "UPDATE MEMBER SET MEM_MONEY = MEM_MONEY + ? WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(money);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public List<Map<String, Object>> usedOrderList() {
		String sql = "SELECT D.USEDPROD_NAME, D.USEDPROD_PRICE, C.BQTY, D.USEDPROD_PRICE * C.BQTY AS PRICE "
				+ "FROM (SELECT A.USEDPROD_ID AS PID, A.USEDBASKET_QTY AS BQTY "
				+ "FROM USEDBASKET A, (SELECT CART_NO "
				+ "FROM CART "
				+ "WHERE MEM_ID = ? "
				+ "AND CART_YN = 1) B "
				+ "WHERE A.CART_NO = B.CART_NO) C, USEDPROD D "
				+ "WHERE D.USEDPROD_ID = C.PID";
	
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectList(sql, param);
	}
}
