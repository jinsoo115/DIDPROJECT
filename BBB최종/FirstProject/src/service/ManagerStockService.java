package service;

import java.util.List;
import java.util.Map;
import util.ScanUtil;
import util.View;
import dao.ManagerStockDao;

public class ManagerStockService {
	private static ManagerStockService instance;
	private ManagerStockService(){}
	public static ManagerStockService getInstance() {
		if(instance == null){
			instance = new ManagerStockService();
		}
		return instance;
	}

	ManagerStockDao stockDao = ManagerStockDao.getInstance();
	private String currentProdno;
	private String currentUProdno;
	public int managerProductMain(){
		System.out.println();
		System.out.println("============BIBIDI BABIDI BOOK==========");
		System.out.println("=================관리자모드================");
		System.out.println("=================상품관리================");
		System.out.println("1.일반상품\t\t2.중고상품");
		System.out.println("3.일반상품관리\t4.중고상품관리");
		System.out.println("0.되돌아가기");
		System.out.println("========================================");
		System.out.print("원하는 번호를 선택 : ");
		int input = ScanUtil.nextInt();
		System.out.println();
		switch(input){
		case 1: return View.MANAGERSTOCK_NEW;
		case 2: return View.MANAGERSTOCK_USED;
		case 3: return View.MANAGERPRODUCT_MANAGEMENT_MAIN;
		case 4: return View.MANAGERPRODUCT_MANAGEMENT_MAIN_USED;
		case 0: return View.MANAGER_MAIN;
		}
		return View.MANAGERPRODUCT_MAIN;

	}

	public int newProduct(){
		System.out.println("----------------------관리자 일반상품 목록----------------------");
		int page = 1;
		int pageSize = 10;
		while(true){
			Map<String, Object> maxcount = stockDao.prodMax();
			int maxPage = Integer.parseInt(maxcount.get("COUNT").toString());
			maxPage = maxPage/pageSize + 1;
			if(page<=0){
				page = 1;
			}
			if(page>maxPage){
				page = maxPage;
			}
			List<Map<String, Object>> list = stockDao.productList(page, pageSize);
			System.out.println();
			System.out.println("======================현재 일반상품 목록======================");
			for(int i = 0; i < list.size(); i++){
				System.out.print("상품번호 : "+list.get(i).get("PROD_ID")+" | ");
				System.out.println("제목 : " +list.get(i).get("PROD_NAME"));
				System.out.print("현재재고 : "+list.get(i).get("PROD_TOTAL_STOCK")+"\t");
				System.out.println("판매가격 : "+list.get(i).get("PROD_PRICE")+"\t");
				System.out.println("저자 : "+list.get(i).get("PROD_WRITER")
						+"\t 출판사 : "+ list.get(i).get("BUYER_NAME"));
				System.out.println("----------------------------------------------------");
			}
			System.out.println("=================현재 페이지"+page+"/"+maxPage+"=====================");
			System.out.println("1.이전페이지\t2.다음페이지\t3.원하는 페이지 입력");
			System.out.println("4.페이지당 목록 개수\t5.상품확인\t\t6.부족한 재고 확인\t0.나가기");
			System.out.print("입력 : ");
			int input = ScanUtil.nextInt();
			switch (input) {
				case 1:
					page--;
					break;
				case 2:
					page++;
					break;
				case 3:
					System.out.print("원하는 페이지 입력 : ");
					page = ScanUtil.nextInt();
					break;
				case 4:
					System.out.print("페이지당 목록 개수 : ");
					pageSize = ScanUtil.nextInt();
					break;
				case 5:
					System.out.print("상품번호입력 : ");
					currentProdno = ScanUtil.nextLine();
					return View.MANAGERSTOCK_NEWVIEW;
				case 6:
					return View.MANAGERSTOCK_NEWSLACKTCOK;
				case 0:
					return View.MANAGERPRODUCT_MAIN;
			}
		}
	}

	//일반상품 - 재고추가 
	public int newProductPlus() {
		Map<String, Object> map = stockDao.productView(currentProdno);
		System.out.println("---------------------------------------------------");
		System.out.print("상품번호 : "+map.get("PROD_ID")+" | ");
		System.out.println("제목 : " +map.get("PROD_NAME"));
		System.out.print("현재재고 : "+map.get("PROD_TOTAL_STOCK")+"\t");
		System.out.println("판매가격 : "+map.get("PROD_PRICE")+"\t");
		System.out.println("저자 : "+map.get("PROD_WRITER")
				+"\t 출판사 : "+ map.get("BUYER_NAME"));
		System.out.println("---------------------------------------------------");
		System.out.print("재고 추가 수량 입력(0입력시 되돌아가기) : ");
		int input= ScanUtil.nextInt();
		if(input==0){
			return View.MANAGERSTOCK_NEW;
		}else{
			int result = stockDao.productBuy(currentProdno, input);
			result = stockDao.productStock(currentProdno, input);
			if(result>0){
				System.out.println("재고 주문 완료");
			}else{
				System.out.println("재고 주문 실패");
			}
		}
		return View.MANAGERSTOCK_NEW;
	}

	//일반상품 - 부족한재고확인
	public int newProductLack() {
		System.out.print("몇개 미만의 재고를 검색 하시겠습니까 : ");
		int count = ScanUtil.nextInt();
		int page = 1;
		int pageSize = 10;
		while(true){
			Map<String, Object> maxcount = stockDao.prodLackMax(count);
			int maxPage = Integer.parseInt(maxcount.get("COUNT").toString());
			maxPage = maxPage/pageSize + 1;
			if(page<=0){
				page = 1;
			}
			if(page>maxPage){
				page = maxPage;
			}
			List<Map<String, Object>> list = stockDao.productLackList(page, count, pageSize);
			System.out.println("======================부족한 일반상품 목록======================");
			for(int i = 0; i < list.size(); i++){
				System.out.print("상품번호 : "+list.get(i).get("PROD_ID")+" | ");
				System.out.println("제목 : " +list.get(i).get("PROD_NAME"));
				System.out.print("현재재고 : "+list.get(i).get("PROD_TOTAL_STOCK")+"\t");
				System.out.println("판매가격 : "+list.get(i).get("PROD_PRICE")+"\t");
				System.out.println("저자 : "+list.get(i).get("PROD_WRITER")
						+"\t출판사 : "+list.get(i).get("BUYER_NAME"));
				System.out.println("----------------------------------------------------");
			}
			System.out.println("=================현재 페이지"+page+"/"+maxPage+"=====================");
			System.out.println("1.이전페이지\t2.다음페이지\t3.원하는 페이지 입력");
			System.out.println("4.페이지당 목록 개수\t5.상품확인\t\t0.나가기");
			System.out.print("입력 : ");
			int input = ScanUtil.nextInt();
			switch (input) {
				case 1:
					page--;
					break;
				case 2:
					page++;
					break;
				case 3:
					System.out.print("원하는 페이지 입력 : ");
					page = ScanUtil.nextInt();
					break;
				case 4:
					System.out.print("페이지당 목록 개수 : ");
					pageSize = ScanUtil.nextInt();
					break;
				case 5:
					System.out.print("상품번호입력 : ");
					currentProdno = ScanUtil.nextLine();
					return View.MANAGERSTOCK_NEWVIEW;
				case 0:
					return View.MANAGERPRODUCT_MAIN;
			}
		}
	}

	public int usedProduct(){
		System.out.println("----------------------관리자 중고상품 목록----------------------");
		int page = 1;
		int pageSize = 10;
		while(true){
			Map<String, Object> maxcount = stockDao.usedProdMax();
			int maxPage = Integer.parseInt(maxcount.get("COUNT").toString());
			maxPage = maxPage/pageSize + 1;
			if(page <= 0){
				page = 1;
			}
			if(page > maxPage){
				page = maxPage;
			}
			List<Map<String, Object>> list = stockDao.usedproductList(page, pageSize);
			System.out.println();
			System.out.println("======================현재 중고상품 목록======================");
			for(int i = 0; i < list.size(); i++){
				System.out.print("상품번호 : "+list.get(i).get("USEDPROD_ID")+" | ");
				System.out.println("제목 : " +list.get(i).get("USEDPROD_NAME"));
				System.out.print("현재재고 : "+list.get(i).get("USEDPROD_TOTAL_STOCK")+"\t");
				System.out.print("판매가격 : "+list.get(i).get("USEDPROD_PRICE")+"\t");
				System.out.println("저자 : "+list.get(i).get("USEDPROD_WRITER"));
				System.out.println("출판사 : "+list.get(i).get("BUYER_NAME")
						+ "\t등급 : "+list.get(i).get("GRADE_NAME"));
				System.out.println("----------------------------------------------------");
			}
			System.out.println("=================현재 페이지"+page+"/"+maxPage+"=====================");
			System.out.println("1.이전페이지\t2.다음페이지\t3.원하는 페이지 입력");
			System.out.println("4.페이지당 목록 개수\t5.상품확인\t\t6.부족한 재고 확인\t0.나가기");
			System.out.print("입력 : ");
			int input = ScanUtil.nextInt();
			switch (input) {
				case 1:
					page--;
					break;
				case 2:
					page++;
					break;
				case 3:
					System.out.print("원하는 페이지 입력 : ");
					page = ScanUtil.nextInt();
					break;
				case 4:
					System.out.print("페이지당 목록 개수 : ");
					pageSize = ScanUtil.nextInt();
					break;
				case 5:
					System.out.print("상품번호입력 : ");
					currentUProdno = ScanUtil.nextLine();
					return View.MANAGERSTOCK_USEDVIEW;
				case 6:
					return View.MANAGERSTOCK_USEDSLACKTCOK;
				case 0:
					return View.MANAGERPRODUCT_MAIN;
			}
		}
	}

	public int usedProductPlus(){
		Map<String, Object> map = stockDao.usedproductView(currentUProdno);
		System.out.println("---------------------------------------------------");
		System.out.print("상품번호 : "+ map.get("USEDPROD_ID")+ " | ");
		System.out.println("제목 : " +map.get("USEDPROD_NAME"));
		System.out.print("현재재고 : "+map.get("USEDPROD_TOTAL_STOCK")+"\t");
		System.out.print("판매가격 : "+map.get("USEDPROD_PRICE")+"\t");
		System.out.println("저자 : "+map.get("USEDPROD_WRITER"));
		System.out.println("출판사 : "+map.get("BUYER_NAME")+"\t등급 : "+map.get("GRADE_NAME"));
		System.out.println("---------------------------------------------------");
		System.out.print("재고 추가 수량 입력(0입력시 되돌아가기) : ");
		int input= ScanUtil.nextInt();
		if(input==0){
			return View.MANAGERSTOCK_USED;
		}else{
			int result = stockDao.usedproductBuy(currentUProdno, input);

			result = stockDao.usedproductStock(currentUProdno, input);

			if(result > 0){
				System.out.println("중고재고 주문 완료");
			}else{
				System.out.println("중고재고 주문 실패");
			}
		}
		return View.MANAGERSTOCK_USED;
	}

	public int usedProductLack() {
		System.out.print("몇개 미만의 재고를 검색 하시겠습니까 : ");
		int count = ScanUtil.nextInt();
		int page = 1;
		int pageSize = 10;
		while(true){
			Map<String, Object> maxcount = stockDao.usedprodLackMax(count);
			int maxPage = Integer.parseInt(maxcount.get("COUNT").toString());
			maxPage = maxPage/pageSize + 1;
			if(page<=0){
				page = 1;
			}
			if(page>maxPage){
				page = maxPage;
			}
			List<Map<String, Object>> list = stockDao.usedproductLackList(page, count, pageSize);
			System.out.println();
			System.out.println("======================부족한 중고상품 목록======================");
				for(int i = 0; i < list.size(); i++){
					System.out.print("상품번호 : "+list.get(i).get("USEDPROD_ID")+" | ");
					System.out.println("제목 : " +list.get(i).get("USEDPROD_NAME"));
					System.out.print("현재재고 : "+list.get(i).get("USEDPROD_TOTAL_STOCK")+"\t");
					System.out.print("판매가격 : "+list.get(i).get("USEDPROD_PRICE")+"\t");
					System.out.println("저자 : "+list.get(i).get("USEDPROD_WRITER"));
					System.out.println("출판사 : "+list.get(i).get("BUYER_NAME")
							+ "\t등급 : "+list.get(i).get("GRADE_NAME"));
				System.out.println("----------------------------------------------------");
			}
			System.out.println("=================현재 페이지"+page+"/"+maxPage+"=====================");
			System.out.println("1.이전페이지\t2.다음페이지\t3.원하는 페이지 입력");
			System.out.println("4.페이지당 목록 개수\t5.상품확인\t\t0.나가기");
			System.out.print("입력 : ");
			int input = ScanUtil.nextInt();
			switch (input) {
				case 1:
					page--;
					break;
				case 2:
					page++;
					break;
				case 3:
					System.out.print("원하는 페이지 입력 : ");
					page = ScanUtil.nextInt();
					break;
				case 4:
					System.out.print("페이지당 목록 개수 : ");
					pageSize = ScanUtil.nextInt();
					break;
				case 5:
					System.out.print("상품번호입력 : ");
					currentUProdno = ScanUtil.nextLine();
					return View.MANAGERSTOCK_USEDVIEW;
				case 0:
					return View.MANAGERPRODUCT_MAIN;
			}
		}
	}
	


}//
