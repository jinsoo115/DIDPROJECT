package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.UsedProductDao;

public class UsedProductService {
	private static UsedProductService instance;
	private UsedProductService(){}
	public static UsedProductService getInstance() {
		if(instance == null){
			instance = new UsedProductService();
		}
		return instance;
	}
	
	private UsedProductDao usedproductdao = UsedProductDao.getInstance();
	private String keyword = "";
	private Object prodid = "";
	private int detailNum;
	private int searchcount;
	private int decount;
	private int pcount; 
	//중고상품 메인화면
	public int UsedProductMain(){
		System.out.println();
		System.out.println("*_*_*_*_*_*_*_*_중고 도서*_*_*_*_*_*_*_*_");
		System.out.println("1.전체상품 보기\t2. 검색하기\t");
		System.out.println("3.분야별 보기\t0.BOOK화면");
		System.out.println("*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_");
		System.out.print("원하는 번호 입력 : ");
		int input = ScanUtil.nextInt();
		System.out.println();
		switch(input){
			case 1: return View.USEDPRODUCT_VIEW;
			case 2: return View.USEDPRODUCT_SEARCH;
			case 3: return View.USEDPRODUCT_LPROD;
			case 0: return View.PRODUCT_MAIN;
		}
		return View.USEDPRODUCT_MAIN;
	}
	
	//1.전체상품 보기 - 1page당 10개씩 나누어서 페이지를 선택하면 페이지에 해당하는 10개의 목록을 보여줌
	public int UsedProductView(){
		int page = 1;
		int pagesize = 10;
		boolean run = true;
		while(run){
			Map<String, Object> maxcount = usedproductdao.prodMax();
			int maxPage = Integer.parseInt(maxcount.get("COUNT").toString());
			maxPage = maxPage/pagesize +1;
			if(page <= 0){
				page = 1;
			}
			if(page > maxPage){
				page = maxPage;
			}
			List<Map<String,Object>> view = usedproductdao.viewusedProduct(page, pagesize);
			System.out.println("************************책 목록************************");
			for(int i = 0; i<view.size(); i++){
				Map<String, Object> post = view.get(i);
				System.out.println(post.get("RN")+". "+post.get("USEDPROD_NAME"));
				System.out.println(post.get("USEDPROD_WRITER")+" 저 | "+post.get("BUYER_NAME")
						+" | "+post.get("USEDPROD_PRICE")+"원"+" | "+post.get("GRADE_NAME"));
			}
			System.out.println("************현재 페이지"+page+"/"+maxPage+"**************");
			System.out.println("1.이전페이지\t2.다음페이지\t3.원하는 페이지 입력\t");
			System.out.println("4.목록의 개수 변경\t5.상세정보 보기\t0.나가기");
			System.out.print("원하는 번호 입력 : ");
			int input = ScanUtil.nextInt();
			switch(input){
				case 1: 
					page -= 1; break;
				case 2: 
					page += 1; break;
				case 3: 
					System.out.print("원하는 페이지를 입력 : ");
					page = ScanUtil.nextInt();
					break;
				case 4:
					System.out.print("원하는 목록의 개수 입력 : ");
					pagesize = ScanUtil.nextInt();
					break;
				case 5:
					System.out.print("상세정보 보고 싶은 번호를 입력하세요 : ");
					detailNum = ScanUtil.nextInt();
					decount = 1;
					return View.USEDPRODUCT_VIEW_DETAIL;
				case 0:
					run = false;
			}
		}
		return View.USEDPRODUCT_MAIN;
	}
	
	//2. 검색하기-메인
	public int UsedProductSearch(){
		System.out.println();
		System.out.println("~~~~~~~~~~~~ 책 검색~~~~~~~~~~~~");
		System.out.println("1.제목\t2.출판사\t3.저자\t0.돌아가기");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("원하는 방법을 입력 : ");
		int input = ScanUtil.nextInt();
		System.out.print("검색하고자 하는 단어를 입력하세요 : ");
		keyword = ScanUtil.nextLine();
		switch(input){
			case 1: searchcount = 1; 
			return View.USEDPRODUCT_SEARCH_MENU;
			case 2: searchcount = 2; 
			return View.USEDPRODUCT_SEARCH_MENU;
			case 3:searchcount = 3; 
			return View.USEDPRODUCT_SEARCH_MENU;
			case 0: return View.USEDPRODUCT_MAIN;
		}
		return View.USEDPRODUCT_SEARCH;
	}
	
	public int UsedProductSearchmenu(){
		List<Map<String, Object>> search = new ArrayList<>();
		if(searchcount == 1){
			search = usedproductdao.usedProductSearch_title(keyword);
		}else if(searchcount ==2){
			search = usedproductdao.usedProductSearch_publisher(keyword);
		}else if(searchcount ==3){
			search = usedproductdao.usedProductSearch_writer(keyword);
		}
		if(search.size()==0){
			System.out.println("검색된 내용이 없습니다.");
			return View.USEDPRODUCT_SEARCH;
		}
		
		System.out.println("*****************책 목록*****************");
		for(int i=0 ; i< search.size(); i++){
			Map<String, Object> post = search.get(i);
			System.out.println(i+1+". "+post.get("USEDPROD_NAME"));
			System.out.println(post.get("USEDPROD_WRITER")+" 저 | "+post.get("BUYER_NAME")
					+" | "+post.get("USEDPROD_PRICE")+"원"+" | "+post.get("GRADE_NAME"));
		}
		System.out.println("****************************************");
		System.out.print("번호 입력(1.상세정보 보기 0.나가기) : ");
		int input = ScanUtil.nextInt();
		switch(input){
			case 1:
				System.out.print("상세정보 보고 싶은 번호를 입력 : ");
				int number = ScanUtil.nextInt();
				prodid = search.get(number-1).get("USEDPROD_ID");
				decount = 2;
				return View.USEDPRODUCT_VIEW_DETAIL;
			case 0: break;
		}
		return View.USEDPRODUCT_SEARCH;
	}
	
	public int UsedProductlprod(){
		List<Map<String, Object>> list = usedproductdao.usedProductlprodcheck(); 
		for(int i = 0; i < list.size(); i++){
			Map<String, Object> post = list.get(i);
			System.out.print(i+1+"."+post.get("LPROD_NAME")+"\t");
		}
		System.out.println();

		System.out.print("보고싶은 분야의 번호를 선택 : ");
		int input = ScanUtil.nextInt();
		Object lprodid = list.get(input-1).get("LPROD_GU");
		
		List<Map<String, Object>> show = usedproductdao.usedProductlprodshow(lprodid);
		System.out.println("*****************책 목록*****************");
		for(int i=0 ; i< show.size(); i++){
			Map<String, Object> post = show.get(i);
			System.out.println(i+1+". "+post.get("USEDPROD_NAME"));
			System.out.println(post.get("USEDPROD_WRITER")+" 저 | "+post.get("BUYER_NAME")
					+" | "+post.get("USEDPROD_PRICE")+"원"+" | "+post.get("GRADE_NAME"));
		}
		System.out.println("****************************************");
		
		System.out.print("번호 입력(1.상세정보 보기 0.나가기) : ");
		int num = ScanUtil.nextInt();
		switch(num){
			case 1:
				System.out.print("상세정보 보고 싶은 번호를 입력 : ");
				int number = ScanUtil.nextInt();
				prodid = show.get(number-1).get("USEDPROD_ID");
				decount = 3;
				return View.USEDPRODUCT_VIEW_DETAIL;
			case 0: break;
		}
		return View.USEDPRODUCT_MAIN;
	}
	
	public int UsedProductDetail(){
		Map<String, Object> Detail = new HashMap<>();
		if(decount==1){
			Detail = usedproductdao.detailusedProduct(detailNum);
			prodid = Detail.get("USEDPROD_ID");
		}
		if(decount==2){
			Detail = usedproductdao.usedProductSearch_detail(prodid);
		}
		if(decount==3){
			Detail = usedproductdao.usedProductlprod_detail(prodid);
		}
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.println("책제목 : "+ Detail.get("USEDPROD_NAME"));
		System.out.println("저자 : "+ Detail.get("USEDPROD_WRITER")+" 저");
		System.out.println("출판사 : "+ Detail.get("BUYER_NAME"));
		System.out.println("가격 : "+ Detail.get("USEDPROD_PRICE")+"원");
		System.out.println("등급 : "+ Detail.get("GRADE_NAME"));
		System.out.println("쪽수 : "+ Detail.get("USEDPROD_PAGE")+"쪽");
		System.out.println("설명 : "+ Detail.get("USEDPROD_DETAIL"));
		System.out.println("----------------------------------------------------------------------------------------------");
		System.out.println("1.상품담기\t2.바로구매\t0.뒤로가기");
		System.out.print("입력 : ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			return View.USEDPRODUCT_CART;
		case 2:	
			return View.USEDPRODUCT_ORDER;
		case 0:
			return View.USEDPRODUCT_MAIN;
		}
		return View.USEDPRODUCT_MAIN;
	}
	
	public int UsedProductCart() {//상품담기
		System.out.print("몇개를 담으시겠습니까? ");
		pcount = ScanUtil.nextInt();
		int result;
		
		Map<String, Object> cartCheck = usedproductdao.prodCartCheck();
		if(cartCheck == null){
			result = usedproductdao.cartInsert();
		}
		Map<String, Object> basketCheck = usedproductdao.prodBasketCheck(prodid);
		
		if(basketCheck == null){
			result = usedproductdao.prodBasketInsert(prodid, pcount);
		}else{
			result = usedproductdao.prodBasketUpdate(prodid, pcount);
		}
			result = usedproductdao.prodCartUp(prodid);
		if(result > 0){
			System.out.println("상품이 담겼습니다.");
		}else{
			System.out.println("상품 담기를 실패했습니다.");
		}
		return View.USEDPRODUCT_MAIN;
	}
	public int UsedProductOrder() {
		System.out.print("몇개를 구매하시겠습니까? ");
		pcount = ScanUtil.nextInt();
		Map<String, Object> moneyCheck = usedproductdao.moneyCheck(pcount, prodid);
		int money = Integer.parseInt(Controller.loginMember.get("MEM_MONEY").toString());
		int checkMoney = Integer.parseInt(moneyCheck.get("USEDPROD_PRICE").toString());
		if(money<checkMoney){
			System.out.println("예치금이 부족합니다.");
			return View.USEDPRODUCT_MAIN;
		}
		int result;
		result = usedproductdao.orderCartInsert();
		result = usedproductdao.orderProdBasketInsert(prodid, pcount);
		result = usedproductdao.orderCartUpdate();
		result = usedproductdao.mileageUpdate(checkMoney/10);
		result = usedproductdao.moneyUpdate(checkMoney);
		result = usedproductdao.pay();
		if(result > 0){
			System.out.println("상품이 구매되었습니다.");
			Map<String, Object> member = usedproductdao.selectMember();
			Controller.loginMember = member;
		}else{
			System.out.println("상품 구매를 실패했습니다.");
		}
		return View.USEDPRODUCT_MAIN;
	}
}
