package com.example.springbootaopredis.util;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springbootaopredis.exception.CommonExcept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实体类反射处理工具类
 * @author yuming
 *
 */
@Slf4j
public class EntityReflectUtil {


	private static final String FS_ERROR_MSG = "反射异常";

	private EntityReflectUtil() {
		throw new IllegalStateException("Utility class");
	}


	/**
	 * 获取类的所有属性，包括父类
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] getAllFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		for(int i=0; i<fieldList.size(); i++) {
			if(fieldList.get(i).getName().equals("serialVersionUID")) {
				fieldList.remove(i);
				break;
			}
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}

	/**
	 * 获取实体类的所有字段，提高点效率，不取父类的字段了
	 * zgg 20191101更改
	 * @param clazz
	 * @return
	 */
	public static Field[] getEntityAllFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		if (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
		}
		for(int i=0; i<fieldList.size(); i++) {
			if(fieldList.get(i).getName().equals("serialVersionUID")) {
				fieldList.remove(i);
				break;
			}
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
	}
	
	/**
	 * 根据字段名称获取字段，可能是从当前类里面获取到，也可能从继承的父类里面获取到
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByName(Object obj, String fieldName) {
		Field field = null;
		Class<?> clazz = obj.getClass();
		while (clazz != null) {
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (Exception e) {
				log.error(e.getMessage() + " : ",e);
			}
			clazz = clazz.getSuperclass();
			if(field != null) {
				break;
			}
		}
		return field;
	}
	
	/**
	 * 根据字段名称获取字段的值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getValueFromFieldName(Object obj, String fieldName) {
		Field field = getFieldByName(obj, fieldName);
		return getValueFromField(obj, field);
	}
	
	/**
	 * 从实体类里面获取字段的值，相当于调用get或is方法
	 * @param obj
	 * @param field
	 * @return
	 */
	public static Object getValueFromField(Object obj, Field field) {
		if(obj==null||field==null){
			throw new CommonExcept("参数不能为空");
		}
		return getFiled(obj,field);
	}


	private static Object getFiled(Object obj, Field field) {
		try {
			return ReflectionUtils.getField(field, obj);
		} catch (IllegalStateException e) {
			//log.warn(FS_ERROR_MSG + ": {} ", e.getMessage());
			ReflectionUtils.makeAccessible(field);
		}
		try {
			return ReflectionUtils.getField(field, obj);
		} catch (Exception e) {
			//log.error(FS_ERROR_MSG + ":", e);
			throw new CommonExcept(FS_ERROR_MSG);
		}
	}

	/**
	 * 给实体类字段设置值，相当于调用set方法
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void setValueToField(Object obj, String fieldName, Object value) {
		if(obj == null) {
			return;
		}
		Field field = getFieldByName(obj, fieldName);
		if(field == null || value == null) {
			return;
		}
		setValueToField(obj, field, value);
	}
	
	/**
	 * 给实体类字段设置值，相当于调用set方法
	 * @param obj
	 * @param field
	 * @param value
	 */
	public static void setValueToField(Object obj, Field field, Object value) {
		if (StringUtils.isEmpty(field)) {
			throw new CommonExcept("字段不存在");
		}
		try {
			ReflectionUtils.setField(field, obj, value);
		}catch (IllegalStateException e){
			//log.warn(FS_ERROR_MSG + ": {} ", e.getMessage());
			ReflectionUtils.makeAccessible(field);
		}
		try {
			ReflectionUtils.setField(field, obj, value);
		}catch (Exception e){
			//log.error(FS_ERROR_MSG + ":",e);
			throw new CommonExcept(FS_ERROR_MSG);
		}
	}
	
	/**
	 * 获取实体类上的表名
	 * @param obj
	 * @return
	 */
	public static String getTableName(Object obj) {
		String tableName = "";
		Class<?> clazz = obj.getClass();
		while (clazz != null) {
			if(clazz.getAnnotation(TableName.class) != null) {
				tableName = clazz.getAnnotation(TableName.class).value();
				break;
			}
			clazz = clazz.getSuperclass();
		}
		return tableName;
	}
}
