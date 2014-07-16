package com.webanalytics.web.dto;

import java.util.List;
import java.util.Map;

public class JsonResult {
	
	private String status;
	private List results;
	private Boolean success = true;
	private Map<String, String> errors;
	private String serverException;
	private Object result;
	
	
	

	public void setResult(Object result) {
		this.result = result;
	}

	public JsonResult(){
		
	}

	public JsonResult(Exception exception){
		this.success = false;
		this.serverException = exception.getMessage();
		
	}
	
	public JsonResult(Object singleResult){
		result = singleResult;
		status = "true";
	}
	
	
	
	public JsonResult(Map<String, String> errors ){
		this.errors = errors;
		status = "false";
		success = false;
		
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<? extends Object> getResults() {
		return results;
	}
	public void setResults(List<? extends Object> results) {
		this.results = results;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	public Object getResult() {
		return result;
	}

	public String getServerException() {
		return serverException;
	}

	public void setServerException(String serverException) {
		this.serverException = serverException;
	}
	
}
