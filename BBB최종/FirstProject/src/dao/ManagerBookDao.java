package dao;


public class ManagerBookDao {
	private static ManagerBookDao instance;
	private ManagerBookDao(){}
	public static ManagerBookDao getInstance() {
		if(instance == null){
			instance = new ManagerBookDao();
		}
		return instance;
	}
}
