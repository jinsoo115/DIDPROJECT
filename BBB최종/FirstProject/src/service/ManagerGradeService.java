package service;

import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import dao.ManagerGradeDao;


public class ManagerGradeService {
	private static ManagerGradeService instance;
	private ManagerGradeService(){}
	public static ManagerGradeService getInstance() {
		if(instance == null){
			instance = new ManagerGradeService();
		}
		return instance;
	}
	
	ManagerGradeDao gradeDao = ManagerGradeDao.getInstance();
	
	public int gradeMain() {
		System.out.println("============BIBIDI BABIDI BOOK==========");
		System.out.println("=================관리자모드================");
		System.out.println("================중고등급관리================");
		System.out.println("~-~-~-등급 목록~-~-~-");
		System.out.println("등급코드\t등급명");
		List<Map<String, Object>> list = gradeDao.lprodList();
		for(int i = 0; i < list.size(); i++){
			System.out.print(list.get(i).get("GRADE_ID")+"\t");
			System.out.println(list.get(i).get("GRADE_NAME"));
		}
		System.out.println("~-~-~-~-~-~-~-~-~-~-");
		System.out.println("1.등급추가\t2.등급삭제\t0.돌아가기");
		System.out.println("========================================");
		System.out.print("입력 : ");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.MANAGER_GRADE_INSERT;
		case 2: return View.MANAGER_GRADE_DELETE;
		case 0: return View.MANAGER_MAIN;

		}
		return View.MANAGER_MAIN;
	}
	
	public int gradeInsert() {
		System.out.println("--------등급추가--------");
		System.out.print("등급코드 : ");
		String gradeId = ScanUtil.nextLine(); 
		System.out.print("등급명 : ");
		String gradeName = ScanUtil.nextLine();
		int result = gradeDao.lprodInsert(gradeId, gradeName);
		if(result > 0){
			System.out.println("등급 추가를 성공했습니다.");
		}else{
			System.out.println("등급 추가를 실패했습니다.");
		}

System.out.println("----------------------");
		return View.MANAGER_MAIN;
	}
	
	public int gradeDelete() {
		System.out.println("--------등급삭제--------");
		System.out.print("삭제할 등급 코드 입력 : ");
		String gradeId = ScanUtil.nextLine();
		int result = gradeDao.lprodDelete(gradeId);
		if(result > 0){
			System.out.println("등급 삭제가 성공했습니다.");
		}else{
			System.out.println("등급 삭제가 실패했습니다.");
		}
		System.out.println("----------------------");
		return View.MANAGER_MAIN;
	}
}
