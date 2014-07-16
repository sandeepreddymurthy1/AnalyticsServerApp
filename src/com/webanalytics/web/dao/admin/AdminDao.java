package com.webanalytics.web.dao.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webanalytics.web.dao.BaseDao;
import com.webanalytics.web.dto.MemberProfile;
import com.webanalytics.web.dto.Registration;
import com.webanalytics.web.dto.db.GenericDBDTO;
import com.webanalytics.web.util.JAXBContextHelper;

public class AdminDao extends BaseDao{

	public void insertMember( Registration registration){
		Map<String, Object> params = new HashMap<String, Object>();
		MemberProfile profile = new MemberProfile();
		profile.setCompanyName(registration.getCompanyName());
		profile.setEmail(registration.getEmail());
		profile.setPhoneNumber(registration.getPhoneNumber());
		profile.setFirstName(registration.getFirstName());
		profile.setLastName(registration.getLastName());
		params.put("email", registration.getEmail());
		params.put("xml", JAXBContextHelper.objectToXml(profile));
		params.put("password", registration.getPassword());
		params.put("appId", registration.getAppId());
		String sql = "INSERT INTO MemberInfo (email,password,appId,xml) VALUES (:email:,:password:,:appId:,:xml:)";
		executeNamedCallableStatement(sql, params);
	}
	
	public MemberProfile getMember( String emailId ){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", emailId);
		String query = "select id as intUse1, appId as intUse2, xml as stringUse1 from MemberInfo where email = :email:";
		List<GenericDBDTO> results = (List<GenericDBDTO>)executeNamedPreparedStatement(query, params,GenericDBDTO.class);
		MemberProfile profile = null;
		if( results.size() > 0 ){
			GenericDBDTO result = results.get(0);
			String str = result.getString1();
			profile = (MemberProfile)JAXBContextHelper.xmlToObject(str, MemberProfile.class);
			profile.setAppId(result.getIntUse2());
			profile.setMemberId(result.getIntUse1());
		}
		return profile;
	}
	
	public boolean login(String emailId, String password){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", emailId);
		params.put("password",password);
		String query = "select email as stringUse1 from MemberInfo where email = :email: and password = :password:";
		List<GenericDBDTO> results = executeNamedPreparedStatement(query, params,GenericDBDTO.class);
		if(results != null && results.size() > 0 ){
			return true;
		}
		return false;
	}
	
	public void updatePassword(String email, String password){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("password", password);
		String sql = "UPDATE MemberInfo SET password=:password: WHERE email=:email:";
		executeNamedCallableStatement(sql, params);
	}
	
	public void updateProfile(MemberProfile profile){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("xml", JAXBContextHelper.objectToXml(profile));
		params.put("id", profile.getMemberId());
		String sql = "UPDATE MemberInfo SET xml=:xml: WHERE id=:id:";
		executeNamedCallableStatement(sql, params);
	}
}
