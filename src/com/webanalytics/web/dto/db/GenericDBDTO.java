package com.webanalytics.web.dto.db;

import com.webanalytics.web.dao.util.DataBinding;
import com.webanalytics.web.dao.util.Translator;

public class GenericDBDTO {

	private String string1;
	private String string2;
	private int intUse1;
	private int intUse2;

	public int getIntUse2() {
		return intUse2;
	}
	
	@DataBinding(columnName="intUse2", type=Translator.INTEGER)
	public void setIntUse2(int intUse2) {
		this.intUse2 = intUse2;
	}
	public int getIntUse1() {
		return intUse1;
	}
	@DataBinding(columnName="intUse1", type=Translator.INTEGER)
	public void setIntUse1(int intUse1) {
		this.intUse1 = intUse1;
	}
	
	public String getString1() {
		return string1;
	}
	@DataBinding(columnName="stringUse1", type=Translator.STRING)
	public void setString1(String string1) {
		this.string1 = string1;
	}
	
	public String getString2() {
		return string2;
	}
	@DataBinding(columnName="stringUse2", type=Translator.STRING)
	public void setString2(String string2) {
		this.string2 = string2;
	}
	
}
