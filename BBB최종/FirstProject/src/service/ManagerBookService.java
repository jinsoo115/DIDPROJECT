package service;


import controller.Controller;
import util.ScanUtil;
import util.View;

public class ManagerBookService {
	private static ManagerBookService instance;
	private ManagerBookService(){}
	public static ManagerBookService getInstance() {
		if(instance == null){
			instance = new ManagerBookService();
		}
		return instance;
	}
	
	public int BookMain() {
		System.out.println();
		System.out.println("===================관리자화면===================");
		System.out.println("1.상품\t\t2.공지사항\t\t3.QnA");
		System.out.println("4.거래처관리\t5.장르관리\t\t6.중고등급관리");
		System.out.println("0.로그아웃");
		System.out.println("=============================================");
		System.out.print("원하는 번호를 선택 : ");
		int input = ScanUtil.nextInt();
		System.out.println();
		switch(input){
		case 1: return View.MANAGERPRODUCT_MAIN;
		case 2: return View.NOTICE;
		case 3: return View.QNA;
		case 4: return View.MANAGER_BUYER_MAIN;
		case 5: return View.MANAGER_LPROD_LIST;
		case 6: return View.MANAGER_GRADE_LIST;
		case 0: Controller.loginMember = null; return View.HOME;
		}
		return View.MANAGER_MAIN;
	}
	
}
