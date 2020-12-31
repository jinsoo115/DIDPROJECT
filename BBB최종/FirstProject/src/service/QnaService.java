package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.QnaDao;

public class QnaService {

	private static QnaService instance;
	private QnaService(){}
	public static QnaService getInstance(){
		if(instance == null){
			instance = new QnaService();
		}
		return instance;
	}
	private QnaDao qnaDao = QnaDao.getInstance();
	private int currentQnaNo;//멤버용
	private int ManagerQnaNo;//관리자용
	private String memberpassword;
	private String objectnum = "0";

	public int Qna(){
		Map<String, Object> check = qnaDao.check(Controller.loginMember.get("MANAGER_ID"));
		if(check!=null){
			System.out.println("======================BIBIDI BABIDI BOOK====================");
			System.out.println("===========================관리자모드==========================");
			System.out.println("===========================Q&A관리==========================");
		}
		System.out.println();
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*QNA*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		System.out.println("번호\t제목\t작성자ID\t작성날짜\t\t답변여부\t공개여부");

		List<Map<String, Object>> qna = qnaDao.showList();
		for(int i = 0; i < qna.size(); i++){
			System.out.print(qna.get(i).get("QA_NO")+"\t");
			System.out.print(qna.get(i).get("Q_TITLE")+"\t");
			System.out.print(qna.get(i).get("MEM_ID")+"\t");
			System.out.print(qna.get(i).get("Q_DATE")+"\t");
			String answer = "";
			if(qna.get(i).get("A_ANSWER")==null){
				answer = "미답변";
			}else{
				answer = "답변 완료";
			}
			System.out.print(answer+"\t");
			String password = "";
			if(qna.get(i).get("Q_PW").equals(objectnum)){
				password = "공개";
			}else{
				password = "비공개";
			}
			System.out.println(password);
		}
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");

		if(check == null){//멤버가보는 메뉴
			System.out.println("1.조회\t2.등록\t0.돌아가기");
			System.out.print("번호 입력 : ");
			int input= ScanUtil.nextInt();
			switch(input){
			case 1 :
				System.out.print("게시글 번호 입력 : ");
				currentQnaNo = ScanUtil.nextInt();
				Map<String, Object> checkpassword = qnaDao.checkpassword(currentQnaNo);
				if(checkpassword==null){
					System.out.println("게시글이 없습니다.");
					return View.QNA;
				}
				if(checkpassword.get("Q_PW").equals(objectnum)){
					memberpassword = "0";
					return View.QNA_ALL_READ;
				}else{
					System.out.print("비밀번호를 입력 : ");
					memberpassword = ScanUtil.nextLine();
				}
				return View.QNA_MEM_READ;
			case 2: 
				return View.QNA_QUESTION_INSERT;
			case 0: 
				return View.BOOKMAIN;
			}
			return View.QNA;
		}else{//관리자메뉴
			System.out.println("1.조회\t0.돌아가기");
			System.out.println("============================================================");
			System.out.print("번호 입력 : ");
			int select = ScanUtil.nextInt();
			switch(select){
			case 1:
				System.out.print("게시글 번호 입력 : ");
				ManagerQnaNo = ScanUtil.nextInt();
				return View.QNA_MANAGER_READ;
			case 0:
				return View.MANAGER_MAIN;
			}
		}
		return View.QNA;
	}

	public int qnaAllRead(){
		Map<String, Object> qna = qnaDao.MatchAll(currentQnaNo, memberpassword);

		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		System.out.println("글번호 :" + qna.get("QA_NO"));
		System.out.println("작성자ID :" + qna.get("MEM_ID"));
		System.out.println("작성일 :" + qna.get("Q_DATE"));
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		System.out.println("제목 :" + qna.get("Q_TITLE"));
		System.out.println("내용 :" + qna.get("Q_CONTENT"));
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		if(qna.get("A_ANSWER")==null){
			System.out.println("등록된 답변이 없습니다.");
		}else{
			System.out.println("답변자 ID : "+ qna.get("MANAGER_ID"));
			System.out.println("답변날짜 : "+ qna.get("A_DATE"));
			System.out.println("답변 : "+ qna.get("A_ANSWER"));
		}
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
		Map<String, Object> user = qnaDao.MathchAlluser(currentQnaNo, memberpassword);
		if(user!=null){
			System.out.println("~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-");
			System.out.println("1. 수정\t2.삭제\t0.되돌아가기");
			System.out.println("~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-");
			System.out.print("번호 입력 : ");

			int input = ScanUtil.nextInt();
			switch (input){
			case 1:
				return View.QNA_QUESTION_UPDATE;
			case 2: 
				return View.QNA_QUESTION_DELETE;
			case 0:
				return View.BOOKMAIN;
			}
		}
		return View.QNA;
	}

	public int qnaMemberRead(){
		Map<String, Object> qna = qnaDao.Match(memberpassword, currentQnaNo); 
		if(qna== null){
			System.out.println("비밀번호가틀리거나 회원이 작성하신 글이  아닙니다.");
		}else{
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			System.out.println("글번호 :" + qna.get("QA_NO"));
			System.out.println("작성자ID :" + qna.get("MEM_ID"));
			System.out.println("작성일 :" + qna.get("Q_DATE"));
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			System.out.println("제목 :" + qna.get("Q_TITLE"));
			System.out.println("내용 :" + qna.get("Q_CONTENT"));
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			if(qna.get("A_ANSWER")==null){
				System.out.println("등록된 답변이 없습니다.");
			}else{
				System.out.println("답변자 ID : "+ qna.get("MANAGER_ID"));
				System.out.println("답변날짜 : "+ qna.get("A_DATE"));
				System.out.println("답변 : "+ qna.get("A_ANSWER"));
			}
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			System.out.println();
			System.out.println("~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-");
			System.out.println("1. 수정\t2.삭제\t0.되돌아가기");
			System.out.println("~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-");
			System.out.print("번호 입력 : ");

			int input = ScanUtil.nextInt();
			switch (input){
			case 1:
				return View.QNA_QUESTION_UPDATE;
			case 2: 
				return View.QNA_QUESTION_DELETE;
			case 0: 
				return View.BOOKMAIN;
			}
		}
		return View.QNA;
	}

	public int qnaMemberUpdate(){
		System.out.print("바꾸고 싶은 제목을 입력  : ");
		String title = ScanUtil.nextLine();
		System.out.print("바꾸고 싶은 내용을 입력  : ");
		String content = ScanUtil.nextLine();

		int result = qnaDao.questionUpdate(title, content, currentQnaNo);
		if(result > 0){
			System.out.println("수정 완료");
		}else{
			System.out.println("수정 실패");
		}
		return View.QNA;
	}

	public int qnaMemberDelete(){
		int result = qnaDao.questionDelete(currentQnaNo);
		if(result > 0){
			System.out.println("삭제 완료");
		}else{
			System.out.println("삭제 실패");
		}
		return View.QNA;	
	}

	public int qnaMemberInsert(){
		System.out.print("제목 : ");
		String title = ScanUtil.nextLine();
		System.out.print("내용 : ");
		String content = ScanUtil.nextLine();
		System.out.print("공개여부(1.공개/2.비공개) : ");
		int input = ScanUtil.nextInt();
		String password;
		int result = 0;
		if(input == 1){
			password = "0";
			result = qnaDao.questionInsert(title, content, password);
		}else if(input == 2){
			System.out.print("비밀번호를 입력 : ");
			password = ScanUtil.nextLine();
			result = qnaDao.questionInsert(title, content, password);
		}

		if(result>0){
			System.out.println("질문등록완료!");
		}else{
			System.out.println("질문등록실패!");
		}
		return View.QNA;
	}

	public int ManagerRead(){
		Map<String, Object> qna = qnaDao.managerRead(ManagerQnaNo);
		if(qna!=null){
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			System.out.println("글번호 :" + qna.get("QA_NO"));
			System.out.println("작성자ID :" + qna.get("MEM_ID"));
			System.out.println("작성일 :" + qna.get("Q_DATE"));
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			System.out.println("제목 :" + qna.get("Q_TITLE"));
			System.out.println("내용 :" + qna.get("Q_CONTENT"));
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			if(qna.get("A_ANSWER")==null){
				System.out.println("등록된 답변이 없습니다.");
			}else{
				System.out.println("답변자 ID : "+ qna.get("MANAGER_ID"));
				System.out.println("답변날짜 : "+ qna.get("A_DATE"));
				System.out.println("답변 : "+ qna.get("A_ANSWER"));
			}
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
			System.out.println();
			System.out.println("########################################");
			System.out.println("1.답변등록\t2.수정\t3.삭제\t0.되돌아가기");
			System.out.println("########################################");
			System.out.print("번호 입력 : ");
			int input = ScanUtil.nextInt();
			switch(input){
			case 1 :
				return View.QNA_ANSWER_INSERT;
			case 2 :
				return View.QNA_ANSWER_UPDATE;	
			case 3 :
				return View.QNA_ANSWER_DELETE;	
			case 0 :
				return View.BOOKMAIN;
			}
			return View.QNA;
		}
		else if(qna==null){
			System.out.println("해당 게시물의 번호가 존재하지 않습니다.");
			return View.QNA;
		}
		return View.QNA;
	}
	public int qnaManagerInsert() {
		System.out.print("내용 : ");
		String content = ScanUtil.nextLine();
		int result = qnaDao.answerInsert(content, ManagerQnaNo);
		if(result > 0){
			System.out.println("답변 등록");
		}else{
			System.out.println("답변 없음");
		}
		return View.QNA;
	}

	public int qnaManagerUpdate(){
		System.out.print("바꾸고 싶은 내용을 입력  : ");
		String content = ScanUtil.nextLine();

		int result = qnaDao.answerUpdate(content, ManagerQnaNo);
		if(result > 0){
			System.out.println("수정 완료");
		}else{
			System.out.println("수정 실패");
		}
		return View.QNA;
	}

	public int qnaManagerDelete(){
		int result = qnaDao.answerDelete(ManagerQnaNo);
		if(result > 0){
			System.out.println("삭제 완료");
		}else{
			System.out.println("삭제 실패");
		}
		return View.QNA;	
	}

}//
