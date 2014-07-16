package com.webanalytics.web.worker;

import org.springframework.beans.factory.annotation.Autowired;

import com.webanalytics.web.dao.AppDataDao;
import com.webanalytics.web.dao.ReportDao;
import com.webanalytics.web.dao.admin.AdminDao;
import com.webanalytics.web.dto.AppData;
import com.webanalytics.web.dto.ChangePassword;
import com.webanalytics.web.dto.MemberProfile;
import com.webanalytics.web.dto.Registration;
import com.webanalytics.web.util.Cache;
import com.webanalytics.web.util.SecurityHolder;

public class AdminWorker {

	@Autowired
	AdminDao dao = null;
	@Autowired
	AppDataDao appDataDao = null;
	
	
	public void registerMember( Registration register){
		
		AppData appData = AppData.createNewAppData();
		appData.setRegisteredEmail(register.getEmail());
		appDataDao.insertAppData(appData);
		AppData dbAppData = appDataDao.getMemberAppData(register.getEmail());
		register.setAppId(dbAppData.getAppId());
		dao.insertMember(register);
		
	}
	
	public MemberProfile login( String emailId, String password){
		if( dao.login(emailId, password)){
			MemberProfile profile = dao.getMember(emailId);
			SecurityHolder.getCurrentSession().setAttribute(SecurityHolder.LOGGED_MEMBER, profile);
			Cache.createCache(appDataDao.getMemberAppData(profile.getAppId()));
			return profile;
		}
		return null;
	}
	
	public boolean updatePassword(String email, ChangePassword changePassword){
		boolean match =  dao.login(email, changePassword.getOldPassword());
		if( match ){
			dao.updatePassword(email, changePassword.getNewPassword());
			return true;
		}else{
			return false;
		}
		
	}
}
