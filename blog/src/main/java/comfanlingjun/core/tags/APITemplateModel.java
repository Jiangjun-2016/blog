package comfanlingjun.core.tags;

import comfanlingjun.code.utils.Constant;
import comfanlingjun.code.utils.LoggerUtils;
import comfanlingjun.code.utils.SpringContextUtil;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.HashMap;
import java.util.Map;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

/**
 * Freemarker 自定义标签 API公共入口
 */
public class APITemplateModel extends WYFTemplateModel {

	@Override
	protected Map<String, TemplateModel> putValue(Map params) throws TemplateModelException {
		Map<String, TemplateModel> paramWrap = null;
		if (null != params && params.size() != 0 || null != params.get(Constant.TARGET)) {
			String name = params.get(Constant.TARGET).toString();
			paramWrap = new HashMap<String, TemplateModel>(params);
			//获取子类，用父类接收，
			SuperCustomTag tag = SpringContextUtil.getBean(name, SuperCustomTag.class);
			//父类调用子类方法
			Object result = tag.result(params);
			//输出
			paramWrap.put(Constant.OUT_TAG_NAME, DEFAULT_WRAPPER.wrap(result));
		} else {
			LoggerUtils.error(getClass(), "Cannot be null, must include a 'name' attribute!");
		}
		return paramWrap;
	}
}