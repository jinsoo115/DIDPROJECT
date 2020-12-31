package service;

import java.util.regex.Pattern;

public class RegEx {
	/**
	 * 타이틀 정규식
	 * @param title
	 * @return
	 */
	public static boolean regtitle(String title){
		String str = "[가-힣A-Za-z0-9]{2,5}";
		boolean result = Pattern.matches(str, title);
		return result;
	}
	/**
	 * 비밀번호 정규식 체크
	 * @param mem_pw
	 * @return
	 */
	public static boolean regPw(String mem_pw) {
		String str = "[A-Za-z0-9]{4,10}";
		boolean result = Pattern.matches(str, mem_pw);
		return result;
	}
	
	/**
	 * 아이디 정규식 체크
	 * @param mem_id
	 * @return
	 */
	public static boolean regId(String mem_id) {
		String str = "[A-Za-z0-9]{4,10}";
		boolean result = Pattern.matches(str, mem_id);
		return result;
	}
	/**
	 * 이름 정규식
	 * @param mem_name
	 * @return
	 */
	public static boolean regName(String mem_name) {
		String str = "[가-힣]{2,5}";
		boolean result = Pattern.matches(str, mem_name);
		return result;
	}
}
