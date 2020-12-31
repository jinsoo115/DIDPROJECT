package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;


public class ManagerBuyerDao {
	private static ManagerBuyerDao instance;
	private ManagerBuyerDao(){}
	public static ManagerBuyerDao getInstance() {
		if(instance == null){
			instance = new ManagerBuyerDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	public List<Map<String, Object>> selectAdd(String doroAdd) {
		String sql = "SELECT ADD_POST, ADD_NAME "
				+ "FROM ADDER "
				+ "WHERE ADD_NAME LIKE '%' || ? || '%'";
		List<Object> param = new ArrayList<>();
		param.add(doroAdd);
		return jdbc.selectList(sql, param);
	}
	public int buyerInsert(Map<String, Object> param) {
		String sql = "INSERT INTO BUYER VALUES((select newbuyerid(max(buyer_id))from buyer), ?, ?,?,? ,?,?,0,?)";
		 List<Object> p = new ArrayList<>();
		 p.add(param.get("BUYER_NAME"));
		 p.add(param.get("BUYER_MANAGER"));
		 p.add(param.get("BUYER_ADD1"));
		 p.add(param.get("BUYER_TEL"));
		 p.add(param.get("BUYER_EMAIL"));
		 p.add(param.get("BUYER_ADD2"));
		 p.add(param.get("MEM_POST"));
		return jdbc.update(sql, p);
	}
	public Map<String, Object> buyerMax() {
		String sql = "SELECT COUNT(*) AS COUNT "
				+ "FROM BUYER";
		return jdbc.selectOne(sql);
	}
	public List<Map<String, Object>> buyerList(int page, int pageSize) {
		String sql = "SELECT * "
				+ "FROM (SELECT ROWNUM RN, A.* "
				+ "FROM (SELECT BUYER_ID, BUYER_NAME "
				+ "FROM BUYER) A) "
				+ "WHERE RN BETWEEN (?-1) * ?+1 AND ?*? "
				+ "ORDER BY BUYER_ID";
		List<Object> param = new ArrayList<>();
		param.add(page);
		param.add(pageSize);
		param.add(page);
		param.add(pageSize);
		return jdbc.selectList(sql, param);
	}
	public Map<String, Object> buyerView(Object buyerId) {
		String sql = "SELECT * FROM BUYER WHERE BUYER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(buyerId);
		return jdbc.selectOne(sql, param);
	}
	public int buyerDelete(String buyerId) {
		String sql = "UPDATE BUYER SET BUYER_DELETE = 1 WHERE BUYER_ID = ?";
		List<Object> param = new ArrayList<>();
		param.add(buyerId);
		return jdbc.update(sql, param);
	}
	public int buyerUpdate(Map<String, Object> param, String buyerId) {
		String sql = "UPDATE BUYER SET BUYER_NAME = ?, BUYER_MANAGER=?, BUYER_ADD1=?, BUYER_TEL=?, BUYER_EMAIL=?, BUYER_ADD2=?, BUYER_POST=? WHERE BUYER_ID = ?";
		List<Object> p = new ArrayList<>();
		p.add(param.get("BUYER_NAME"));
		 p.add(param.get("BUYER_MANAGER"));
		 p.add(param.get("BUYER_ADD1"));
		 p.add(param.get("BUYER_TEL"));
		 p.add(param.get("BUYER_EMAIL"));
		 p.add(param.get("BUYER_ADD2"));
		 p.add(param.get("MEM_POST"));
		 p.add(buyerId);
		return jdbc.update(sql, p);
		
	}
}
