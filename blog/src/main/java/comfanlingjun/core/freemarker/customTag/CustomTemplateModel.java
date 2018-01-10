package comfanlingjun.core.freemarker.customTag;

import comfanlingjun.core.freemarker.utils.FreemarkerTagUtil;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * TemplateDirectiveModel接口是freemarker自定标签或者自定义指令的核心处理接口。
 * 通过实现该接口，用户可以自定义标签（指令）进行任意操作，、 任意文本写入模板的输出。
 * <p>
 * 实现TemplateMethodModel接口
 * <p>
 * FreeMarker自定义宏 后端宏
 * 重写方法execute()
 */
public abstract class CustomTemplateModel implements TemplateDirectiveModel {

	/**
	 * 当模板页面遇到用户自定义的标签指令时，该方法会被执行
	 *
	 * @param env      系统环境变量，通常用它来输出相关内容，如Writer out = env.getOut();
	 * @param params   自定义标签传过来的对象，其key=自定义标签的参数名,valuez值是TemplateModel类型，
	 *                 而TemplateModel是一个接口类型，通常我们都使用TemplateScalarModel接口替代它获取一个String值
	 * @param loopVars 循环替代变量
	 * @param body     用于处理自定义标签中的内容;当标签是<@myDirective />格式时，body=null
	 */
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
						TemplateDirectiveBody body) throws TemplateException, IOException {
		//模版方法 子类实现; 完成赋值：resultWrap.put(outTagName, 实际标签); outTagName：输出标签Name
		Map<String, TemplateModel> resultWrap = putValue(params);
		//完成env赋值：env.setVariable--paramWrap的值;实际就是转换模型
		Map<String, TemplateModel> origMap = FreemarkerTagUtil.convertToTemplateModel(env, resultWrap);
		body.render(env.getOut());
		//清除变量值
		FreemarkerTagUtil.clearTempleModel(env, resultWrap, origMap);
	}

	/**
	 * 子类实现
	 */
	protected abstract Map<String, TemplateModel> putValue(Map params) throws TemplateModelException;
}