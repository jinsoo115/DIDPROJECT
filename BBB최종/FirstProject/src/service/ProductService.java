package service;

import util.ScanUtil;
import util.View;

public class ProductService {
	private static ProductService instance;
	private ProductService(){}
	public static ProductService getInstance() {
		if(instance == null){
			instance = new ProductService();
		}
		return instance;
	}
	
	public int ProductMain() {
		System.out.println();
		System.out.println("-_-_-_-_-_-_-_-_BOOK-_-_-_-_-_-_-_-_");
		System.out.println("1.일반상품\t\t2.중고상품");
		System.out.println("3.장바구니\t\t0.홈화면");
		System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
		System.out.print("원하는 번호 입력 : ");
		int input = ScanUtil.nextInt();
		System.out.println();
		switch(input){
		case 1 : return View.NEWPRODUCT_MAIN;
		case 2 : return View.USEDPRODUCT_MAIN;
		case 3 : return View.BUYPRODUCT_CARTALL;
		case 0:  return View.BOOKMAIN;
		}
		return View.PRODUCT_MAIN;
		
	}
}
