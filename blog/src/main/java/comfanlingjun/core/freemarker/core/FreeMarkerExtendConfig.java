package comfanlingjun.core.freemarker.core;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * 装载配置freeMarker
 */
public class FreeMarkerExtendConfig extends FreeMarkerConfigurer {

	/**
	 * 加载shiro标签
	 */
	@Override
	public void afterPropertiesSet() throws IOException, TemplateException {
		super.afterPropertiesSet();
		Configuration cfg = this.getConfiguration();
		putShiroTag(cfg);
	}

	public static void putShiroTag(Configuration cfg) throws TemplateModelException {
		cfg.setSharedVariable("shiro", new ShiroTags());
		cfg.setNumberFormat("#");//防止页面输出数字,变成2,000
	}
}