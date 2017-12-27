package comfanlingjun.core.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色判断校验
 */
public class RoleFilter extends AccessControlFilter {

	public static final String LOGIN_URL = "http://fanlingjun/user/open/toLogin.shtml";
	public static final String UNAUTHORIZED_URL = "http://fanlingjun/unauthorized.html";

	/**
	 * 表示是否允许访问
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		String[] arra = (String[]) mappedValue;
		Subject subject = getSubject(request, response);
		for (String role : arra) {
			if (subject.hasRole("role:" + role)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 表示当访问拒绝时是否已经处理了
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		//表示没有登录，重定向到登录页面
		if (subject.getPrincipal() == null) {
			saveRequest(request);
			WebUtils.issueRedirect(request, response, LOGIN_URL);
		} else {
			//如果有未授权页面跳转过去
			if (StringUtils.hasText(UNAUTHORIZED_URL)) {
				WebUtils.issueRedirect(request, response, UNAUTHORIZED_URL);
			} else {//否则返回401未授权状态码
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		return false;
	}
}