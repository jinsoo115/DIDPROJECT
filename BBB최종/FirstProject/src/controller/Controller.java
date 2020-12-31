package controller;

import java.util.Map;

import service.BookService;
import service.ManagerBookService;
import service.ManagerBuyerService;
import service.ManagerGradeService;
import service.ManagerLprodService;
import service.ManagerProductService;
import service.ManagerService;
import service.ManagerStockService;
import service.MemberService;
import service.MypageService;
import service.NewProductService;
import service.NoticeService;
import service.ProdBuySevice;
import service.ProductService;
import service.QnaService;
import service.UsedProductService;
import util.ScanUtil;
import util.View;

public class Controller {
	public static void main(String[] args) {
		/*
		 * 발표순서 : 조 소개 > 주제 소개 > 주제 선정 배경 > 메뉴 구조 > 시연
		 * 발표인원 : 발표자 1명, ppt 및 시연 도우미 1명
		 * 
		 * Controller : 화면 이동
		 * Service : 화면 기능
		 * Dao : 쿼리작성
		 */

		new Controller().start();
	}

	public static Map<String, Object> loginMember;

	MemberService memberService = MemberService.getInstance();
	BookService bookService = BookService.getInstance();
	ManagerService managerService = ManagerService.getInstance();
	ManagerBookService managerBookService = ManagerBookService.getInstance();
	MypageService mypageService = MypageService.getInstance();
	ManagerStockService managerStockService = ManagerStockService.getInstance();
	NoticeService noticeService = NoticeService.getInstance();
	ProductService productService = ProductService.getInstance();
	NewProductService newProductService = NewProductService.getInstance();
	UsedProductService usedProductService = UsedProductService.getInstance();
	ProdBuySevice prodBuyService = ProdBuySevice.getInstance();
	ManagerBuyerService managerBuyerService = ManagerBuyerService.getInstance();
	ManagerLprodService managerLprodService = ManagerLprodService.getInstance();
	ManagerGradeService managerGradeService = ManagerGradeService.getInstance();
	ManagerProductService managerProductService = ManagerProductService.getInstance();
	QnaService qnaService = QnaService.getInstance();
	private void start() {
		int view = View.HOME;

		while (true) {
			switch (view) {
			case View.HOME: view = home(); break;//로그인,회원가입 하는 곳
			case View.LOGIN: view = memberService.login(); break;//로그인
			case View.JOIN: view = memberService.join(); break;//회원가입
			case View.BOOKMAIN: view = bookService.BookMain(); break;//BOOk PROJECT의 메인화면
			//마이페이지
			case View.MYPAGE: view = mypageService.MypageMain(); break;//마이페이지 메인
			case View.MYPAGE_INFORM_UPDATE: view = mypageService.informupdate(); break;//마이페이지 정보 업테이트 메인
			case View.MYPAGE_PHONE_UPDATE: view = mypageService.phoneupdate(); break;//마이페이지 정보 - 폰번호 변경
			case View.MYPAGE_EMAIL_UPDATE: view = mypageService.emailupdate(); break;//마이페이지 정보 - 이메일 변경
			case View.MYPAGE_ADDR_UPDATE: view = mypageService.adderupdate(); break;//마이페이지 정보 - 주소 변경
			case View.MYPAGE_PASSWORD_UPDATE: view = mypageService.passwordupdate(); break;//마이페이지-비밀번호 변경
			case View.MYPAGE_CHANGEMONEY: view = mypageService.changemoney(); break;//마이페이지- 마일리지=>예치금으로 변경
			case View.MYPAGE_UNREGISTER: view = mypageService.unregister(); break;//탈퇴
			case View.MYPAGE_ORDER: view = mypageService.order(); break;//주문 및 내역
			case View.NEWPRODUCT_ORDERLIST: view = mypageService.orderList(); break;//주문내역 보기
			case View.MYPAGE_CHARGINH: view = mypageService.moneyCharging(); break;//예치금충전
			//관리자로그인
			case View.MANAGERLOGIN: view = managerService.login(); break;//관리자로그인
			case View.MANAGER_MAIN: view = managerBookService.BookMain(); break;//관리자 로그인 후 메인
			//공지사항
			case View.NOTICE: view = noticeService.Notice(); break;//공지사항메인
			case View.NOTICE_INSERT: view = noticeService.noticeInsert(); break;//공지사항 추가
			case View.NOTICE_READ: view = noticeService.noticeRead(); break;//공지사항 읽기
			case View.NOTICE_UPDATE: view = noticeService.noticeUpdate(); break;//공지사항 수정
			case View.NOTICE_DELETE: view = noticeService.noticeDelete(); break;//공지사항 삭제
			//관리자 상품관리
			case View.MANAGERPRODUCT_MAIN: view = managerStockService.managerProductMain(); break;//관리자 상품 메인
			case View.MANAGERSTOCK_NEW: view = managerStockService.newProduct(); break;//관리자 일반상품목룍
			case View.MANAGERSTOCK_NEWVIEW: view = managerStockService.newProductPlus(); break;//관리자 재고추가
			case View.MANAGERSTOCK_NEWSLACKTCOK: view = managerStockService.newProductLack(); break;//관리자 일반상품 재고확인 및 출력
			//관리자상품관리 - 중고상품
			case View.MANAGERSTOCK_USED: view = managerStockService.usedProduct(); break;
			case View.MANAGERSTOCK_USEDVIEW: view = managerStockService.usedProductPlus(); break;
			case View.MANAGERSTOCK_USEDSLACKTCOK: view = managerStockService.usedProductLack(); break;
			//관리자상품추가 - 일반
			case View.MANAGERPRODUCT_MANAGEMENT_MAIN: view = managerProductService.ManagerNewProductMain(); break;//관리자 일반상품추가 메인
			case View.MANAGERPRODUCT_NEW_INSERT: view = managerProductService.ManagerNewProductInsert(); break;//관리자 일반상품추가 
			case View.MANAGERPRODUCT_NEW_UPDATE: view = managerProductService.ManagerNewProductUpdate(); break;//관리자 일반상품수정
			case View.MANAGERPRODUCT_NEW_DELETE: view = managerProductService.ManagerNewProductDelete(); break;//관리자 일반상품삭제
			//관리자중고상품추가
			case View.MANAGERPRODUCT_MANAGEMENT_MAIN_USED: view = managerProductService.ManagerUsedProductMain(); break;
			case View.MANAGERPRODUCT_USED_INSERT: view = managerProductService.ManagerUsedProductInsert(); break;//관리자 중고상품추가
			case View.MANAGERPRODUCT_USED_UPDATE: view = managerProductService.ManagerUsedProductUpdate(); break;//관리자 중고상품수정
			case View.MANAGERPRODUCT_USED_DELETE: view = managerProductService.ManagerUsedProductDelete(); break;//관리자 중고상품삭제
			//관리자- 거래처
			case View.MANAGER_BUYER_MAIN: view = managerBuyerService.buyerMain(); break;//관리자 - 거래처 메인
			case View.MANAGER_BUYER_ADD: view = managerBuyerService.buyerAdd(); break;//관리자 - 거래처 추가
			case View.MANAGER_BUYER_UPDATE: view = managerBuyerService.buyerUpdate(); break;//관리자 - 거래처 수정
			case View.MANAGER_BUYER_LIST: view = managerBuyerService.buyerList(); break;//관리자 - 거래처 리스트
			case View.MANAGER_BUYER_DELETE: view = managerBuyerService.buyerDelete(); break;//관리자 - 거래처 삭제
			case View.MANAGER_BUYER_VIEW: view = managerBuyerService.buyerView(); break;//관리자 - 거래처 확인
			//관리자 - 장르
			case View.MANAGER_LPROD_LIST: view = managerLprodService.lprodMain(); break;//관리자 - 장르메인 목록
			case View.MANAGER_LPROD_INSERT: view = managerLprodService.lprodInsert(); break;//관리자 - 장르 추가
			case View.MANAGER_LPROD_DELETE: view = managerLprodService.lprodDelete(); break;//관리자 - 장르 삭제
			//관리자 - 중고등급
			case View.MANAGER_GRADE_LIST: view = managerGradeService.gradeMain(); break;//관리자 - 중고등급 메인 목록
			case View.MANAGER_GRADE_INSERT: view = managerGradeService.gradeInsert(); break;//관리자 - 중고등급 추가
			case View.MANAGER_GRADE_DELETE: view = managerGradeService.gradeDelete(); break;//관리자 - 중고등급 삭제
			//QNA
			case View.QNA : view = qnaService.Qna(); break; //회원이 게시판으로 가는 메인
			case View.QNA_MEM_READ : view = qnaService.qnaMemberRead(); break;
			case View.QNA_ALL_READ : view = qnaService.qnaAllRead(); break;
			case View.QNA_QUESTION_UPDATE: view = qnaService.qnaMemberUpdate(); break;
			case View.QNA_QUESTION_DELETE: view = qnaService.qnaMemberDelete(); break;
			case View.QNA_QUESTION_INSERT: view = qnaService.qnaMemberInsert(); break;
			case View.QNA_MANAGER_READ: view = qnaService.ManagerRead();break;
			case View.QNA_ANSWER_INSERT: view = qnaService.qnaManagerInsert(); break;
			case View.QNA_ANSWER_DELETE: view = qnaService.qnaManagerDelete(); break;
			case View.QNA_ANSWER_UPDATE: view = qnaService.qnaManagerUpdate(); break;
			//회원상품
			case View.PRODUCT_MAIN: view = productService.ProductMain(); break;//일반상품, 중고상품으로 갈수 있는 메인
			//회원상품 - 일반
			case View.NEWPRODUCT_MAIN: view = newProductService.NewProductMain(); break;//일반상품메인
			case View.NEWPRODUCT_VIEW: view = newProductService.NewProductView(); break;//일반상품 전체보기
			case View.NEWPRODUCT_VIEW_DETAIL: view = newProductService.NewProductDetail(); break;//일반상품에대한 디테일한 정보 출력
			case View.NEWPRODUCT_SEARCH: view = newProductService.NewProductSearch(); break;//일반상품 검색
			case View.NEWPRODUCT_SEARCH_MENU : view = newProductService.NewProductSearchmenu(); break;//일반상품 검색-제목
			case View.NEWPRODUCT_LPROD: view = newProductService.NewProductlprod(); break;//일반상품-분류별
			case View.NEWPRODUCT_CART: view = newProductService.NewProductCart(); break;//일반상품-담기
			case View.NEWPRODUCT_ORDER: view = newProductService.NewProductOrder(); break;//일반상품-바로주문
			//구매
			case View.BUYPRODUCT_CARTALL: view = prodBuyService.productAlllorder(); break;//일반상품 담긴거 주문
			case View.BUYPRODUCT_BUY: view = prodBuyService.productBuy(); break;//구매
			case View.BUYPRODUCT_DELETE: view = prodBuyService.productDelete(); break;//삭제
			case View.BUYPRODUCT_CHANGE: view = prodBuyService.productChange(); break;//수량변경
			//회원상품 - 중고
			case View.USEDPRODUCT_MAIN: view = usedProductService.UsedProductMain(); break;//중고상품메인
			case View.USEDPRODUCT_VIEW: view = usedProductService.UsedProductView(); break;//중고상품 전체보기
			case View.USEDPRODUCT_VIEW_DETAIL: view = usedProductService.UsedProductDetail(); break;//일반상품에대한 디테일한 정보 출력
			case View.USEDPRODUCT_SEARCH: view = usedProductService.UsedProductSearch(); break;//중고상품 검색
			case View.USEDPRODUCT_SEARCH_MENU : view = usedProductService.UsedProductSearchmenu(); break;//중고상품 검색 - 제목, 출판사,저자에 따라 출력
			case View.USEDPRODUCT_LPROD: view = usedProductService.UsedProductlprod(); break;//중고상품-분류별
			case View.USEDPRODUCT_CART: view = usedProductService.UsedProductCart(); break;//중고상품-담기
			case View.USEDPRODUCT_ORDER: view = usedProductService.UsedProductOrder(); break;//중고상품-바로주문
			}
		}
	}

	private int home() {
		System.out.println("---------------------------------------");
		System.out.println("1.로그인\t2.회원가입\t0.프로그램 종료");
		System.out.println("---------------------------------------");
		System.out.print("번호 입력 : ");

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1: return View.LOGIN; 
		case 2: return View.JOIN; 
		case 9999: return View.MANAGERLOGIN;
		case 0:
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
			break;
		}
		return View.HOME;
	}

}
