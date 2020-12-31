package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.JDBCUtil;

public class NewProductDao {
	private static NewProductDao instance;
	private NewProductDao(){}
	public static NewProductDao getInstance() {
		if(instance == null){
			instance = new NewProductDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> viewnewProduct(int page, int pagesize){
		String sql = "SELECT * "
				+ " FROM"
				+ " (SELECT ROWNUM RN , A.* , b.BUYER_NAME "
				+ " FROM "
				+ " (SELECT prod_id, prod_name, prod_price, prod_writer, BUYER_ID"
				+ " FROM prod) A, buyer b"
				+ " where a.BUYER_ID = b.buyer_id)"
				+ "WHERE RN BETWEEN (?-1)*?+1 AND ?*?";
		List<Object> param = new ArrayList<>();
		param.add(page);
		param.add(pagesize);
		param.add(page);
		param.add(pagesize);
		return jdbc.selectList(sql, param);
	}
	
	public Map<String, Object> DetailProduct(int num){
		String sql = "SELECT * "
				+ " FROM"
				+ " (SELECT ROWNUM RN , A.* , b.BUYER_NAME "
				+ " FROM "
				+ " (SELECT prod_id, prod_name, prod_price, prod_writer, prod_detail, prod_page, BUYER_ID"
				+ " FROM prod) A, buyer b"
				+ " where a.BUYER_ID = b.buyer_id)"
				+ "WHERE RN = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(num);
		return jdbc.selectOne(sql, param);
	}
	public List<Map<String, Object>> newProductSearch_title(String keyword){
		String sql = "select a.*, b.buyer_name"
				+ " from prod a, buyer b"
				+ " where a.BUYER_ID = b.buyer_id"
				+ " and a.prod_name like '%' || ? || '%'";
		

		List<Object> param = new ArrayList<>();
		param.add(keyword);
		return jdbc.selectList(sql, param);
	}
	
	public List<Map<String, Object>> newProductSearch_publisher(String keyword){
		String sql = "select a.*, b.buyer_name"
				+ " from prod a, buyer b"
				+ " where a.BUYER_ID = b.buyer_id"
				+ " and b.buyer_name like '%' || ? || '%'";
		
		List<Object> param = new ArrayList<>();
		param.add(keyword);
		return jdbc.selectList(sql, param);
	}
	
	public List<Map<String, Object>> newProductSearch_writer(String keyword){
		String sql = "select a.*, b.buyer_name"
				+ " from prod a, buyer b"
				+ " where a.BUYER_ID = b.buyer_id"
				+ " and a.prod_writer like '%' || ? || '%'";
		

		List<Object> param = new ArrayList<>();
		param.add(keyword);
		return jdbc.selectList(sql, param);
	}
	
	public Map<String, Object> newProductSearch_detail(Object id){
		String sql = "select a.*, b.buyer_name"
				+ " from prod a, buyer b"
				+ " where a.BUYER_ID = b.buyer_id"
				+ " and a.prod_id = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(id);
		
		return jdbc.selectOne(sql, param);
	}
	
	public List<Map<String, Object>> newProductlprodcheck(){
		String sql = "select *"
				+ " from lprod";
		
		return jdbc.selectList(sql);
	}
	
	public List<Map<String, Object>> newProductlprodshow(Object lprodid){
		String sql = "select a.*, c.buyer_name"
				+ " from prod a, lprod b, buyer c"
				+ " where a.lprod_gu = b.lprod_gu"
				+ " and a.BUYER_ID = c.buyer_id"
				+ " and a.lprod_gu = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(lprodid);
		return jdbc.selectList(sql, param);
	}
	
	public Map<String, Object> newProductlprod_detail(Object prodid){
		String sql = "select a.*, c.buyer_name"
				+ " from prod a, lprod b, buyer c"
				+ " where a.lprod_gu = b.lprod_gu"
				+ " and a.BUYER_ID = c.buyer_id"
				+ " and a.prod_id = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(prodid);
		return jdbc.selectOne(sql, param);
	}
	public Map<String, Object> prodMax() {
		String sql = "SELECT COUNT(*) AS COUNT "
				+ "FROM PROD";
		return jdbc.selectOne(sql);
	}
	
	public int prodBasketInsert(Object prodid, int pcount) {
		String sql = "INSERT INTO BASKET VALUES((SELECT MAX(CART_NO) FROM CART WHERE MEM_ID = ?), ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(prodid);
		param.add(pcount);
		return jdbc.update(sql, param);
		
	}
	public int prodCartUp(Object prodid) {
		String sql = "UPDATE CART SET CART_PRICE = CART_PRICE + (SELECT SUM(P.PROD_PRICE*B.BASKET_QTY) AS PRICE "
				+ "FROM PROD P, BASKET B, CART C "
				+ "WHERE P.PROD_ID = B.PROD_ID "
				+ "AND B.CART_NO = C.CART_NO "
				+ "AND CART_YN = 0 AND C.MEM_ID = ? "
				+ "AND P.PROD_ID = ?) "
				+ "WHERE MEM_ID = ? AND CART_YN = 0";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(prodid);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
		
	}
	public Map<String, Object> prodBasketCheck(Object prodid) {
		String sql = "SELECT * "
				+ "FROM BASKET "
				+ "WHERE PROD_ID = ? "
				+ " AND CART_NO = (SELECT MAX(CART_NO) FROM CART WHERE MEM_ID = ?)";
		List<Object> param = new ArrayList<>();
		param.add(prodid);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.selectOne(sql, param);
	}
	public int prodBasketUpdate(Object prodid, int pcount) {
		String sql = "UPDATE BASKET SET BASKET_QTY = BASKET_QTY + ? WHERE PROD_ID = ? AND CART_NO = (SELECT MAX(CART_NO) FROM CART WHERE MEM_ID = ?)";
		List<Object> param = new ArrayList<>();
		param.add(pcount);
		param.add(prodid);
		param.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, param);
	}
	public int cartInsert() {
		String sql = "INSERT INTO CART VALUES((SELECT NVL(MAX(CART_NO),0)+1 FROM CART), ?, (TO_CHAR(SYSDATE,'YYYYMMDD')), 0, 0)";
		List<Object> p = new ArrayList<>();
		p.add(Controller.loginMember.get("MEM_ID"));
		return jdbc.update(sql, p);
	}
	public Map<String, Object> prodCartCheck() {
		String sql = "SELECT * FROM CART WHERE MEM_ID = ? AND CART_YN = 0";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		
		return jdbc.selectOne(sql, param);
	}
	
	public Map<String, Object> moneyCheck(int pcount, Object prodid) {
		String sql = "SELECT PROD_PRICE * ? AS PROD_PRICE "
				+ "FROM PROD "
				+ "WHERE PROD_ID = ?";
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
		String sql = "INSERT INTO BASKET VALUES((SELECT MAX(CART_NO) FROM CART WHERE MEM_ID = ?), ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(Controller.loginMember.get("MEM_ID"));
		param.add(prodid);
		param.add(pcount);
		return jdbc.update(sql, param);
	}
	public int orderCartUpdate() {
		String sql = "UPDATE CART SET CART_PRICE = (SELECT SUM(P.PROD_PRICE*B.BASKET_QTY) AS PRICE "
				+ "FROM PROD P, BASKET B, (SELECT MAX(CART_NO) AS CART_NO "
				+ "FROM CART WHERE MEM_ID = ? "
				+ "AND CART_YN = 1 "
				+ "AND MEM_ID = ?) C "
				+ "WHERE P.PROD_ID = B.PROD_ID"
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
	
	
}//

