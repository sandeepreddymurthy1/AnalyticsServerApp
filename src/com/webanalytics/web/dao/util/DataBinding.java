/**
 * Copyright (c) 2009 - 2010, FedEx.
 *
 * $Id: DataBinding.java 3457 2010-05-27 15:51:01Z indra.varakantham.osv $
 *
 * Description:
 * 
 * Story: 
 *
 * Defect:
 *
*/
package com.webanalytics.web.dao.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataBinding {

	// Column name to be binded
	String columnName();

	// Column type to be binded
	String type();

}
