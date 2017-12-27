package comfanlingjun.core.tags;


import comfanlingjun.commons.utils.StringUtils;

import java.util.Map;

/**
 * 自定义标签的父类
 * <p>
 * 用于 APITemplateModel  Freemarker的自定义标签转换数据
 */
public abstract class SuperCustomTag {

	/**
	 * 本方法采用多态集成的方式，然后用父类接收，用父类调用子类的
	 */
	protected abstract Object result(Map params);

	/**
	 * 直接强转报错，需要用Object过度一下
	 */
	protected Long getLong(Map params, String key) {
		Object i = params.get(key);
		return StringUtils.isBlank(i) ? null : new Long(i.toString());
	}

	protected String getString(Map params, String key) {
		Object i = params.get(key);
		return StringUtils.isBlank(i) ? null : i.toString();
	}

	protected Integer getInt(Map params, String key) {
		Object i = params.get(key);
		return StringUtils.isBlank(i) ? null : Integer.parseInt(i.toString());
	}
}