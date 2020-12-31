package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import dao.ManagerProdcutDao;

public class ManagerProductService {
	private static ManagerProductService instance;
	private ManagerProductService(){}
	public static ManagerProductService getInstance() {
		if(instance == null){
			instance = new ManagerProductService();
		}
		return instance;
	}

	ManagerProdcutDao managerproductdao = ManagerProdcutDao.getInstance();

	public int ManagerNewProductMain(){
		System.out.println("-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-");
		System.out.println("1.등록\t2.수정\t3.삭제\t0.돌아가기");
		System.out.println("-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-");
		System.out.print("번호 입력 : ");
		int input = ScanUtil.nextInt();
		switch(input){
		case 1: return View.MANAGERPRODUCT_NEW_INSERT;
		case 2: return View.MANAGERPRODUCT_NEW_UPDATE;
		case 3: return View.MANAGERPRODUCT_NEW_DELETE;
		case 0: return View.MANAGERPRODUCT_MAIN;
		}
		return View.MANAGERPRODUCT_MAIN;
	}

	private int number;
	
	public int ManagerNewProductInsert(){
		System.out.println("***********일반상품 등록***********");
		System.out.print("책 이름 입력 : ");
		String bookName = ScanUtil.nextLine();
		System.out.println("***********일반상품 목록***********");
		List<Map<String, Object>> list = managerproductdao.newproductname(bookName);
		for(int i =0; i<list.size();i++){
			System.out.println("상품ID : "+list.get(i).get("PROD_ID"));
			System.out.println("제목 : "+list.get(i).get("PROD_NAME"));
			System.out.println("저자 : "+list.get(i).get("PROD_WRITER")
					+"\t 출판사 : " +list.get(i).get("BUYER_NAME"));
			System.out.println("*********************************");
		}
		System.out.print("상품명 : ");
		String name = ScanUtil.nextLine();
		System.out.print("상품 가격 : ");
		String price = ScanUtil.nextLine();
		System.out.print("저자 : ");
		String writer = ScanUtil.nextLine();
		System.out.print("책 쪽수 : ");
		String page =  ScanUtil.nextLine();
		String buyer = "";
		while(true){
			System.out.print("거래처명 : ");
			buyer = ScanUtil.nextLine();
			List<Map<String, Object>> buyerList = managerproductdao.selectBuyer(buyer);
			if(buyerList == null){
				System.out.println("검색하신 거래처명이 없습니다.");
				continue;
			}
			System.out.println("**********"+buyer+"로 검색한 결과********");
			System.out.println("번호. 거래처코드\t거래처명");
			for(int i = 0; i < buyerList.size(); i++){
				System.out.print(i+1+". ");
				System.out.println(buyerList.get(i).get("BUYER_NAME"));
			}
			System.out.println("*********************************");
			System.out.print("번호를 선택하세요 : ");
			number = ScanUtil.nextInt();
			buyer = (String)buyerList.get(number-1).get("BUYER_ID");
			break;
		}
		String lprod = "";
		while(true){
			System.out.print("장르 : ");
			lprod = ScanUtil.nextLine();
			List<Map<String, Object>> lprodList = managerproductdao.selectLprod(lprod);
			if(lprodList == null){
				System.out.println("검색하신 거래처명이 없습니다.");
				continue;
			}
			System.out.println("**********"+lprod+"로 검색한 결과********");
			System.out.println("번호. 거래처코드\t거래처명");
			for(int i = 0; i < lprodList.size(); i++){
				System.out.print(i+1+". ");
				System.out.println(lprodList.get(i).get("LPROD_NAME"));
			}
			System.out.println("*********************************");
			System.out.print("번호를 선택하세요 : ");
			number = ScanUtil.nextInt();
			lprod = (String)lprodList.get(number-1).get("LPROD_GU");
			break;
		}
		
		Map<String,Object> number = managerproductdao.getnum(lprod);
		int no = Integer.parseInt(number.get("NUM").toString());
		String num = "";
		if(no>=0 &&no<10){
			num = "000"+number.get("NUM").toString();
		}
		else if(no>=10 &&no<100){
			num = "00"+number.get("NUM").toString();
		}
		else if(no>=100 &&no<1000){
			num = "0"+number.get("NUM").toString();
		}
		else if(no>=1000 &&no<10000){
			num = number.get("NUM").toString();
		}
		
		String id = "P"+lprod+num;
		List<Object> param = new ArrayList<>();
		param.add(id);
		param.add(name);
		param.add(price);
		param.add(writer);
		param.add(page);
		param.add(buyer);
		param.add(lprod);
		int result = managerproductdao.newProdInsert(param);
			if(result > 0){
			System.out.println("등록되었습니다.");
		}else{
			System.out.println("실패하였습니다.");
		}
		return View.MANAGERPRODUCT_MANAGEMENT_MAIN;
	}

	public int ManagerNewProductUpdate() {
		System.out.println("***********일반상품 수정***********");
		System.out.print("책 이름 입력 : ");
		String bookName = ScanUtil.nextLine();
		List<Map<String, Object>> list = managerproductdao.newproductname(bookName);
		for(int i =0; i<list.size();i++){
			System.out.println("상품ID : "+list.get(i).get("PROD_ID"));
			System.out.println("제목 : "+list.get(i).get("PROD_NAME"));
			System.out.println("저자 : "+list.get(i).get("PROD_WRITER")
					+"\t 출판사 : " +list.get(i).get("BUYER_NAME"));
			System.out.println("*********************************");
		}
		System.out.println("상품ID를 입력 : ");
		String prodId = ScanUtil.nextLine();
		Map<String, Object> map = managerproductdao.newprodSelect(prodId);
		System.out.println("********************************");
		System.out.println("가격 : " +map.get("PROD_PRICE"));
		System.out.println("********************************");
		System.out.println("수정 할 금액을 입력 : ");
		int price = ScanUtil.nextInt();
		int result = managerproductdao.update(price, prodId);
		if(result > 0){
			System.out.println("수정되었습니다.");
		}else{
			System.out.println("실패하였습니다.");
		}
		return View.MANAGERPRODUCT_MANAGEMENT_MAIN;
	}
	
	public int ManagerNewProductDelete() {
		System.out.println("***********일반상품 삭제***********");
		System.out.print("책 이름 입력 : ");
		String bookName = ScanUtil.nextLine();
		List<Map<String, Object>> list = managerproductdao.newproductname(bookName);
		for(int i =0; i<list.size();i++){
			System.out.println("상품ID : "+list.get(i).get("PROD_ID"));
			System.out.println("제목 : "+list.get(i).get("PROD_NAME"));
			System.out.println("저자 : "+list.get(i).get("PROD_WRITER")
					+"\t 출판사 : " +list.get(i).get("BUYER_NAME"));
			System.out.println("*********************************");
		}
		System.out.println("상품ID를 입력 : ");
		String prodId = ScanUtil.nextLine();
		int result = managerproductdao.delete(prodId);
		if(result > 0){
			System.out.println("삭제되었습니다.");
		}else{
			System.out.println("실패하였습니다.");
		}
		return View.MANAGERPRODUCT_MANAGEMENT_MAIN;
	}

	public int ManagerUsedProductMain(){
		System.out.println("@_@_@_@_@_@_@_@_@_@_@_@_@_@_@_@_@_@_@");
		System.out.println("1.등록\t2.수정\t3.삭제\t0.돌아가기");
		System.out.println("@_@_@_@_@_@_@_@_@_@_@_@_@_@_@_@_@_@_@");
		System.out.print("번호 입력 : ");
		int input = ScanUtil.nextInt();
		switch(input){
		case 1: return View.MANAGERPRODUCT_USED_INSERT;
		case 2: return View.MANAGERPRODUCT_USED_UPDATE;
		case 3: return View.MANAGERPRODUCT_USED_DELETE;
		case 0: return View.MANAGERPRODUCT_MAIN;
		}
		return View.MANAGERPRODUCT_MAIN;
	}


	public int ManagerUsedProductInsert() {
		System.out.println("***********중고상품 등록***********");
		System.out.print("책 이름 입력 : ");
		String bookName = ScanUtil.nextLine();
		List<Map<String, Object>> list = managerproductdao.newproductname(bookName);
		for(int i =0; i<list.size();i++){
			System.out.println("상품ID : "+list.get(i).get("PROD_ID"));
			System.out.println("제목 : "+list.get(i).get("PROD_NAME"));
			System.out.println("저자 : "+list.get(i).get("PROD_WRITER")
					+"\t 출판사 : " +list.get(i).get("BUYER_NAME"));
			System.out.println("*********************************");
		}
		System.out.print("상품코드를 입력하세요 : ");
		String prodId = ScanUtil.nextLine();

		List<Map<String, Object>> listGrade = managerproductdao.grade();
		for(int i = 0; i < listGrade.size(); i++){
			System.out.println((i+1)+". "+listGrade.get(i).get("GRADE_NAME"));			}
		System.out.print("등급을 선택해주세요 : ");
		int input = ScanUtil.nextInt();
		double persent = 0;
		if(input ==1){
			persent = 0.5;
		}else if(input == 2){
			persent = 0.4;
		}else{
			persent = 0.3;
		}
		String grade = (String)listGrade.get(input-1).get("GRADE_ID");
		Map<String, Object> listAll = managerproductdao.newproductnameAll(prodId);
		int price =  Integer.parseInt(listAll.get("PROD_PRICE").toString());
		price = (int)(price * persent);
		String finalprodid = grade;
		String checkProdId = listAll.get("PROD_ID").toString().substring(1);
		finalprodid = finalprodid + checkProdId;
		grade = grade + "01";
		int result = managerproductdao.usedProdInsert(finalprodid, price, grade, listAll);
		if(result > 0){
			System.out.println("등록되었습니다.");
		}else{
			System.out.println("실패하였습니다.");
		}
		return View.MANAGERPRODUCT_MANAGEMENT_MAIN;
	}
	
	public int ManagerUsedProductUpdate() {
		System.out.println("***********중고상품 수정***********");
		System.out.print("책 이름 입력 : ");
		String bookName = ScanUtil.nextLine();
		List<Map<String, Object>> list = managerproductdao.usedproductname(bookName);
		for(int i =0; i<list.size();i++){
			System.out.println("상품ID : "+list.get(i).get("PROD_ID"));
			System.out.println("제목 : "+list.get(i).get("PROD_NAME"));
			System.out.println("저자 : "+list.get(i).get("PROD_WRITER")
					+"\t 출판사 : " +list.get(i).get("BUYER_NAME"));
			System.out.println("*********************************");
		}
		System.out.println("상품ID를 입력 : ");
		String prodId = ScanUtil.nextLine();
		Map<String, Object> map = managerproductdao.usedprodSelect(prodId);
		System.out.println("********************************");
		System.out.println("가격 : " +map.get("PROD_PRICE"));
		System.out.println("********************************");
		System.out.println("수정 할 금액을 입력 : ");
		int price = ScanUtil.nextInt();
		int result = managerproductdao.usedupdate(price, prodId);
		if(result > 0){
			System.out.println("수정되었습니다.");
		}else{
			System.out.println("실패하였습니다.");
		}
		return View.MANAGERPRODUCT_MANAGEMENT_MAIN;
	}
	
	public int ManagerUsedProductDelete() {
		System.out.println("***********중고상품 삭제***********");
		System.out.print("책 이름 입력 : ");
		String bookName = ScanUtil.nextLine();
		List<Map<String, Object>> list = managerproductdao.usedproductname(bookName);
		for(int i =0; i<list.size();i++){
			System.out.println("상품ID : "+list.get(i).get("PROD_ID"));
			System.out.println("제목 : "+list.get(i).get("PROD_NAME"));
			System.out.println("저자 : "+list.get(i).get("PROD_WRITER")
					+"\t 출판사 : " +list.get(i).get("BUYER_NAME"));
			System.out.println("*********************************");
		}
		System.out.println("상품ID를 입력 : ");
		String prodId = ScanUtil.nextLine();
		int result = managerproductdao.useddelete(prodId);
		if(result > 0){
			System.out.println("삭제되었습니다.");
		}else{
			System.out.println("실패하였습니다.");
		}
		return View.MANAGERPRODUCT_MANAGEMENT_MAIN;
	} 
}//
