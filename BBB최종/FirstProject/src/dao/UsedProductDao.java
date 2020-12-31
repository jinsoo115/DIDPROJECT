package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class UsedProductDao {
	private static UsedProductDao instance;
	private UsedProductDao(){}
	public static UsedProductDao getInstance() {
		if(instance == null){
			instance = new UsedProductDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> viewusedProduct(int page, int pagesize){
		String sql = "SELECT * "
				+ " FROM"
				+ " (SELECT ROWNUM RN , A.* , b.BUYER_NAME, C.GRADE_NAME "
				+ " FROM "
				+ " (SELECT USEDPROD_ID, USEDPROD_NAME, USEDPROD_PRICE, USEDPROD_WRITER, BUYER_ID, GRADE_ID"
				+ " FROM USEDPROD) A, BUYER B, GRADE C"
				+ " WHERE A.BUYER_ID =  B.BUYER_ID"
				+ " AND A.GRADE_ID = C.GRADE_ID)"
				+ " WHERE RN BETWEEN (?-1)*?+1 AND ?*?";
		List<Object> param = new ArrayList<>();
		param.add(page);
		param.add(pagesize);
		param.add(page);
		param.add(pagesize);
		return jdbc.selectList(sql, param);
	}
	
	public Map<String, Object> detailusedProduct(int num){
		String sql = "SELECT * "
				+ " FROM"
				+ " (SELECT ROWNUM RN , A.* , b.BUYER_NAME, c.grade_name"
				+ " FROM "
				+ " (SELECT usedprod_id, usedprod_name, usedprod_price, usedprod_writer, "
				+ " usedprod_detail, usedprod_page, buyer_id, grade_id"
				+ " FROM usedprod) A, buyer b, grade c"
				+ " where A.buyer_id = b.buyer_id"
				+ " and A.grade_id = c.grade_id)"
				+ "WHERE RN = ?";

		List<Object> param = new ArrayList<>();
		param.add(num);
		return jdbc.selectOne(sql, param);
	}
	
	public Map<String, Object> prodMax() {
		String sql = "SELECT COUNT(*) AS COUNT "
				+ "FROM USEDPROD";
		return jdbc.selectOne(sql);
	}
	
	public List<Map<String, Object>> usedProductSearch_title(String keyword){
		String sql = "select a.*, b.buyer_name, c.grade_name"
				+ " from usedprod a, buyer b, grade c"
				+ " where a.buyer_id = b.buyer_id"
				+ " and a.grade_id = c.grade_id"
				+ " and a.usedprod_name like '%' || ? || '%'";
		
		List<Object> param = new ArrayList<>();
		param.add(keyword);
		return jdbc.selectList(sql, param);
	}
	
	public List<Map<String, Object>> usedProductSearch_publisher(String keyword){
		String sql = "select a.*, b.buyer_name, c.grade_name"
				+ " from usedprod a, buyer b, grade c"
				+ " where a.buyer_id = b.buyer_id"
				+ " and a.grade_id = c.grade_id"
				+ " and b.buyer_name like '%' || ? || '%'";
		
		List<Object> param = new ArrayList<>();
		param.add(keyword);
		return jdbc.selectList(sql, param);
	}
	
	public List<Map<String, Object>> usedProductSearch_writer(String keyword){
		String sql = "select a.*, b.buyer_name, c.grade_name"
				+ " from usedprod a, buyer b, grade c"
				+ " where a.buyer_id = b.buyer_id"
				+ " and a.grade_id = c.grade_id"
				+ " and a.usedprod_writer like '%' || ? || '%'";
		
		List<Object> param = new ArrayList<>();
		param.add(keyword);
		return jdbc.selectList(sql, param);
	}
	
	public List<Map<String, Object>> usedProductlprodcheck(){
		String sql = "select *"
				+ " from lprod";
		
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> usedProductlprodshow(Object lprodid) {
		String sql = "select a.*, c.buyer_name, d.grade_name"
				+ " from usedprod a, lprod b, buyer c, grade d"
				+ " where a.lprod_gu = b.lprod_gu"
				+ " and a.buyer_id = c.buyer_id"
				+ " and a.grade_id = d.grade_id"
				+ " and a.lprod_gu = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(lprodid);
		return jdbc.selectList(sql, param);
	}
	
	public Map<String, Object> usedProductSearch_detail(Object prodid) {
		String sql = "select a.*, b.buyer_name, c.grade_name"
				+ " from usedprod a, buyer b, grade c"
				+ " where a.buyer_id = b.buyer_id"
				+ " and a.grade_id = c.grade_id"
				+ " and a.usedprod_id = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(prodid);
		
		return jdbc.selectOne(sql, param);
	}
	
	public Map<String, Object> usedProductlprod_detail(Object prodid) {
		String sql = "select a.*, c.buyer_name, d.grade_name"
				+ " from usedprod a, lprod b, buyer c, grade d"
				+ " where a.lprod_gu = b.lprod_gu"
				+ " and a.buyer_id = c.buyer_id"
				+ " and a.grade_id = d.grade_id"
				+ " and a.usedprod_id = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(prodid);
		return jdbc.selectOne(sql, param);
	}
	public Map<String, Object> prodCartCheck() {
		String sql = "SELECT * FROM CART WHERE MEM_ID = ? AND CART_YN = 0";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		
		return jdbc.selectOne(sql, param);
	}
	public int cartInsert() {
		String sql = "INSERT INTO CART VALUES((SELECT NVL(MAX(CART_NO),0)+1 FROM CART), ?, (TO_CHAR(SYSDATE,'YYYYMMDD')), 0, 0)";
		List<Object> p = new ArrayList<>();
		p.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, p);
	}
	public Map<String, Object> prodBasketCheck(Object prodid) {
		String sql = "SELECT * "
				+ "FROM USEDBASKET "
				+ "WHERE USEDPROD_ID = ? "
				+ " AND CART_NO = (SELECT MAX(CART_NO) FROM CART WHERE MEM_ID = ?)";
		List<Object> param = new ArrayList<>();
		param.add(prodid);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectOne(sql, param);
	}
	public int prodBasketInsert(Object prodid, int pcount) {
		String sql = "INSERT INTO USEDBASKET VALUES((SELECT MAX(CART_NO) FROM CART WHERE MEM_ID = ?), ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(prodid);
		param.add(pcount);
		return jdbc.update(sql, param);
	}
	public int prodBasketUpdate(Object prodid, int pcount) {
		String sql = "UPDATE USEDBASKET SET USEDBASKET_QTY = USEDBASKET_QTY + ? WHERE USEDPROD_ID = ? AND CART_NO = (SELECT MAX(CART_NO) FROM CART WHERE MEM_ID = ?)";
		List<Object> param = new ArrayList<>();
		param.add(pcount);
		param.add(prodid);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public int prodCartUp(Object prodid) {
		String sql = "UPDATE CART SET CART_PRICE = CART_PRICE + (SELECT SUM(U.USEDPROD_PRICE*B.USEDBASKET_QTY) AS PRICE "
				+ "FROM USEDPROD U, USEDBASKET B, CART C "
				+ "WHERE U.USEDPROD_ID = B.USEDPROD_ID "
				+ "AND B.CART_NO = C.CART_NO "
				+ "AND CART_YN = 0 AND C.MEM_ID = ? "
				+ "AND U.USEDPROD_ID = ?) "
				+ "WHERE MEM_ID = ? AND CART_YN = 0";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(prodid);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public Map<String, Object> moneyCheck(int pcount, Object prodid) {
		String sql = "SELECT USEDPROD_PRICE * ? AS USEDPROD_PRICE "
				+ "FROM USEDPROD "
				+ "WHERE USEDPROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(pcount);
		param.add(prodid);
		
		return jdbc.selectOne(sql, param);
	}
	public int orderCartInsert() {
		String sql = "INSERT INTO CART VALUES((SELECT NVL(MAX(CART_NO),0)+1 FROM CART), ?, (TO_CHAR(SYSDATE,'YYYYMMDD')), 0, 1)";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public int orderProdBasketInsert(Object prodid, int pcount) {
		String sql = "INSERT INTO USEDBASKET VALUES((SELECT MAX(CART_NO) FROM CART WHERE MEM_ID = ?), ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(prodid);
		param.add(pcount);
		return jdbc.update(sql, param);
	}
	public int orderCartUpdate() {
		String sql = "UPDATE CART SET CART_PRICE = (SELECT SUM(U.USEDPROD_PRICE*B.USEDBASKET_QTY) AS PRICE "
				+ "FROM USEDPROD U, USEDBASKET B, (SELECT MAX(CART_NO) AS CART_NO "
				+ "FROM CART WHERE MEM_ID = ? "
				+ "AND CART_YN = 1 "
				+ "AND MEM_ID = ?) C "
				+ "WHERE U.USEDPROD_ID = B.USEDPROD_ID"
				+ "  AND B.CART_NO = C.CART_NO) "
				+ "WHERE MEM_ID = ? "
				+ "AND CART_NO = (SELECT MAX(CART_NO) FROM CART)";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public int mileageUpdate(int money) {
		String sql = "UPDATE MEMBER SET MEM_MILEAGE = MEM_MILEAGE + ? WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(money);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public int moneyUpdate(int money) {
		String sql = "UPDATE MEMBER SET MEM_MONEY = MEM_MONEY - ? WHERE MEM_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(money);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public int pay() {
		String sql = "INSERT INTO PAY VALUES((SELECT NVL(MAX(PAY_NO),0)+1 FROM PAY), "
				+ "(SELECT MAX(CART_NO) AS CART_NO FROM CART WHERE MEM_ID = ? AND CART_YN = 1 AND MEM_ID = ?), "
				+ "(TO_CHAR(SYSDATE,'YYYYMMDD')), "
				+ "(SELECT CART_PRICE FROM CART WHERE CART_NO = (SELECT MAX(CART_NO) AS CART_NO FROM CART WHERE MEM_ID = ? AND CART_YN = 1 AND MEM_ID = ?)), "
				+ "NULL)";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public Map<String, Object> selectMember() {
		String sql = "SELECT * "
				+ "FROM MEMBER "
				+ "WHERE MEM_ID = ? ";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectOne(sql, param);
	}
}
