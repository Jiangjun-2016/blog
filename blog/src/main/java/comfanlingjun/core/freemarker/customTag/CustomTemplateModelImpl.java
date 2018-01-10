package comfanlingjun.core.freemarker.customTag;

import comfanlingjun.code.utils.Constant;
import comfanlingjun.code.utils.LoggerUtils;
import comfanlingjun.code.utils.SpringContextUtil;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.HashMap;
import java.util.Map;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

/**
 * 自定义后端宏实现类
 */
public class CustomTemplateModelImpl extends CustomTemplateModel {

	/**
	 * 预处理模板
	 */
	@Override
	protected Map<String, TemplateModel> putValue(Map params) throws TemplateModelException {
		Map<String, TemplateModel> resultWrap = null;
		if (null != params && params.size() != 0 || null != params.get(Constant.TARGET)) {
			String name = params.get(Constant.TARGET).toString();
			resultWrap = new HashMap<String, TemplateModel>(params);
			CustomTag tag = SpringContextUtil.getBean(name, CustomTag.class);
			Object result = tag.result(params);
			resultWrap.put(Constant.OUT_TAG_NAME, DEFAULT_WRAPPER.wrap(result));
		} else {
			LoggerUtils.error(getClass(), "Cannot be null, must include a 'name' attribute!");
		}
		return resultWrap;
	}
}