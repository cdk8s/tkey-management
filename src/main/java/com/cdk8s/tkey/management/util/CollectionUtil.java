package com.cdk8s.tkey.management.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类
 */
public final class CollectionUtil {


	//=====================================Apache Common 包 start=====================================


	public static boolean isNotEmpty(final Collection coll) {
		return CollectionUtils.isNotEmpty(coll);
	}

	public static boolean isEmpty(final Collection coll) {
		return CollectionUtils.isEmpty(coll);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return MapUtils.isEmpty(map);
	}

	//=====================================Apache Common 包  end=====================================

	//=====================================Guava 包  end=====================================

	//=====================================Guava 包  end=====================================

	//=====================================其他包 start=====================================


	//=====================================其他包  end=====================================


	//=====================================私有方法 start=====================================

	/**
	 * 数组转换成 List
	 */
	public static <T> List<T> toList(T[] arrays) {
		List<T> list = new ArrayList<>();
		CollectionUtils.addAll(list, arrays);
		return list;
	}

	//=====================================私有方法  end=====================================

}



