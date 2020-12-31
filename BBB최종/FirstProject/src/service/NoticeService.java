package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.NoticeDao;

public class NoticeService {
	private static NoticeService instance;
	private NoticeService(){}
	public static NoticeService getInstance() {
		if(instance == null){
			instance = new NoticeService();
		}
		return instance;
	}
	NoticeDao noticeDao = NoticeDao.getInstance();
	private int currentBoardNo;
	public int Notice(){
		while(true){
			Map<String, Object> check = noticeDao.check();
			if(check!=null){
				System.out.println("============BIBIDI BABIDI BOOK==========");
				System.out.println("=================관리자모드================");
				System.out.println("================공지사항관리================");
			}
			System.out.println();
			System.out.println("__________________NOTICE__________________");
			System.out.println("번호\t제목\t작성자\t작성일");
			List<Map<String, Object>> notice = noticeDao.noticeAll();
			for(int i = 0; i < notice.size(); i++){
				System.out.print(notice.get(i).get("BOARD_NO") + "\t");
				System.out.print(notice.get(i).get("BOARD_TITLE") + "\t");
				System.out.print(notice.get(i).get("MANAGER_ID") + "\t");
				String date = ((String)notice.get(i).get("BOARD_DATE")).substring(0,4) + "-" + ((String)notice.get(i).get("BOARD_DATE")).substring(4,6) + "-" + ((String)notice.get(i).get("BOARD_DATE")).substring(6,8);
				System.out.print(date + "\t");
				System.out.println();
			}
			System.out.println("___________________________________________");
			if(check==null){
				System.out.print("1.내용\t0.되돌아가기\n입력 : ");
				int input = ScanUtil.nextInt();
				switch (input) {
				case 1:
					System.out.print("게시글 번호 입력 : ");
					currentBoardNo = ScanUtil.nextInt();
					return View.NOTICE_READ;
				case 0:
					return View.BOOKMAIN;
				}
			}else{
				System.out.println("1.내용\t2.등록\t0.되돌아가기");
				System.out.println("========================================");
				System.out.print("입력 : ");
				int input = ScanUtil.nextInt();
				switch (input) {
				case 1:
					System.out.print("게시글 번호 입력 : ");
					currentBoardNo = ScanUtil.nextInt();
					return View.NOTICE_READ;
				case 2:
					return View.NOTICE_INSERT;
				case 0:
					return View.MANAGER_MAIN;
				}
			}
		}
	}	
	
	public int noticeRead(){
		Map<String, Object> map = noticeDao.noticeRead(currentBoardNo);
		if(map==null){
			System.out.println("게시글이 없습니다.");
			return View.NOTICE;
		}else{
			System.out.println("-------------------------------------");
			System.out.println("번호\t: " + map.get("BOARD_NO"));
			System.out.println("작성자\t: " + map.get("MANAGER_ID") );
			String date = ((String)map.get("BOARD_DATE")).substring(0,4) + "-" + ((String)map.get("BOARD_DATE")).substring(4,6) + "-" + ((String)map.get("BOARD_DATE")).substring(6,8);
			System.out.println("작성일\t: " + date);
			System.out.println("제목\t: " + map.get("BOARD_TITLE"));
			System.out.println("내용\t: " + map.get("BOARD_CONTENT"));
			System.out.println("-------------------------------------");
			Map<String, Object> check = noticeDao.check();
			if(check==null){
				return View.NOTICE;
			}
			System.out.print("1.수정\t2.삭제\t0.메인\n입력 : ");
		}

		int input = ScanUtil.nextInt();

		switch (input) {
		case 1:
			return View.NOTICE_UPDATE;
		case 2:
			return View.NOTICE_DELETE;
		case 0:
			return View.NOTICE;
		}
		return View.NOTICE;

	}
	
	public int noticeInsert() {
		String title = inputTitle();
		System.out.print("내용 : ");
		String content = ScanUtil.nextLine();
		String id = (String)Controller.loginMember.get("MANAGER_ID") ;
		System.out.println(id);
		int result = noticeDao.noticeInsert(title, content, id);
		if(result > 0){
			System.out.println("공지사항이 등록되었습니다.");
		}else{
			System.out.println("공지사항 등록이 실패하였습니다.");
		}
		return View.NOTICE;
	}
	
	private String inputTitle() {
		String title = null;
		while (true) {
			System.out.print("제목을 입력하세요(2~5글자) : ");
			title = ScanUtil.nextLine();
			// 정규식 체크
			boolean result = RegEx.regtitle(title);
			if (result == false) {
				System.out.println("제목은 2~5자리 이어야 합니다.");
				continue;
			} else {
				break;
			}
		}
		return title;
	}
	
	public int noticeUpdate() {
		String title = inputTitle();
		System.out.print("내용을 입력하세요 : ");
		String content = ScanUtil.nextLine();
		int result = noticeDao.noticeUpdate(title, content, currentBoardNo);
		if(0 < result){
			System.out.println("수정 성공");
		}else{
			System.out.println("수정 실패");
		}
		return View.NOTICE;
	}
	
	public int noticeDelete() {
		int result = noticeDao.noticeDelete(currentBoardNo);
		if(0 < result){
			System.out.println("삭제 성공");
		}else{
			System.out.println("삭제 실패");
		}

		return View.NOTICE;
	}
}
