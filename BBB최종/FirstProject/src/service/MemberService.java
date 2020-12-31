package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ScanUtil;
import util.View;
import controller.Controller;
import dao.MemberDao;

public class MemberService {
	private static MemberService instance;
	private MemberService(){}
	public static MemberService getInstance() {
		if(instance == null){
			instance = new MemberService();
		}
		return instance;
	}

	private MemberDao memberDao = MemberDao.getInstance();
	public int join() {
		System.out.println("==============회원가입=============");		
		String id = inputId();
		String password = inputPw();
		String name = inputName();
		String gender = inputGender();
		String phone = inputPhone();
		System.out.print("이메일입력 : ");
		String email = ScanUtil.nextLine();

		System.out.print("도로명 주소 입력 : ");
		String doroAdd = ScanUtil.nextLine();
		Map<String, Object> param = new HashMap<String, Object>();

		List<Map<String, Object>> doro = memberDao.selectAdd(doroAdd);
		for(int i = 0; i < doro.size(); i++){
			System.out.println(i+1 + " " +doro.get(i).get("ADD_NAME"));
		}
		System.out.print("주소를 선택해주세요 : ");
		int select = ScanUtil.nextInt();
		param.put("MEM_ADD1", doro.get(select-1).get("ADD_NAME"));
		param.put("MEM_POST", doro.get(select-1).get("ADD_POST"));

		System.out.print("상세주소입력 : ");
		String add = ScanUtil.nextLine();

		param.put("MEM_ID", id);
		param.put("MEM_PASSWORD", password);
		param.put("MEM_NAME", name);
		param.put("MEM_GENDER", gender);
		param.put("MEM_PHONE", phone);
		param.put("MEM_EMAIL", email);
		param.put("MEM_ADD2", add);


		int result = memberDao.insert(param);

		if(0 < result){
			System.out.println("회원가입 성공");
		}else{
			System.out.println("회원가입 실패");
		}
		return View.HOME;
	}
	//아이디 정규식 체크
	private String inputId() {
		String id = "";
		while (true) {
			System.out.print("아이디 : ");
			id = ScanUtil.nextLine();
			// 정규식 체크
			boolean result = RegEx.regId(id);
			if (result == false) {
				System.out.println("아이디는 4~10자리여야 하며, 특수기호는 사용할수 없습니다.");
				continue;
			}else{
				List<Map<String, Object>> check = memberDao.checkId(id);
				if(check.size() > 0){
					System.out.println("중복된 아이디입니다");
					continue;
				}else{
					break;
				}
			}
		}
		return id;
	}
	//비밀번호 정규식 체크
	private String inputPw() {
		String pw = null;
		String pwCheck = null;
		while (true) {
			System.out.print("비밀번호 (4~10자리/영문 및 숫자만 입력하세요): ");
			pw = ScanUtil.nextLine();
			System.out.print("비밀번호확인 (4~10자리/영문 및 숫자만 입력하세요): ");
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
	//이름 정규식 체크
	private String inputName() {
		String name = null;
		while (true) {
			System.out.print("(2~5한글)이름 : ");
			name = ScanUtil.nextLine();
			// 정규식 체크
			boolean result = RegEx.regName(name);
			if (result == false) {
				System.out.println("이름은 2~5자리 이어야 합니다.");
				continue;
			} else {
				break;
			}
		}
		return name;
	}
	//성별 정규식
	private String inputGender(){
		String gender ="";
		while(true){
			System.out.print("성별선택 1.남\t2.여\n입력 : ");
			gender = ScanUtil.nextLine();
			if(gender.equals("1")){
				gender = "남";
				break;
			}else if(gender.equals("2")){
				gender = "여";
				break;
			}else{
				System.out.println("1 또는 2번을 입력해주세요");
			}

		}
		return gender;

	}
	//전화번호 정규식
	private String inputPhone() {
		String no = "";
		while(true){
			System.out.print("핸드폰번호입력 :");
			no = ScanUtil.nextLine();
			if(no.length()==11){
				break;
			}else{
				System.out.println("핸드폰번호 11자리를 입력해주세요");
			}

		}
		String phone = no.substring(0,3) + "-" + no.substring(3,7) + "-" + no.substring(7,11);
		return phone;
	}
	public int login() {
		System.out.println("==============로그인=============");
		System.out.print("아이디 : ");
		String id = ScanUtil.nextLine();
		System.out.print("비밀번호 : ");
		String password = ScanUtil.nextLine();

		Map<String, Object> member = memberDao.selectMember(id,password);
		if(member == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
			Controller.loginMember = member;
			return View.BOOKMAIN;
		}
		return 0;
	}
}
