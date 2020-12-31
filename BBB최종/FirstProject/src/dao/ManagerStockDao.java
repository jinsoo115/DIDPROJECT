package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class ManagerStockDao {
	private static ManagerStockDao instance;
	private ManagerStockDao(){}
	public static ManagerStockDao getInstance() {
		if(instance == null){
			instance = new ManagerStockDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> productList(int page, int pageSize) {
		String sql = "SELECT * "
				+ "FROM "
				+ "(SELECT ROWNUM RN , A.* , b.BUYER_NAME "
				+ "FROM "
				+ "(SELECT PROD_ID, PROD_NAME, PROD_TOTAL_STOCK, PROD_PRICE, PROD_WRITER, BUYER_ID AS PID "
				+ "FROM PROD) A, BUYER B "
				+ "WHERE A.PID = B.BUYER_ID) "
				+ "WHERE RN BETWEEN (?-1)*?+1 AND ?*?";
		List<Object> param = new ArrayList<>();
		param.add(page);
		param.add(pageSize);
		param.add(page);
		param.add(pageSize);
		return jdbc.selectList(sql, param);
	}
	
	public Map<String, Object> prodMax() {
		String sql = "SELECT COUNT(*) AS COUNT "
				+ "FROM PROD";
		return jdbc.selectOne(sql);
	}
	
	public Map<String, Object> productView(String currentProdno) {
		String sql = "SELECT a.* , b.buyer_name"
				+ " FROM PROD a, buyer b"
				+ " WHERE a.BUYER_ID = b.buyer_id"
				+ " and a.PROD_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(currentProdno);
		return jdbc.selectOne(sql, param);
	}
	
	public int productBuy(String currentProdno, int input) {
		String sql = "INSERT INTO BUY VALUES((SELECT NVL(MAX(BUY_NO),0)+1 FROM BUY), ?, "
				+ "(TO_CHAR(SYSDATE,'YYYYMMDD')),(SELECT PROD_PRICE*0.6 FROM PROD WHERE PROD_ID = ?) ,"
				+ "?, NULL)";
		List<Object> param = new ArrayList<>();
		param.add(input);
		param.add(currentProdno);
		param.add(currentProdno);
		return jdbc.update(sql, param);
	}
	
	public int productStock(String currentProdno, int input) {
		String sql = "UPDATE PROD SET PROD_TOTAL_STOCK = PROD_TOTAL_STOCK + ? WHERE PROD_ID = ?";
		List<Object>  param = new ArrayList<>();
		param.add(input);
		param.add(currentProdno);
		return jdbc.update(sql, param);
	}
	
	public Map<String, Object> prodLackMax(int count) {
		String sql = "SELECT COUNT(*) AS COUNT  "
				+ "FROM PROD "
				+ "WHERE PROD_TOTAL_STOCK < ?";
		List<Object>  param = new ArrayList<>();
		param.add(count);
		return jdbc.selectOne(sql, param);
	}
	

	public List<Map<String, Object>> productLackList(int page, int count, int pageSize) {
		String sql = "SELECT * "
				+ " FROM (SELECT ROWNUM RN , A.* , b.buyer_name"
				+ " FROM (SELECT PROD_ID, PROD_NAME, PROD_TOTAL_STOCK, PROD_PRICE, PROD_WRITER, BUYER_ID "
				+ " FROM PROD "
				+ " WHERE PROD_TOTAL_STOCK < ?) A, buyer b"
				+ " where a.BUYER_ID = b.buyer_id) "
				+ " WHERE RN BETWEEN (?-1)*?+1 AND ?*? ";
		
		List<Object>  param = new ArrayList<>();
		param.add(count);
		param.add(page);
		param.add(pageSize);
		param.add(page);
		param.add(pageSize);
		return jdbc.selectList(sql, param);
	}
	
	//---------------------------------------------------------------------------
	
	public Map<String, Object> usedProdMax(){
		String sql = "select count(*) as count"
				+ " from usedprod";
		
		return jdbc.selectOne(sql);
	}
	
	public List<Map<String, Object>> usedproductList(int page, int pageSize){
		String sql = "SELECT *"
				+ " FROM"
				+ " (SELECT ROWNUM RN , A.* , b.BUYER_NAME, C.GRADE_NAME"
				+ " FROM "
				+ " (SELECT USEDPROD_ID, USEDPROD_NAME, USEDPROD_TOTAL_STOCK,"
				+ " USEDPROD_PRICE, USEDPROD_WRITER, BUYER_ID, GRADE_ID"
				+ " FROM USEDPROD) A, BUYER B, GRADE C"
				+ " WHERE A.BUYER_ID =  B.BUYER_ID"
				+ " AND A.GRADE_ID = C.GRADE_ID)"
				+ " WHERE RN BETWEEN (?-1)*?+1 AND ?*?";
		List<Object> param = new ArrayList<>();
		param.add(page);
		param.add(pageSize);
		param.add(page);
		param.add(pageSize);
		return jdbc.selectList(sql, param);
	}
	
	public Map<String, Object> usedproductView(String currentUProdno){
		String sql = "select a.*, b.grade_name, c.buyer_name"
				+ " from usedprod a, grade b, buyer c"
				+ " where a.grade_id = b.grade_id"
				+ " and a.buyer_id = c.buyer_id"
				+ " and a.usedprod_id = ?";
		
		List<Object> param = new ArrayList<>();
		param.add(currentUProdno);
		return jdbc.selectOne(sql, param);
	}
	public int usedproductBuy(String currentUProdno, int input) {
		String sql = "INSERT INTO BUY VALUES((SELECT NVL(MAX(BUY_NO),0)+1 FROM BUY), ?,"
				+ " (TO_CHAR(SYSDATE,'YYYYMMDD')),(SELECT USEDPROD_PRICE*0.8 FROM USEDPROD WHERE USEDPROD_ID = ?)"
				+ " ,'', ? )";
		List<Object> param = new ArrayList<>();
		param.add(input);
		param.add(currentUProdno);
		param.add(currentUProdno);
		return jdbc.update(sql, param);
	}
	
	public int usedproductStock(String currentUProdno, int input) {
		String sql = "UPDATE USEDPROD "
				+ " SET USEDPROD_TOTAL_STOCK = USEDPROD_TOTAL_STOCK + ?"
				+ " WHERE USEDPROD_ID = ?";
		List<Object>  param = new ArrayList<>();
		param.add(input);
		param.add(currentUProdno);
		return jdbc.update(sql, param);
	}
	
	public Map<String, Object> usedprodLackMax(int count) {
		String sql = "SELECT COUNT(*) AS COUNT  "
				+ " FROM USEDPROD "
				+ " WHERE USEDPROD_TOTAL_STOCK < ?";
		List<Object> param = new ArrayList<>();
		param.add(count);
		return jdbc.selectOne(sql, param);
	}
	
	public List<Map<String, Object>> usedproductLackList(int page, int count,
			int pageSize) {
		String sql = "SELECT * "
				+ " FROM "
				+ " (SELECT ROWNUM RN , A.*, B.GRADE_NAME, C.BUYER_NAME"
				+ " FROM "
				+ " (SELECT USEDPROD_ID, USEDPROD_NAME, USEDPROD_TOTAL_STOCK, "
				+ " USEDPROD_PRICE, USEDPROD_WRITER, GRADE_ID, BUYER_ID"
				+ " FROM USEDPROD "
				+ " WHERE USEDPROD_TOTAL_STOCK < ?) A, GRADE B, BUYER C"
				+ " WHERE A.GRADE_ID = B.GRADE_ID"
				+ " AND A.BUYER_ID = C.BUYER_ID) "
				+ " WHERE RN BETWEEN (?-1)*?+1 AND ?*? ";
		List<Object>  param = new ArrayList<>();
		param.add(count);
		param.add(page);
		param.add(pageSize);
		param.add(page);
		param.add(pageSize);
		return jdbc.selectList(sql, param);
	}
	
}//
