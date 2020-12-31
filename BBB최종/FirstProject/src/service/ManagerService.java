package service;

import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.ManagerDao;

public class ManagerService {
	private static ManagerService instance;
	private ManagerService(){}
	public static ManagerService getInstance() {
		if(instance == null){
			instance = new ManagerService();
		}
		return instance;
	}
	private ManagerDao managerDao = ManagerDao.getInstance();
	public int login() {
		System.out.println("=========관리자 로그인=========");
		System.out.print("아이디 : ");
		String id = ScanUtil.nextLine();
		System.out.print("비밀번호 : ");
		String password = ScanUtil.nextLine();

		Map<String, Object> manager = managerDao.selectManager(id,password);
		if(manager == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
			Controller.loginMember = manager;
			return View.MANAGER_MAIN;
		}
		return 0;
	}
}
