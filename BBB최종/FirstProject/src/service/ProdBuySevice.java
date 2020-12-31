package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.ProdBuyDao;

public class ProdBuySevice {
	private static ProdBuySevice instance;
	private ProdBuySevice(){}
	public static ProdBuySevice getInstance() {
		if(instance == null){
			instance = new ProdBuySevice();
		}
		return instance;
	}
	ProdBuyDao prodBuyDao = ProdBuyDao.getInstance();
	private int price;
	private int delNum;
	private Object cartNo;
	private Object prodId;
	private int choice;
	public int productAlllorder() {
		List<Map<String, Object>> list = prodBuyDao.allOrderList();
		List<Map<String, Object>> usedList = prodBuyDao.allUsedOrderList();
		if(list.size()==0 && usedList.size()==0){
			System.out.println("장바구니에 등록된 상품이 없습니다.");
			return View.BOOKMAIN;
		}
		System.out.println("~*~*~*~*~*~*~*~*~*~*~*~일반상품*~*~*~*~*~*~*~*~*~*~*~*");
		System.out.println("제목\t\t가격\t수량\t총가격");
		price = 0;
		if(list.size()>0){
			for(int i = 0; i < list.size(); i++){
				System.out.print(list.get(i).get("RN")+".");
				System.out.print(list.get(i).get("PROD_NAME") + "\t");
				System.out.print(list.get(i).get("PROD_PRICE") + "\t");
				System.out.print(list.get(i).get("BASKET_QTY") + "\t");
				System.out.println(list.get(i).get("PRICE"));
				price += Integer.parseInt(list.get(i).get("PRICE").toString());
			}
		}
		if(usedList.size()>0){
			System.out.println("~*~*~*~*~*~*~*~*~*~*~*~중고상품*~*~*~*~*~*~*~*~*~*~*~*");
			for(int i = 0; i < usedList.size(); i++){
				System.out.print(usedList.get(i).get("RN")+".");
				System.out.print(usedList.get(i).get("USEDPROD_NAME") + "\t");
				System.out.print(usedList.get(i).get("USEDPROD_PRICE") + "\t");
				System.out.print(usedList.get(i).get("USEDBASKET_QTY") + "\t");
				System.out.println(usedList.get(i).get("PRICE"));
				price += Integer.parseInt(usedList.get(i).get("PRICE").toString());
			}
		}
		System.out.println("*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*");
		System.out.println("총 구매 가격 : " + price);
		System.out.println("~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~");
		System.out.println("1.구매\t2.삭제\t3.수량변경\t0.취소");
		System.out.print("입력 : ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.BUYPRODUCT_BUY;
		case 2: 
			System.out.print("1.일반삭제\t2.중고삭제\t0.돌아가기\n입력 : ");
			choice = ScanUtil.nextInt();
			switch (choice) {
			case 1:
				if(list.size()==0){
					System.out.println("일반상품 중 등록된 상품이 없습니다.");
					return View.BUYPRODUCT_CARTALL;
				}
				System.out.print("몇번 상품을 삭제 하시겠습니까?\n입력 : ");
				delNum = ScanUtil.nextInt();
				if(list.size() < delNum){
					System.out.println("해당 상품번호가 없습니다.");
					return View.BUYPRODUCT_CARTALL;
				}
				prodId = list.get(delNum-1).get("PROD_ID");
				cartNo = list.get(delNum-1).get("CART_NO");
				return View.BUYPRODUCT_DELETE;
			case 2:
				if(usedList.size()==0){
					System.out.println("중고상품 중 등록된 상품이 없습니다.");
					return View.BUYPRODUCT_CARTALL;
				}
				System.out.print("몇번 상품을 삭제 하시겠습니까?\n입력 : ");
				delNum = ScanUtil.nextInt();
				if(usedList.size() < delNum){
					System.out.println("해당 상품번호가 없습니다.");
					return View.BUYPRODUCT_CARTALL;
				}
				prodId = usedList.get(delNum-1).get("USEDPROD_ID");
				cartNo = usedList.get(delNum-1).get("CART_NO");
				return View.BUYPRODUCT_DELETE;
			case 0: return View.BUYPRODUCT_CARTALL;
			}
		case 3:
			System.out.print("1.일반변경\t2.중고변경\t0.돌아가기\n입력 : ");
			choice = ScanUtil.nextInt();
			switch (choice) {
			case 1:
				if(list.size()==0){
					System.out.println("일반상품 중 등록된 상품이 없습니다.");
					return View.BUYPRODUCT_CARTALL;
				}
				System.out.print("몇번 상품을 변경 하시겠습니까?\n입력 : ");
				delNum = ScanUtil.nextInt();
				if(list.size() < delNum){
					System.out.println("해당 상품번호가 없습니다.");
					return View.BUYPRODUCT_CARTALL;
				}
				prodId = list.get(delNum-1).get("PROD_ID");
				cartNo = list.get(delNum-1).get("CART_NO");
				return View.BUYPRODUCT_CHANGE;
			case 2:
				if(usedList.size()==0){
					System.out.println("중고상품 중 등록된 상품이 없습니다.");
					return View.BUYPRODUCT_CARTALL;
				}
				System.out.print("몇번 상품을 변경 하시겠습니까?\n입력 : ");
				delNum = ScanUtil.nextInt();
				if(usedList.size() < delNum){
					System.out.println("해당 상품번호가 없습니다.");
					return View.BUYPRODUCT_CARTALL;
				}
				prodId = usedList.get(delNum-1).get("USEDPROD_ID");
				cartNo = usedList.get(delNum-1).get("CART_NO");
				return View.BUYPRODUCT_CHANGE;
			case 0: return View.BUYPRODUCT_CARTALL;
			}
			
		case 0: return View.BOOKMAIN;
		}
		return View.PRODUCT_MAIN;
	}
	
	public int productBuy() {
		int money = Integer.parseInt(Controller.loginMember.get("MEM_MONEY").toString());
		if(money < price){
			System.out.println("예치금이 부족합니다.");
			return View.PRODUCT_MAIN;
		}
		Map<String, Object> noCheck = prodBuyDao.noCheck();
		int cartNo = Integer.parseInt(noCheck.get("CART_NO").toString());
		int result = prodBuyDao.prodBuy();
		result = prodBuyDao.moneyUpdate(price);
		result = prodBuyDao.mileageUpdate(price/10);
		result = prodBuyDao.pay(cartNo);
		price = 0;
		if(result > 0){
			System.out.println("상품구매가 완료되었습니다.");
			Map<String, Object> member = prodBuyDao.selectMember();
			Controller.loginMember = member;
		}else{
			System.out.println("상품구매가 실패하였습니다.");
		}
		return View.PRODUCT_MAIN;
	}
	public int productDelete(){
		int result = 0;
		if(choice == 1){
			result = prodBuyDao.delete(prodId, cartNo);
		}
		if(choice == 2){
			result = prodBuyDao.usedDelete(prodId, cartNo);
		}
		if(result > 0){
			System.out.println("해당상품이 삭제되었습니다.");
		}else{
			System.out.println("해당상품 삭제에 실패했습니다.");
		}
		return View.BUYPRODUCT_CARTALL;
	}
	public int productChange() {
		int result;
		System.out.println("1.개수입력\t2.한개씩 수정\t0.되돌아가기");
		System.out.print("입력 : ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1:
			System.out.print("몇개의 상품으로 수정하시겠습니까? \n입력 : ");
			int count = ScanUtil.nextInt();
			if(choice == 1){
				result = prodBuyDao.countChange(count, prodId, cartNo);
			}
			if(choice == 2){
				result = prodBuyDao.usedCountChange(count, prodId, cartNo);
			}
			break;
		case 2:
			while(true){
				int check = 0;
				if(choice == 1){
					Map<String, Object> countCheck = prodBuyDao.countCheck(prodId, cartNo);
					check = Integer.parseInt(countCheck.get("BASKET_QTY").toString());
					System.out.println("현재 개수 : " + check);
				}
				if(choice == 2){
					Map<String, Object> countCheck = prodBuyDao.usedCountCheck(prodId, cartNo);
					check = Integer.parseInt(countCheck.get("USEDBASKET_QTY").toString());
					System.out.println("현재 개수 : " + check);
				}
				System.out.print("1. 1개 증가\t2. 1개 감소\t0.돌아가기\n입력 : ");
				input = ScanUtil.nextInt();
				switch (input) {
				case 1:
					if(choice == 1){
						result = prodBuyDao.countUp(prodId, cartNo);
					}
					if(choice == 2){
						result = prodBuyDao.usdeCountUp(prodId, cartNo);
					}
					break;
				case 2:
					if(check==0){
						System.out.println("수량이 0입니다. 장바구니에서 삭제합니다.");
						if(choice == 1){
							result  = prodBuyDao.delete(prodId, cartNo);
						}
						if(choice == 2){
							result  = prodBuyDao.usedDelete(prodId, cartNo);
						}
						return View.BUYPRODUCT_CARTALL;
					}else{
						if(choice == 1){
							result = prodBuyDao.countDown(prodId, cartNo);
						}
						if(choice == 2){
							result = prodBuyDao.usedCountDown(prodId, cartNo);
						}
					}
					break;
				case 0:
					return View.BUYPRODUCT_CARTALL;
				}
			}
		}
		return View.BUYPRODUCT_CARTALL;
	}
}
