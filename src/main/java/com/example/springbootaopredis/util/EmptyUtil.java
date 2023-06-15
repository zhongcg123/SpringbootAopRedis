package com.example.springbootaopredis.util;

import java.util.Collection;
import java.util.Map;

/**
 * 空判断工具类，返回true时为空，false为非空
 * @author yuming
 *
 */
public class EmptyUtil {
	
	/**
	 * 对象空判断，为空时返回true
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		if(object instanceof Collection) {	//List和Set
			if(((Collection)object).isEmpty()) {
				return true;
			}
		} else if(object instanceof Map) {	//Map
			if(((Map)object).isEmpty()) {
				return true;
			}
		} else if(object.getClass().isArray()) {	//数组
			if(((Object[]) object).length == 0) {
				return true;
			}
		} else {	//普通对象
			if (object.toString().trim().equals("")) {
				return true;
			}
			if (object.toString().trim().equals("null")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 对象非空判断，非空时返回true
	 * @param object
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Object object) {
		if(object == null) {
			return false;
		}
		if(object instanceof Collection) {	//List和Set
			if(!((Collection)object).isEmpty()) {
				return true;
			}
		} else if(object instanceof Map) {	//Map
			if(!((Map)object).isEmpty()) {
				return true;
			}
		} else if(object.getClass().isArray()) {	//数组
			if(((Object[]) object).length > 0) {
				return true;
			}
		} else {	//普通对象
			if (!object.toString().trim().equals("") && !object.toString().trim().equals("null")) {
				return true;
			}
		}
		return false;
	}

}
