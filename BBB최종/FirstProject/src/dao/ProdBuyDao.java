package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class ProdBuyDao {
	private static ProdBuyDao instance;
	private ProdBuyDao(){}
	public static ProdBuyDao getInstance() {
		if(instance == null){
			instance = new ProdBuyDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> allOrderList() {
		String sql = "SELECT ROWNUM RN, A.CART_NO, B.PROD_ID, B.PROD_NAME, B.PROD_PRICE, A.BASKET_QTY, B.PROD_PRICE * A.BASKET_QTY AS PRICE "
				+ "FROM (SELECT PROD_ID AS PID, BASKET_QTY, CART_NO "
				+ "FROM BASKET "
				+ "WHERE CART_NO = (SELECT CART_NO "
				+ "FROM CART "
				+ "WHERE MEM_ID = ? "
				+ "AND CART_YN = 0))A, PROD B "
				+ "WHERE A.PID = B.PROD_ID";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectList(sql,param);
	}
	public int prodBuy() {
		String sql = "UPDATE CART SET CART_YN = 1 "
				+ "WHERE CART_NO = (SELECT CART_NO "
				+ "FROM CART "
				+ "WHERE MEM_ID = ? "
				+ "AND CART_YN = 0)";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql,param);
	}
	public int moneyUpdate(int price) {
		String sql = "UPDATE MEMBER SET MEM_MONEY = MEM_MONEY - ? WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(price);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public int mileageUpdate(int price) {
		String sql = "UPDATE MEMBER SET MEM_MILEAGE = MEM_MILEAGE + ? WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(price);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	
	public int pay(int cartNo) {
		String sql = "INSERT INTO PAY VALUES((SELECT NVL(MAX(PAY_NO),0)+1 FROM PAY), ?, (TO_CHAR(SYSDATE,'YYYYMMDD')), (SELECT CART_PRICE FROM CART WHERE CART_NO = ?), NULL)";
		List<Object> param = new ArrayList<>();
		param.add(cartNo);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public Map<String, Object> noCheck() {
		String sql = "SELECT CART_NO FROM CART WHERE MEM_ID = ? AND CART_YN = 0";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectOne(sql, param);
	}
	public Map<String, Object> selectMember() {
		String sql = "SELECT * "
				+ "FROM MEMBER "
				+ "WHERE MEM_ID = ? ";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectOne(sql, param);
	}
	public int delete(Object prodId, Object cartNo) {
		String sql = "DELETE FROM BASKET "
				+ "WHERE PROD_ID = ? AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public Map<String, Object> countCheck(Object prodId, Object cartNo) {
		String sql = "SELECT BASKET_QTY "
				+ "FROM BASKET "
				+ "WHERE PROD_ID = ? "
				+ "AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		param.add(cartNo);
		return jdbc.selectOne(sql, param);
	}
	public int countChange(int count, Object prodId, Object cartNo) {
		String sql = "UPDATE BASKET SET BASKET_QTY = ? "
				+ "WHERE PROD_ID = ? "
				+ "AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(count);
		param.add(prodId);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public int countUp(Object prodId, Object cartNo) {
		String sql = "UPDATE BASKET SET BASKET_QTY = BASKET_QTY + 1 "
				+ "WHERE PROD_ID = ? "
				+ "AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public int countDown(Object prodId, Object cartNo) {
		String sql = "UPDATE BASKET SET BASKET_QTY = BASKET_QTY - 1 "
				+ "WHERE PROD_ID = ? "
				+ "AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public List<Map<String, Object>> allUsedOrderList() {
		String sql = "SELECT ROWNUM RN, A.CART_NO, B.USEDPROD_ID, B.USEDPROD_NAME, B.USEDPROD_PRICE, A.USEDBASKET_QTY, B.USEDPROD_PRICE * A.USEDBASKET_QTY AS PRICE "
				+ "FROM (SELECT USEDPROD_ID AS PID, USEDBASKET_QTY, CART_NO "
				+ "FROM USEDBASKET "
				+ "WHERE CART_NO = (SELECT CART_NO "
				+ "FROM CART "
				+ "WHERE MEM_ID = ? "
				+ "AND CART_YN = 0))A, USEDPROD B "
				+ "WHERE A.PID = B.USEDPROD_ID";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectList(sql, param);
	}
	public int usedDelete(Object prodId, Object cartNo) {
		String sql = "DELETE FROM USEDBASKET "
				+ "WHERE USEDPROD_ID = ? AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public int usedCountChange(int count, Object prodId, Object cartNo) {
		String sql = "UPDATE USEDBASKET SET USEDBASKET_QTY = ? "
				+ "WHERE USEDPROD_ID = ? "
				+ "AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(count);
		param.add(prodId);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public int usdeCountUp(Object prodId, Object cartNo) {
		String sql = "UPDATE USEDBASKET SET USEDBASKET_QTY = USEDBASKET_QTY + 1 "
				+ "WHERE USEDPROD_ID = ? "
				+ "AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public int usedCountDown(Object prodId, Object cartNo) {
		String sql = "UPDATE USEDBASKET SET USEDBASKET_QTY = USEDBASKET_QTY - 1 "
				+ "WHERE USEDPROD_ID = ? "
				+ "AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		param.add(cartNo);
		return jdbc.update(sql, param);
	}
	public Map<String, Object> usedCountCheck(Object prodId, Object cartNo) {
		String sql = "SELECT USEDBASKET_QTY "
				+ "FROM USEDBASKET "
				+ "WHERE USEDPROD_ID = ? "
				+ "AND CART_NO = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		param.add(cartNo);
		return jdbc.selectOne(sql, param);
	}
}
