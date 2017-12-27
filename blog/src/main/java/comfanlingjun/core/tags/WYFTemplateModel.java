package comfanlingjun.core.tags;

import comfanlingjun.core.freemarker.utils.FreemarkerTagUtil;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * Freemarker 模板
 * 基础标签类
 */
public abstract class WYFTemplateModel implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
						TemplateDirectiveBody body) throws TemplateException, IOException {
		//模版方法模式，把变化委派下去，交给子类实现！
		Map<String, TemplateModel> paramWrap = putValue(params);
		Map<String, TemplateModel> origMap = FreemarkerTagUtil.convertToTemplateModel(env, paramWrap);
		body.render(env.getOut());
		FreemarkerTagUtil.clearTempleModel(env, paramWrap, origMap);
	}

	/**
	 * 子类实现
	 */
	protected abstract Map<String, TemplateModel> putValue(Map params) throws TemplateModelException;
}
