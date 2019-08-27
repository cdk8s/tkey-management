package com.cdk8s.tkey.management.constant;


public interface GlobalVariable {

	//=====================================LOG start=====================================

	// LOG 严重性能问题的时间阀值(毫秒)
	int SERIOUS_PERFORMANCE_PROBLEMS_TIME_THRESHOLD = 4000;

	// LOG 一般性能问题的时间阀值(毫秒)
	int GENERAL_PERFORMANCE_PROBLEMS_TIME_THRESHOLD = 3000;

	// LOG 修改优化的时间阀值(毫秒)
	int NEED_OPTIMIZE_TIME_THRESHOLD = 2000;

	//=====================================LOG  end=====================================

	String REDIS_CLIENT_ID_PREFIX = "CID-";
	String HEADER_TOKEN_KEY = "x-token";
	String MANAGEMENT_CLIENT_TOKEN_KEY_PREFIX = "MANAGEMENT_CLIENT_";

}
