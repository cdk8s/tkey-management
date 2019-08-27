package com.cdk8s.tkey.management.util;

import com.cdk8s.tkey.management.constant.GlobalVariable;

public final class GlobalVariableUtil {

	public static String getManagementClientTokenKey(String accessToken) {
		return GlobalVariable.MANAGEMENT_CLIENT_TOKEN_KEY_PREFIX + accessToken;
	}
}
