package comfanlingjun.code.utils;

import java.util.Calendar;

/**
 * 静态变量
 */
public interface Constant {

	//项目根路径
	static final String CONTEXT_PATH = "contextPath";

	//Freemarker 变量
	static final String TARGET = "target";//标签使用目标
	static final String OUT_TAG_NAME = "outTagName";//输出标签Name

	//常用变量
	static final String NAME = "name";
	static final String ID = "id";
	static final String TOKEN = "token";
	static final String LOING_USER = "loing_user";
	//Long
	static final Long ZERO = 0L;
	static final Long ONE = 1L;
	static final Long TWO = 2L;
	static final Long THREE = 3L;
	static final Long EIGHT = 8L;
	// String
	static final String S_ZERO = "0";
	static final String S_ONE = "1";
	static final String S_TOW = "2";
	static final String S_THREE = "3";
	// Integer
	static final Integer I_ZERO = 0;
	static final Integer I_ONE = 1;
	static final Integer I_TOW = 2;
	static final Integer I_THREE = 3;

	//cache变量
	static final String CACHE_NAME = "shiro_cache";
	static final String CACHE_MANAGER = "cacheManager";//cacheManager bean name

	//当前年份
	static final int NOW_YEAY = Calendar.getInstance().get(Calendar.YEAR);

	// 地址
	static final String DOMAIN_WWW = IConfig.get("domain.www");//前端域名
	static final String DOMAIN_CDN = IConfig.get("domain.cdn");//静态资源域名
	static String VERSION = String.valueOf(System.currentTimeMillis());//版本号，重启的时间

	//存储到缓存，标识用户的禁止状态，解决在线用户踢出的问题
	final static String EXECUTE_CHANGE_USER = "SOJSON_EXECUTE_CHANGE_USER";
}