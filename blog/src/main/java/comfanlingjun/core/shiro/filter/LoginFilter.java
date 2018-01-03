package comfanlingjun.core.shiro.filter;

import comfanlingjun.commons.model.UUser;
import comfanlingjun.commons.utils.LoggerUtils;
import comfanlingjun.core.shiro.token.TokenService;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 是否允许登录
 * 运行过滤器前在SampleRealm先进行认证
 * SampleRealm认证，会取出在登录时候生成的Token实体信息
 */
public class LoginFilter extends AccessControlFilter {

	/**
	 * 表示是否允许访问
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		//调用TokenManager类，用来获取Token实体
		UUser token = TokenService.getToken();
		// && isEnabled()
		if (null != token || isLoginRequest(request, response)) {
			return Boolean.TRUE;
		}
		// 是否Ajax请求,Ajax请求要单独处理,否则页面没有动静。
		if (ShiroFilterUtils.isAjax(request)) {
			Map<String, String> resultMap = new HashMap<String, String>();
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			resultMap.put("login_status", "300");
			resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE;
	}

	/**
	 * 表示当访问拒绝时是否已经处理了
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		//保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE;
	}
}