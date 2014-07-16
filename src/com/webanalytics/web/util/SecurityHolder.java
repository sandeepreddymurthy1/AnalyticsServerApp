package com.webanalytics.web.util;

import javax.servlet.http.HttpSession;
import com.webanalytics.web.dto.MemberProfile;


public class SecurityHolder {

	public static final String LOGGED_MEMBER = "loggedMember";
	
	private static ThreadLocal<MemberProfile> currentSessionUser = new ThreadLocal<MemberProfile>();
	private static ThreadLocal<HttpSession> currentSession = new ThreadLocal<HttpSession>();

	
	public static MemberProfile getCurrentSessionUser() {
		return currentSessionUser.get();
	}

	public static void putCurrentSessionUser(MemberProfile sessionUser) {
		currentSessionUser.set(sessionUser);
	}

	public static void clearCurrentSessionUser() {
		currentSessionUser.set(null);
	}

	public static void putCurrentSession(HttpSession session) {
		currentSession.set(session);
	}

	public static void clearCurrentSession() {
		currentSession.set(null);
	}

	public static HttpSession getCurrentSession() {
		return currentSession.get();
	}

	public static void clearAll() {
		currentSession.set(null);
		currentSessionUser.set(null);
	}
	
	public static void setUserForSession(MemberProfile profile){
		currentSession.get().setAttribute(LOGGED_MEMBER,profile );
		currentSessionUser.set(profile);
	}



	
}
