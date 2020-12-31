package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ScanUtil;
import util.View;
import dao.ManagerBuyerDao;

public class ManagerBuyerService {
	private static ManagerBuyerService instance;
	private ManagerBuyerService(){}
	public static ManagerBuyerService getInstance() {
		if(instance == null){
			instance = new ManagerBuyerService();
		}
		return instance;
	}
	
	ManagerBuyerDao managerBuyerDao = ManagerBuyerDao.getInstance();

	public int buyerMain() {
		System.out.println("==============BIBIDI BABIDI BOOK============");
		System.out.println("===================관리자모드==================");
		System.out.println("===================거래처관리==================");
		System.out.println("1.거래처 추가\t2.거래처 목록\t0.되돌아가기");
		System.out.println("============================================");
		System.out.print("입력 : ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.MANAGER_BUYER_ADD;
		case 2: return View.MANAGER_BUYER_LIST; 
		case 0: return View.MANAGER_MAIN; 
		}
		return View.MANAGER_MAIN;
	}
	
	public int buyerAdd() {
		Map<String, Object> param = new HashMap<String, Object>();
		System.out.print("거래처명 : ");
		String buyerName = ScanUtil.nextLine();
		param.put("BUYER_NAME", buyerName);

		System.out.print("담당자명 : ");
		String name = ScanUtil.nextLine();
		param.put("BUYER_MANAGER", name);

		System.out.print("도로명 주소 입력 : ");
		String doroAdd = ScanUtil.nextLine();

		List<Map<String, Object>> doro = managerBuyerDao.selectAdd(doroAdd);
		for(int i = 0; i < doro.size(); i++){
			System.out.println(i+1 + " " +doro.get(i).get("ADD_NAME"));
		}
		System.out.print("주소를 선택해주세요 : ");
		int select = ScanUtil.nextInt();
		param.put("BUYER_ADD1", doro.get(select-1).get("ADD_NAME"));

		System.out.print("전화번호입력 : ");
		String tel = ScanUtil.nextLine();
		param.put("BUYER_TEL", tel);

		System.out.print("이메일입력 : ");
		String email = ScanUtil.nextLine();
		param.put("BUYER_EMAIL", email);

		System.out.print("상세주소입력 : ");
		String add = ScanUtil.nextLine();
		param.put("BUYER_ADD2", add);

		param.put("MEM_POST", doro.get(select-1).get("ADD_POST"));

		int result = managerBuyerDao.buyerInsert(param);
		if(result>0){
			System.out.println("거래처 등록 완료!");
		}else{
			System.out.println("거래처 등록 실패!");
		}
		return View.MANAGER_BUYER_MAIN;
	}
	
	private String buyerId;
	
	public int buyerList() {
		int page = 1;
		int pageSize = 10;
		while(true){
			Map<String, Object> maxcount = managerBuyerDao.buyerMax();
			int maxPage = Integer.parseInt(maxcount.get("COUNT").toString());
			maxPage = maxPage/pageSize + 1;
			if(page<=0){
				page = 1;
			}
			if(page>maxPage){
				page = maxPage;
			}
			List<Map<String, Object>> list = managerBuyerDao.buyerList(page, pageSize);
			System.out.println();
			System.out.println("==================현재 거래처 목록==================");
			for(int i = 0; i < list.size(); i++){
				System.out.print("거래처코드 : "+list.get(i).get("BUYER_ID")+" | ");
				System.out.println("거래처명 : " +list.get(i).get("BUYER_NAME"));
				System.out.println("------------------------------------");
			}
			System.out.println("=================현재 페이지"+page+"/"+maxPage+"=====================");
			System.out.println("1.이전페이지\t2.다음페이지\t3.원하는 페이지 입력");
			System.out.println("4.페이지당 목록 개수\t5.거래처 확인\t0.돌아가기");
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
				System.out.print("확인 하실 거래처코드 입력 : ");
				buyerId = ScanUtil.nextLine();
				return View.MANAGER_BUYER_VIEW;
			case 0:
				return View.MANAGER_BUYER_LIST;
			}
			
		}
	}

	public int buyerView() {
		Map<String, Object> map = managerBuyerDao.buyerView(buyerId);
		System.out.println();
		System.out.println("================거래처 확인==================");
		System.out.println("거래처코드 : "+map.get("BUYER_ID"));
		System.out.println("거래처명 : "+map.get("BUYER_NAME"));
		System.out.println("담당자 : "+map.get("BUYER_MANAGER"));
		System.out.println("전화번호 : "+map.get("BUYER_TEL"));
		System.out.println("이메일 : "+map.get("BUYER_EMAIL"));
		System.out.print("주소 : "+map.get("BUYER_ADD1"));
		if(map.get("BUYER_ADD2")!=null){
			System.out.print("  " + map.get("BUYER_ADD2"));
		}
		System.out.println("\n우편번호 : "+map.get("BUYER_POST"));
		System.out.println("============================================");
		System.out.println("1.수정 \t2.삭제\t0.되돌아가기");
		System.out.print("입력 : ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.MANAGER_BUYER_UPDATE;
		case 2: return View.MANAGER_BUYER_DELETE;
		case 0: return View.MANAGER_BUYER_MAIN;
		}
		return View.MANAGER_BUYER_MAIN;
	}
	
	public int buyerDelete() {
		System.out.println("--------거래처 삭제--------");
		int result = managerBuyerDao.buyerDelete(buyerId);
		if(result > 0){
			System.out.println("거래처 삭제가 성공했습니다.");
		}else{
			System.out.println("거래처 삭제가 실패했습니다.");
		}
		System.out.println("----------------------");
		return View.MANAGER_BUYER_MAIN;
	}
	
	public int buyerUpdate() {
		System.out.println("--------거래서 수정--------");
		Map<String, Object> param = new HashMap<String, Object>();
		System.out.print("거래처명 : ");
		String buyerName = ScanUtil.nextLine();
		param.put("BUYER_NAME", buyerName);

		System.out.print("담당자명 : ");
		String name = ScanUtil.nextLine();
		param.put("BUYER_MANAGER", name);

		System.out.print("도로명 주소 입력 : ");
		String doroAdd = ScanUtil.nextLine();


		List<Map<String, Object>> doro = managerBuyerDao.selectAdd(doroAdd);
		for(int i = 0; i < doro.size(); i++){
			System.out.println(i+1 + " " +doro.get(i).get("ADD_NAME"));
		}
		System.out.print("주소를 선택해주세요 : ");
		int select = ScanUtil.nextInt();
		param.put("BUYER_ADD1", doro.get(select-1).get("ADD_NAME"));

		System.out.print("전화번호입력 : ");
		String tel = ScanUtil.nextLine();
		param.put("BUYER_TEL", tel);

		System.out.print("이메일입력 : ");
		String email = ScanUtil.nextLine();
		param.put("BUYER_EMAIL", email);

		System.out.print("상세주소입력 : ");
		String add = ScanUtil.nextLine();
		param.put("BUYER_ADD2", add);

		param.put("MEM_POST", doro.get(select-1).get("ADD_POST"));
		int result = managerBuyerDao.buyerUpdate(param, buyerId);
		if(result > 0){
			System.out.println("거래처 수정이 성공했습니다.");
		}else{
			System.out.println("거래처 수정이 실패했습니다.");
		}
		System.out.println("----------------------");
		return View.MANAGER_BUYER_MAIN;		
	}

}
