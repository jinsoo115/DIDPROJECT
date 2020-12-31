package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import dao.MypageDao;
import util.View;

public class MypageService {
	private static MypageService instance;
	private MypageService(){}
	public static MypageService getInstance() {
		if(instance == null){
			instance = new MypageService();
		}
		return instance;
	}

	private MypageDao mypagedao = MypageDao.getInstance();

	//메인화면
	public int MypageMain() {
		System.out.println();
		System.out.println("===============" + Controller.loginMember.get("MEM_ID") + "님의 마이페이지================");
		System.out.println("이름 : "+ Controller.loginMember.get("MEM_NAME"));
		System.out.println("등급 : "+ Controller.loginMember.get("MEM_GRADE"));
		System.out.println("예치금 : "+ Controller.loginMember.get("MEM_MONEY"));
		System.out.println("마일리지 : "+ Controller.loginMember.get("MEM_MILEAGE"));
		System.out.println("=================================================");
		System.out.println();
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.println("1.개인정보 수정\t2.비밀번호 수정\t3.예치금 충전");
		System.out.println("4.주문 및 내역\t5.마일리지 전환\t6.회원탈퇴");
		System.out.println("0.되돌아가기");
		System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.print("원하는 번호 입력 : ");
		int input = ScanUtil.nextInt();
		System.out.println();
		switch(input){
		case 1: return View.MYPAGE_INFORM_UPDATE;
		case 2: return View.MYPAGE_PASSWORD_UPDATE;
		case 3: return View.MYPAGE_CHARGINH;
		case 4: return View.MYPAGE_ORDER;
		case 5: return View.MYPAGE_CHANGEMONEY;
		case 6: return View.MYPAGE_UNREGISTER;
		case 0: return View.BOOKMAIN;
		}
		return View.MYPAGE;
	}

	//1.개인정보수정
	public int informupdate(){
		System.out.println();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~현재정보~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("아이디 : "+Controller.loginMember.get("MEM_ID"));
		System.out.println("이름 : "+ Controller.loginMember.get("MEM_NAME"));
		System.out.println("핸드폰번호 : "+ Controller.loginMember.get("MEM_PHONE"));
		System.out.println("이메일 : "+ Controller.loginMember.get("MEM_EMAIL"));
		System.out.println("우편번호 : "+ Controller.loginMember.get("MEM_POST"));
		System.out.println("주소 : "+ Controller.loginMember.get("MEM_ADD1")+" "+Controller.loginMember.get("MEM_ADD2"));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("변경하고싶은 정보");
		System.out.println("1.핸드폰번호\t2.이메일\t3.주소\t0.되돌아가기");
		System.out.print("선택 : ");
		int input = ScanUtil.nextInt();
		System.out.println();
		switch(input){
		case 1: return View.MYPAGE_PHONE_UPDATE;
		case 2: return View.MYPAGE_EMAIL_UPDATE;
		case 3: return View.MYPAGE_ADDR_UPDATE;
		case 0: return View.MYPAGE;
		}
		return View.MYPAGE_INFORM_UPDATE;
	}

	//1.개인정보수정-핸드폰번호수정
	public int phoneupdate() {
		System.out.print("바꾸고 싶은 핸드폰 번호 입력 : ");
		String phone1 = ScanUtil.nextLine();
		String phone = "";
		int result = 0;
		if(phone1.length()==11){
			phone = phone1.substring(0, 3)+"-"
					+phone1.substring(3, 7)+"-"
					+phone1.substring(7, 11);

			result = mypagedao.Updatephone(phone);
		}else{
			System.out.println("11자리를 초과하거나 적게 입력하셨습니다.");
		}

		if(result>0){
			System.out.println("핸드폰 번호 수정완료!!");
			Map<String, Object> member = mypagedao.selectMember();
			Controller.loginMember = member;
		}else{
			System.out.println("핸드폰 번호 수정실패!!");
		}

		return View.MYPAGE_INFORM_UPDATE;
	}

	//1.개인정보수정-이메일수정
	public int emailupdate() {
		System.out.print("바꾸고 싶은 이메일 입력 : ");
		String email = ScanUtil.nextLine();

		int result = mypagedao.Updateemail(email);
		if(result>0){
			System.out.println("이메일 수정완료!!");
			Map<String, Object> member = mypagedao.selectMember();
			Controller.loginMember = member;
		}else{
			System.out.println("이메일 수정실패!!");
		}

		return View.MYPAGE_INFORM_UPDATE;
	}

	//1.개인정보수정-주소수정
	public int adderupdate() {
		System.out.print("도로명 주소 입력  : ");
		String doroAdd = ScanUtil.nextLine();
		List<Map<String, Object>> doro = mypagedao.selectAdd(doroAdd);
		System.out.println("-----------------------------------");
		for(int i = 0; i < doro.size(); i++){
			Map<String, Object> post = doro.get(i);
			System.out.println(i+1+". 우편번호  "+post.get("ADD_POST")+" / 주소 : "+post.get("ADD_NAME"));
		}
		System.out.println("-----------------------------------");
		System.out.print("주소를 선택해주세요  : ");
		int select = ScanUtil.nextInt();
		Object postnum = doro.get(select-1).get("ADD_POST");
		System.out.print("상세주소를 입력 : ");
		String add2 = ScanUtil.nextLine();

		int result = mypagedao.Updateaddre(postnum, add2);
		if(result>0){
			System.out.println("주소 수정완료!!");
			Map<String, Object> member = mypagedao.selectMember();
			Controller.loginMember = member;
		}else{
			System.out.println("주소 수정실패!!");
		}
		return View.MYPAGE_INFORM_UPDATE;
	}

	//2. 비밀번호 수정
	public int passwordupdate(){
		boolean run = true;
		while(run){
			System.out.print("현재 비밀번호를 입력 : ");
			String oldpassword = ScanUtil.nextLine();
			String newpassword = inputPw();
			int result = 0;
			if(Controller.loginMember.get("MEM_PASSWORD").equals(oldpassword)){
				result = mypagedao.Updatepassword(newpassword);
				run = false;
			}else{
				System.out.println("현재 비밀번호가 틀립니다.");
			}
			if(result>0){
				System.out.println("비밀번호 수정완료!!");
				Map<String, Object> member = mypagedao.selectMember();
				Controller.loginMember = member;
			}else{
				System.out.println("비밀번호 수정실패!!");
			}
		}
		return View.MYPAGE;
	}

	//2-1.비밀번호 정규식 체크
	private String inputPw() {
		String pw = null;
		String pwCheck = null;
		while (true) {
			System.out.print("변경할 비밀번호 (4~10자리/영문 및 숫자만 입력하세요) : ");
			pw = ScanUtil.nextLine();
			System.out.print("변경할 비밀번호확인 (4~10자리/영문 및 숫자만 입력하세요) : ");
			pwCheck = ScanUtil.nextLine();
			if(pw.equals(pwCheck)){
				boolean result = RegEx.regPw(pw);
				if (result == false) {
					System.out.println("비밀번호는 4~10자리 및 영문및 숫자여야 합니다다.");
					continue;
				} else {
					break;
				}
			}else{
				System.out.println("입력하신 비밀번호가 틀렸습니다.");
				continue;
			}
		}
		return pw;
	}

	//4. 마일리지를 예치금으로 전환
	public int changemoney() {
		int oldmileage = Integer.parseInt(Controller.loginMember.get("MEM_MILEAGE").toString());
		if(oldmileage>0){
			System.out.println("------------ 예치금으로 전환하기 ------------");
			System.out.println(" - 최소 10원부터 예치금으로 전환할 수 있습니다.");
			System.out.println("보유 마일리지 : "+ oldmileage);
			System.out.print("전환할 마일리지 : ");
			int mileage = ScanUtil.nextInt();
			int result = 0;
			if(mileage >= 10){
				result = mypagedao.changemoney(mileage);
			}

			if(result>0){
				System.out.println("전환완료!!");
				Map<String, Object> member = mypagedao.selectMember();
				Controller.loginMember = member;
			}else{
				System.out.println("전환실패!!");
			}
		}else{
			System.out.println("전환할 마일리지가 없습니다.");
		}

		return View.MYPAGE;
	}
	//5.회원탈퇴
	public int unregister() {
		int result = 0;;
		System.out.print("회원탈퇴를 진심으로 하시겠습니까?\n회원탈퇴를 진행시 모든 회원의 정보는 복구 할 수 없습니다.(Y)");
		String yes = ScanUtil.nextLine();
		if(yes.equals("Y") || yes.equals("y")){
			result = mypagedao.unregister();

		}
		if(result > 0){
			System.out.println("탈퇴가 완료되었습니다.");
			Controller.loginMember = null;
			return View.HOME;
		}else{
			System.out.println("탈퇴를 실패하였습니다.");
		}
		return View.MYPAGE;
	}

	public int order() {
		System.out.println("1.장바구니 주문 \t2.주문내역\t0.돌아가기");
		System.out.print("입력 : ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.BUYPRODUCT_CARTALL;
		case 2: return View.NEWPRODUCT_ORDERLIST;
		case 0: return View.MYPAGE;	
		}
		return View.MYPAGE_ORDER;
	}

	public int orderList() {
		List<Map<String, Object>> list = mypagedao.orderList();
		System.out.println("=============="+Controller.loginMember.get("MEM_ID")+"님의 일반책 주문내역==============");
		if(list.size()==0){
			System.out.println("일반책 주문내역이 없습니다.");
		}else{
			for(int i = 0; i < list.size(); i++){
				System.out.println("제목 : " + list.get(i).get("PROD_NAME") + "\t");
				System.out.print("가격 : " + list.get(i).get("PROD_PRICE") + "\t");
				System.out.print("개수 :" + list.get(i).get("BQTY") + "\t");
				System.out.println("총 가격 : "+list.get(i).get("PRICE"));
				System.out.println("----------------------------------------");
			}
		}

		List<Map<String, Object>> usedList = mypagedao.usedOrderList();
		System.out.println("=============="+Controller.loginMember.get("MEM_ID")+"님의 중고책 주문내역==============");
		if(usedList.size()==0){
			System.out.println("중고책 주문내역이 없습니다.");
		}else{
			for(int i = 0; i < list.size(); i++){
				System.out.println("제목 : " + usedList.get(i).get("USEDPROD_NAME") + "\t");
				System.out.print("가격 : " + usedList.get(i).get("USEDPROD_PRICE") + "\t");
				System.out.print("개수 :" + usedList.get(i).get("USEDBQTY") + "\t");
				System.out.println("총 가격 : "+usedList.get(i).get("PRICE"));
				System.out.println("----------------------------------------");
			}
		}
		return View.MYPAGE;
	}

	public int moneyCharging() {
		System.out.print("예치금 충전입니다. 얼마를 충전하시겠습니까?\n입력 : ");
		int money = ScanUtil.nextInt();
		if(money == 0){
			System.out.println("0원을 입력하셔서 종료합니다.");
			return View.MYPAGE;
		}
		int result = mypagedao.moneyCharging(money);
		if(result > 0){
			System.out.println("예치금 충전이 완료되었습니다.");
			Map<String, Object> member = mypagedao.selectMember();
			Controller.loginMember = member;
		}else{
			System.out.println("예치금 충전이 실패하였습니다.");
		}
		return View.MYPAGE;
	}
}
