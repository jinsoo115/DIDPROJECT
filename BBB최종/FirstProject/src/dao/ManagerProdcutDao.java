package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class ManagerProdcutDao {
	private static ManagerProdcutDao instance;
	private ManagerProdcutDao(){}
	public static ManagerProdcutDao getInstance() {
		if(instance == null){
			instance = new ManagerProdcutDao();
		}
		return instance;
	}
	
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> newproductname(String bookName) {
		String sql = "select a.prod_id, a.prod_name, a.prod_writer, b.buyer_name"
				+ " from prod a, buyer b"
				+ " where a.buyer_id = b.buyer_id"
				+ " and prod_name like '%' || ? || '%'";
		
		List<Object> param = new ArrayList<>();
		param.add(bookName);
		return jdbc.selectList(sql, param);
	}
	public List<Map<String, Object>> selectBuyer(String buyer) {
		String sql = "SELECT BUYER_ID, BUYER_NAME "
				+ "FROM BUYER "
				+ "WHERE BUYER_NAME LIKE '%' || ? || '%' "
				+ "ORDER BY BUYER_ID";
		List<Object> param = new ArrayList<>();
		param.add(buyer);
		return jdbc.selectList(sql, param);
	}
	public List<Map<String, Object>> selectLprod(String lprod) {
		String sql = "SELECT * "
				+ "FROM LPROD "
				+ "WHERE LPROD_NAME LIKE '%' || ? || '%' "
				+ "ORDER BY LPROD_GU";
		List<Object> param = new ArrayList<>();
		param.add(lprod);
		return jdbc.selectList(sql, param);
	}
	public int newProdInsert(List<Object> param) {
		String sql = "insert into prod"
				+ " values(?, ?, ?, ?, 0, null, to_char(sysdate, 'YYYYMMDD')"
				+ ", ?, ?, ?)";
		
		return jdbc.update(sql, param);
	}
	public Map<String,Object> getnum(String lprod){
		String sql = "select nvl(max(substr(prod_id,6)),0)+1 as num"
				+ " from prod"
				+ " where lprod_gu = ?";
		List<Object> param = new ArrayList<>();
		param.add(lprod);
		
		return jdbc.selectOne(sql, param);
	}
	public Map<String, Object> newprodSelect(String prodId) {
		String sql = "SELECT * "
				+ "FROM PROD "
				+ "WHERE PROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		return jdbc.selectOne(sql, param);
	}
	public int update(int price, String prodId) {
		String sql = "UPDATE PROD SET PROD_PRICE = ? WHERE PROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(price);
		param.add(prodId);
		return jdbc.update(sql, param);
	}
	public int delete(String prodId) {
		String sql = "DELETE FROM PROD WHERE PROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		return jdbc.update(sql, param);
	}
	public List<Map<String, Object>> usedproductname(String bookName) {
		String sql = "select a.usedprod_id, a.usedprod_name, a.usedprod_writer, b.buyer_name"
				+ " from usedprod a, buyer b"
				+ " where a.usedbuyer_id = b.buyer_id"
				+ " and usedprod_name like '%' || ? || '%'";
		
		List<Object> param = new ArrayList<>();
		param.add(bookName);
		return jdbc.selectList(sql, param);
	}
	public Map<String, Object> usedprodSelect(String prodId) {
		String sql = "SELECT * "
				+ "FROM USEDPROD "
				+ "WHERE USEDPROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		return jdbc.selectOne(sql, param);
	}
	public int usedupdate(int price, String prodId) {
		String sql = "UPDATE USEDPROD SET USEDPROD_PRICE = ? WHERE USEDPROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(price);
		param.add(prodId);
		return jdbc.update(sql, param);
	}
	public int useddelete(String prodId) {
		String sql = "DELETE FROM USEDPROD WHERE USEDPROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		return jdbc.update(sql, param);
	}
	public List<Map<String, Object>> grade() {
		String sql = "SELECT GRADE_NAME, SUBSTR(GRADE_ID,0,2) AS GRADE_ID FROM GRADE";
		return jdbc.selectList(sql);
	}
	public Map<String, Object> newproductnameAll(String prodId) {
		String sql = "SELECT * FROM PROD WHERE PROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(prodId);
		return jdbc.selectOne(sql, param);
	}
	public int usedProdInsert(String finalprodid, int price, String grade, Map<String, Object> listAll) {
		String sql = "INSERT INTO USEDPROD "
				+ "VALUES (?, ?, ?, ?, 0, ?, TO_CHAR(SYSDATE, 'YYYYMMDD'), ?, ?, ?, ?)";
		List<Object> param = new ArrayList<>();
		param.add(finalprodid);
		param.add(listAll.get("PROD_NAME"));
		param.add(price);
		param.add(listAll.get("PROD_WRITER"));
		param.add(listAll.get("PROD_DETAIL"));
		param.add(listAll.get("PROD_PAGE"));
		param.add(listAll.get("LPROD_GU"));
		param.add(listAll.get("BUYER_ID"));
		param.add(grade);
		return jdbc.update(sql, param);
	}
	
	
	
}//
