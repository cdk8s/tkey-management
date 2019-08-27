package com.cdk8s.tkey.management.util;

import org.apache.commons.lang3.StringUtils;


public final class StringUtil {


	//=====================================Apache Common 包 start=====================================

	public static boolean isNotBlank(final String str) {
		return StringUtils.isNotBlank(str);
	}

	public static boolean isBlank(final String str) {
		return StringUtils.isBlank(str);
	}

	public static boolean equalsIgnoreCase(final String str1, final String str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	public static boolean endsWithAny(final CharSequence sequence, final CharSequence... searchStrings) {
		return StringUtils.endsWithAny(sequence, searchStrings);
	}

	//=====================================Apache Common 包  end=====================================

	//=====================================其他包 start=====================================


	//=====================================其他包  end=====================================


	//=====================================私有方法 start=====================================

	//=====================================私有方法  end=====================================

}



