package com.example.springbootaopredis.util;



import com.example.springbootaopredis.exception.CommonExcept;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用类型转换工具
 * @author yuming
 *
 */
public class DataChangeUtil {

	/**
	 * 获取list字段的class
	 * @param field
	 * @return
	 */
	private static Class<?> obvListFieldClass(Field field) {
		Type genericType = field.getGenericType();
		if(genericType == null) {
			return null;
		}
		if(genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			Class<?> clazz = (Class<?>) pt.getActualTypeArguments()[0];
			return clazz;
		}
		return null;
	}

	/**
	 * 给对象字段赋值，因为需要抛出异常，不能使用EntityReflectUtil里面的方法
	 * @param obj
	 * @param field
	 * @param value
	 * @throws Exception
	 */
	private static void setValueToField(Object obj, Field field, Object value) throws Exception {
		String name = field.getName();
        name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
        Method method = obj.getClass().getMethod("set" + name, field.getType());
        if(field.getType().toString().indexOf("java.lang.") >= 0) {	//增加基础类型数据互转功能
        	try {
        		method.invoke(obj, value);
			} catch (Exception e) {
				try {
					method.invoke(obj, field.getType().getConstructor(field.getType()).newInstance(value));
				} catch (Exception e2) {
					try {
						method.invoke(obj, field.getType().getConstructor(String.class).newInstance(value.toString()));
					} catch (Exception e3) {
						throw new CommonExcept("类型转换不了");
					}
				}
			}
        } else {
        	method.invoke(obj, value);
        }
	}

	/**
	 * 实体类通用类型转换
	 * 注意：暂时不支持带泛型对象，泛型对象太难调了，暂时没空调
	 * 字段支持：基础字段、对象、List，暂时不支持Map、Set等
	 * @param <SRC>		数据来源类型
	 * @param <DEST>	转换结果类型
	 * @param src		数据来源
	 * @param descClass		转换结果
	 */
	public static <SRC,DEST> DEST changeData(SRC src, Class<DEST> descClass) {
		if(EmptyUtil.isEmpty(src)) {
			return null;
		}
		DEST desc = null;
		try {
			desc = descClass.newInstance();
		} catch (Exception e) {

		}
		Field[] srcFields = EntityReflectUtil.getAllFields(src.getClass());
		Field[] descFields = EntityReflectUtil.getAllFields(desc.getClass());
		for(int i=0; i<srcFields.length; i++) {
			Field srcField = srcFields[i];

			for(int j=0; j<descFields.length; j++) {
				Field descField = descFields[j];
				if(srcField.getName().equals(descField.getName())) {	//字段名称一样
					Object srcFieldValue = EntityReflectUtil.getValueFromField(src, srcField);
					try {	//先尝试通过java反射直接塞值进去，类型一样的能成功
						setValueToField(desc, descField, srcFieldValue);
					} catch (Exception e) {
						if(srcField.getType() == List.class && descField.getType() == List.class) {		//字段为list
							Class<?> descFieldClass = obvListFieldClass(descField);
							List<?> srcFieldListValue = (List<?>)srcFieldValue;
							List<?> descFieldListValue = changeList(srcFieldListValue, descFieldClass);
							try {
								setValueToField(desc, descField, descFieldListValue);
							} catch (Exception e1) {

							}
						} else {
							try {
								Object descFieldValue = changeData(srcFieldValue, descField.getType());
								setValueToField(desc, descField, descFieldValue);
							} catch (Exception e1) {

							}
						}
					}
					break;
				}
			}
		}
		return desc;
	}

	/**
	 * 转换list数据
	 * 注意：暂时不支持带泛型对象，泛型对象太难调了，暂时没空调
	 * 字段支持：基础字段、对象、List，暂时不支持Map、Set等
	 * @param <DEST>
	 * @param srcList
	 * @param descClass
	 * @return
	 */
	public static <DEST> List<DEST> changeList(List<?> srcList, Class<DEST> descClass) {
		List<DEST> destList = new ArrayList<DEST>();
		if(EmptyUtil.isEmpty(srcList)) {
			return destList;
		}
		for(int i=0; i<srcList.size(); i++) {
			DEST desc = changeData(srcList.get(i), descClass);
			destList.add(desc);
		}
		return destList;
	}

}
