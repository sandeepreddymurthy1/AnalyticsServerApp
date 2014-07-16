/**
 * Copyright (c) 2009 - 2010, FedEx.
 *
 * $Id: Translator.java 8813 2011-06-20 15:34:17Z kal $
 *
 * Description:
 *
 * Story:
 *
 * Defect:
 */
package com.webanalytics.web.dao.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Translator {

	public static final String BOOLEAN = "boolean";
	public static final String STRING = "string";
	public static final String DATE = "date";
	public static final String TIMESTAMP = "timestamp";
	public static final String DOUBLE = "double";
	public static final String INTEGER = "integer";
	public static final String LONG = "long";
	public static final String BLOB = "blob";
	public static final String CLOB = "clob";
	// Used by annotation to restrict the number of results from the convert
	// method
	public static ThreadLocal<Integer> restrictResults = new ThreadLocal<Integer>() {
		// return null if no restrictions
		protected synchronized Integer initialValue() {
			return null;
		}
	};
	// Cache all the methods at the first time for performance
	private static Map<Class<? extends Object>, List<Method>> cachedMethods =
			new HashMap<Class<? extends Object>, List<Method>>();
	private static Map<Class<?>, String> typeMap = new HashMap<Class<?>, String>();
	private static Map<String, String> typeMapAlias = new HashMap<String, String>();
	static {
		// Add Primitive types
		typeMap.put(boolean.class, BOOLEAN);
		typeMap.put(double.class, DOUBLE);
		typeMap.put(int.class, INTEGER);
		typeMap.put(long.class, LONG);

		// Add Objects
		typeMap.put(String.class, STRING);
		typeMap.put(Date.class, DATE);
		typeMap.put(java.util.Date.class, TIMESTAMP + "," + DATE);
		typeMap.put(Timestamp.class, TIMESTAMP);
		typeMap.put(Boolean.class, BOOLEAN);
		typeMap.put(Double.class, DOUBLE);
		typeMap.put(Integer.class, INTEGER);
		typeMap.put(Long.class, LONG);
		typeMap.put(byte[].class, BLOB);
		// typeMapAlias.put(BLOB, STRING);
		typeMapAlias.put(CLOB, STRING);

	}

	/**
	 * Convert.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param rs
	 *            the rs
	 * @param cl
	 *            the cl
	 * @return the list
	 */
	public static <T> List<T> convert(ResultSet rs, Class<T> cl) {
		List<T> result = new ArrayList<T>();
		Method m = null;
		try {
			List<Method> methods = cachedMethods.get(cl);
			if (methods == null) {
				// Cache only methods with Binding Annotation which reduced
				// method lookups
				Method[] allMethods = cl.getMethods();

				methods = new ArrayList<Method>();
				for (Method method : allMethods) {
					if (method.isAnnotationPresent(DataBinding.class)) {
						// Validate the binding class with the method signature
						DataBinding binding = method.getAnnotation(DataBinding.class);
						String type = binding.type();
						if (checkType(type, method)) {
							methods.add(method);
						}
						else {
							throw new RuntimeException(method + "");
						}
					}
				}
				cachedMethods.put(cl, methods);
			}

			while (rs.next()) {
				T obj = cl.newInstance();
				for (Method method : methods) {
					m = method;
					DataBinding binding = method.getAnnotation(DataBinding.class);
					Object[] args = new Object[1];
					args[0] = getValue(binding.columnName(), binding.type(), rs, method, cl);
					method.invoke(obj, args);
				}
				result.add(obj);
				// handle max results annotations (will be null if no
				// restriction)
				Integer restrict = restrictResults.get();
				if (restrict != null && result.size() >= restrict.intValue()) {
					break;
				}
			}

		}
		catch (InstantiationException e) {
			throw new RuntimeException("Runtime Exception", e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException("Runtime Exception", e);
		}
		catch (InvocationTargetException e) {
			throw new RuntimeException("Runtime Exception", e);
		}
		catch (IllegalArgumentException e) {
			throw new RuntimeException("Runtime Exception" , e);
		}
		catch (SQLException e) {
			throw new RuntimeException("Runtime Exception", e);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * Check type.
	 * 
	 * @param type
	 *            the type
	 * @param method
	 *            the method
	 * @return true, if successful
	 */
	private static boolean checkType(String type, Method method) {
		Class<?>[] params = method.getParameterTypes();
		if (typeMapAlias.get(type) != null) {
			type = typeMapAlias.get(type);
		}
		if (params.length == 0) {
			return false;
		}
		Class<?> methodClass = params[0];
		if (typeMap.containsKey(methodClass) && typeMap.get(methodClass).contains(type)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the value.
	 * 
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @param rs
	 *            the rs
	 * @param method
	 *            the method
	 * @param cl
	 *            the cl
	 * @return the value
	 */
	private static Object getValue(String name, String type, ResultSet rs, Method method, Class<?> cl) {

		Object result = null;
		int clmIndex = 0;

		try {
			// If the column name is not there just return null.
			// All the annotation present on the class need not be on the result
			// set.
			// Same class can be used for various queries.
			clmIndex = rs.findColumn(name);
		}
		catch (SQLException e) {
			if (BOOLEAN.equalsIgnoreCase(type)) {
				return false;
			}
			else if (DOUBLE.equalsIgnoreCase(type)) {
				return (double) 0;
			}
			else if (INTEGER.equalsIgnoreCase(type)) {
				return 0;
			}
			else if (LONG.equalsIgnoreCase(type)) {
				return (long) 0;
			}
			else if (STRING.equalsIgnoreCase(type) || DATE.equalsIgnoreCase(type) || TIMESTAMP.equalsIgnoreCase(type)
					|| BLOB.equalsIgnoreCase(type) || CLOB.equalsIgnoreCase(type)) {
				return null;
			}
			else {
				return null;
			}
		}

		try {
			if (BOOLEAN.equalsIgnoreCase(type)) {
				result = rs.getBoolean(clmIndex);
			}
			else if (DOUBLE.equalsIgnoreCase(type)) {
				result = rs.getDouble(clmIndex);
			}
			else if (INTEGER.equalsIgnoreCase(type)) {
				result = rs.getInt(clmIndex);
			}
			else if (STRING.equalsIgnoreCase(type)) {
				result = rs.getString(clmIndex) == null ? "" : rs.getString(clmIndex);
			}
			else if (LONG.equalsIgnoreCase(type)) {
				result = rs.getLong(clmIndex);
			}
			else if (DATE.equalsIgnoreCase(type)) {
				result = rs.getDate(clmIndex);
			}
			else if (TIMESTAMP.equalsIgnoreCase(type)) {
				result = rs.getTimestamp(clmIndex);
			}
			else if (BLOB.equalsIgnoreCase(type)) {
				if (rs.getBlob(clmIndex) == null) {
					result = null;
				}
				else {
					InputStream stream = null;
					try {
						stream = rs.getBlob(clmIndex).getBinaryStream();
						byte[] array = new byte[1024 * 1024];
						ByteArrayOutputStream dataOs = new ByteArrayOutputStream();
						int len = stream.read(array);
						while (len > 0) {
							dataOs.write(array, 0, len);
							len = stream.read(array);
						}
						result = dataOs.toByteArray();

					}
					catch (IOException e) {
						throw new RuntimeException("While Setting the property" + method.getName()
								+ " on class " + cl.getName(), e);
					}
					finally {
						if (stream != null)
							try {
								stream.close();
							}
							catch (IOException e) {
								logger.warn("IO Exception", e);
							}
					}
				}

			}
			else if (CLOB.equalsIgnoreCase(type)) {
				Clob clob = rs.getClob(clmIndex);
				if (clob == null) {
					result = null;
				}
				else {
					long i = 1;
					int clobLength = (int) clob.length();
					result = clob.getSubString(i, clobLength);
					clob.free();

				}
			}

		}
		catch (SQLException e) {
			throw new RuntimeException("While setting the property for method " + method.getName() + " on class "
					+ cl.getName(), e);
		}

		return result;
	}

	private static final Logger logger = Logger.getLogger(Translator.class.getName());
}
