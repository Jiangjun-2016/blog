package comfanlingjun.core.freemarker.core;

import comfanlingjun.code.model.commons.UUser;
import comfanlingjun.code.utils.Constant;
import comfanlingjun.code.utils.LoggerUtils;
import comfanlingjun.core.freemarker.utils.FerrmarkerExtendUtil;
import comfanlingjun.core.shiro.token.TokenService;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 统一页面标签赋值
 * <p>
 * 通过重写exposeHelpers方法
 * 在spring里配置自己的freemarker的视图解析器，
 * 在模板中就可以通过${basePath}获取。
 */
public class FreeMarkerExtendView extends FreeMarkerView {

	/**
	 * 页面赋值
	 */
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) {
		try {
			super.exposeHelpers(model, request);
		} catch (Exception e) {
			LoggerUtils.fmtError(FreeMarkerExtendView.class, e, "FreeMarkerExtendView 加载父类出现异常。请检查。");
		}
		model.put(Constant.CONTEXT_PATH, request.getContextPath());
		model.putAll(FerrmarkerExtendUtil.initMap);
		UUser token = TokenService.getUUserToken();
		if (TokenService.isLogin()) {
			model.put("token", token);//登录的token
		}
		model.put("_time", new Date().getTime());
		model.put("NOW_YEAY", Constant.NOW_YEAY);//今年
		model.put("_v", Constant.VERSION);//版本号，重启的时间
		model.put("cdn", Constant.DOMAIN_CDN);//CDN域名
		model.put("basePath", request.getContextPath());//项目全局绝对路劲
	}
}