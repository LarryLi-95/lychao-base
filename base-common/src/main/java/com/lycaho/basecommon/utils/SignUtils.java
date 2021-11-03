/**
 * @author：lychao
 * @createTime：2013年11月16日
 */
package com.lycaho.basecommon.utils;

import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 签名操作工具集合。
 * 
 * @author lychao
 */
public class SignUtils {

	/***
	 * 签名
	 * @param clientId
	 * @param timestamp
	 * @param clientKey
	 * @param parameterMap
	 * @return
	 */
	public static  String sign(String clientId, String timestamp, String clientKey, Map<String, String[]> parameterMap){
		List<Map.Entry<String,String[]>> list = new ArrayList<Map.Entry<String,String[]>>(parameterMap.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String,String[]>>() {
			//升序排序
			public int compare(Map.Entry<String, String[]> o1,
							   Map.Entry<String, String[]> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		StringBuffer text = new StringBuffer();
		text.append("timestamp:").append(timestamp);
		//查询token信息
		if(StringUtils.isNotBlank(clientId)){
			text.append("clientId:").append(clientId);
			text.append("clientKey:").append(clientKey);
		}
		for(Map.Entry<String,String[]> mapping:list){
			text.append(mapping.getKey()).append(":");
			ArrayList<String> values = Lists.newArrayList(mapping.getValue());
			Collections.sort(values);
			for (String value : values) {
				if(StringUtils.isNotBlank(value)){
					text.append(value);
				}
			}
		}
		return DigestUtils.md5Hex(text.toString());
	}

	/**
	 * 按键值顺序拼接给定Map中指定键对应的所有非null值。
	 * 
	 * @param map 映射表
	 * @param keys 键值列表
	 * @return 指定键对应的所有非null值拼接成的字符串。
	 * 
	 * @author lychao
	 * @createTime 2013年11月16日
	 */
	public static String jointAllValue(Map<Object, Object> map, Object... keys) {
		StringBuilder text = new StringBuilder();
		if (ArrayUtils.isNotEmpty(keys)) {
			Object value;
			for (Object key : keys) {
				value = map.get(key);
				if (value != null) {
					text.append(value);
				}
			}
		}
		return text.toString();
	}

	/**
	 * 如果给定的值不是null值，则拼接到指定的StringBuilder对象之后。
	 * 
	 * @param text StringBuilder对象
	 * @param obj 要拼接的对象
	 * 
	 * @author lychao
	 * @createTime 2013年11月16日
	 */
	public static void appendIfNotNull(StringBuilder text, Object obj) {
		if (obj != null) {
			if (obj.getClass().isArray()) {
				Object[] array = (Object[]) obj;
				for (int i = 0; i < array.length; ++i) {
					text.append(array[i]);
				}
			} else {
				text.append(obj);
			}
		}
	}
}
