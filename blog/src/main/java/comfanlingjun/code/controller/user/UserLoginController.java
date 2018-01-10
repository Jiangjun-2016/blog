package comfanlingjun.code.controller.user;

import comfanlingjun.code.controller.commons.BaseController;
import comfanlingjun.code.model.commons.UUser;
import comfanlingjun.code.utils.LoggerUtils;
import comfanlingjun.code.utils.StringUtils;
import comfanlingjun.code.utils.VerifyCodeUtils;
import comfanlingjun.core.shiro.token.TokenService;
import comfanlingjun.code.utils.UserPwdUtil;
import comfanlingjun.code.service.user.UUserService;
import net.sf.json.JSONObject;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 用户登录相关，不需要做登录限制
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("u")
public class UserLoginController extends BaseController {

	@Resource
	UUserService userService;

	//登录页面跳转
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("/user/login");
	}

	//注册页面跳转
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView register() {
		return new ModelAndView("/user/register");
	}

	/**
	 * 注册 and 登录
	 *
	 * @param vcode  验证码
	 * @param entity UUser实体
	 */
	@RequestMapping(value = "subRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> subRegister(String vcode, UUser entity) {
		try {
			//验证码是否正确
			if (!VerifyCodeUtils.verifyCode(vcode)) {
				resultMap.put("message", "验证码不正确！");
				return resultMap;
			}
			//验证账号是否存在
			String email = entity.getEmail();
			UUser user = userService.findUserByEmail(email);
			if (null != user) {
				resultMap.put("message", "帐号|Email已经存在！");
				return resultMap;
			}
			//设置创建时间，最后登录时间
			Date date = new Date();
			entity.setCreateTime(date);
			entity.setLastLoginTime(date);
			//设置账号为：有效状态
			entity.setStatus(UUser._1);
			//密码md5加密
			entity = UserPwdUtil.md5Pswd(entity);
			//插入数据
			entity = userService.insert(entity);
			LoggerUtils.fmtDebug(getClass(), "注册插入完毕！", JSONObject.fromObject(entity).toString());
			//shiro操作
			entity = TokenService.login(entity, Boolean.TRUE);
			LoggerUtils.fmtDebug(getClass(), "注册后，登录完毕！", JSONObject.fromObject(entity).toString());
			resultMap.put("status", 200);
			resultMap.put("message", "注册成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "系统错误，请联系管理员。");
		}
		return resultMap;
	}

	/**
	 * 登录提交
	 *
	 * @param entity     登录的UUser
	 * @param rememberMe 是否记住
	 * @param request    用来获取登录之前Url地址，实现登录后跳转到之前的页面
	 */
	@RequestMapping(value = "submitLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitLogin(UUser entity, Boolean rememberMe, HttpServletRequest request) {
		try {
			//生成Token实体
			entity = TokenService.login(entity, rememberMe);
			// shiro 获取登录之前的地址
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = null;
			if (null != savedRequest) {
				url = savedRequest.getRequestUrl();
			}
			//平常用的获取上一个请求的方式，在Session不一致的情况下是获取不到的
			//String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
			LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]", url);
			//如果登录之前没有地址，那么就跳转到个人首页。
			if (StringUtils.isBlank(url)) {
				url = request.getContextPath() + "/user/index.shtml";
			}
			resultMap.put("status", 200);
			resultMap.put("message", "登录成功");
			resultMap.put("back_url", url);
		} catch (DisabledAccountException e) {
			resultMap.put("status", 500);
			resultMap.put("message", "帐号已经禁用。");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "帐号或密码错误。");
		}
		return resultMap;
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> logout() {
		try {
			TokenService.logout();
			resultMap.put("status", 200);
		} catch (Exception e) {
			resultMap.put("status", 500);
			logger.error("errorMessage:" + e.getMessage());
			LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s。", e.getMessage());
		}
		return resultMap;
	}
}