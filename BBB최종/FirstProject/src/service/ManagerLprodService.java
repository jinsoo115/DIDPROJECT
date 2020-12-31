package service;

import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import dao.ManagerLprodDao;

public class ManagerLprodService {
	private static ManagerLprodService instance;
	private ManagerLprodService(){}
	public static ManagerLprodService getInstance() {
		if(instance == null){
			instance = new ManagerLprodService();
		}
		return instance;
	}
	
	ManagerLprodDao lprodDao = ManagerLprodDao.getInstance();
	
	public int lprodMain() {
		System.out.println("============BIBIDI BABIDI BOOK==========");
		System.out.println("=================관리자모드================");
		System.out.println("=================장르관리================");
		System.out.println("~-~-~-~-장르 목록~-~-~-~-");
		System.out.println("장르코드\t장르명");
		List<Map<String, Object>> list = lprodDao.lprodList();
		for(int i = 0; i < list.size(); i++){
			System.out.print(list.get(i).get("LPROD_GU")+"\t");
			System.out.println(list.get(i).get("LPROD_NAME"));
		}
		System.out.println("~-~-~-~-~-~-~-~-~-~-~-");
		System.out.println("1.장르추가\t2.장르삭제\t0.돌아가기");
		System.out.println("========================================");
		System.out.print("입력 : ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.MANAGER_LPROD_INSERT;
		case 2: return View.MANAGER_LPROD_DELETE;
		case 0: return View.MANAGER_MAIN;

		}
		return View.MANAGER_MAIN;
	}
	
	public int lprodInsert() {
		System.out.println("-------장르추가-------");
		System.out.print("장르코드 : ");
		String lprodGu = ScanUtil.nextLine(); 
		System.out.print("장르명 : ");
		String lprodName = ScanUtil.nextLine();
		int result = lprodDao.lprodInsert(lprodGu, lprodName);
		if(result > 0){
			System.out.println("장르 추가를 성공했습니다.");
		}else{
			System.out.println("장르 추가를 실패했습니다.");
		}
		System.out.println("----------------------");
		return View.MANAGER_MAIN;
	}
	
	public int lprodDelete() {
		System.out.println("--------장르삭제--------");
		System.out.print("삭제할 장르 코드 입력 : ");
		String lprodGu = ScanUtil.nextLine();
		int result = lprodDao.lprodDelete(lprodGu);
		if(result > 0){
			System.out.println("등급 삭제가 성공했습니다.");
		}else{
			System.out.println("등급 삭제가 실패했습니다.");
		}
		System.out.println("----------------------");
		return View.MANAGER_MAIN;
	}
}
