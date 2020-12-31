package service;

import controller.Controller;
import util.ScanUtil;
import util.View;

public class BookService {
	private static BookService instance;
	private BookService(){}
	public static BookService getInstance() {
		if(instance == null){
			instance = new BookService();
		}
		return instance;
	}
	
	public int BookMain() {
		System.out.println();
		System.out.println("=================BIBIDI BABIDI BOOK===============");
		System.out.println("==================BBB에 오신걸 환영합니다================");
		System.out.println("1.내정보\t\t2.주문화면\t\t3.상품화면");
		System.out.println("4.공지사항\t\t5.Q&A\t\t0.로그아웃");
		System.out.println("==================================================");
		System.out.print("원하는 번호를 선택 : ");
		int input = ScanUtil.nextInt();
		System.out.println();
		switch(input){
		case 1 : return View.MYPAGE;
		case 2 : return View.BUYPRODUCT_CARTALL;
		case 3 : return View.PRODUCT_MAIN;
		case 4 : return View.NOTICE;
		case 5 : return View.QNA;
		case 0:
			Controller.loginMember= null;
			return View.HOME;
		}
		
		return View.BOOKMAIN;
		
	}

}
