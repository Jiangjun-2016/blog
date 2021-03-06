package comfanlingjun.code.controller.user;

import comfanlingjun.code.controller.commons.BaseController;
import comfanlingjun.code.model.commons.UUser;
import comfanlingjun.code.utils.LoggerUtils;
import comfanlingjun.core.shiro.token.TokenService;
import comfanlingjun.code.utils.UserPwdUtil;
import comfanlingjun.code.service.user.UUserService;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户管理
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("user")
public class UserCoreController extends BaseController {

	@Resource
	UUserService userService;

	/**
	 * 个人资料
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView userIndex() {
		return new ModelAndView("user/index");
	}


	/**
	 * 通用页面跳转
	 */
	@RequestMapping(value = "{page}", method = RequestMethod.GET)
	public ModelAndView toPage(@PathVariable("page") String page) {
		return new ModelAndView(String.format("user/%s", page));
	}

	/**
	 * 密码修改
	 */
	@RequestMapping(value = "updatePswd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePswd(String pswd, String newPswd) {
		//根据当前登录的用户帐号 + 老密码，查询。
		String email = TokenService.getUUserToken().getEmail();
		pswd = UserPwdUtil.md5Pswd(email, pswd);
		UUser user = userService.login(email, pswd);
		if ("admin".equals(email)) {
			resultMap.put("status", 300);
			resultMap.put("message", "管理员不准修改密码。");
			return resultMap;
		}
		if (null == user) {
			resultMap.put("status", 300);
			resultMap.put("message", "密码不正确！");
		} else {
			user.setPswd(newPswd);
			//加工密码
			user = UserPwdUtil.md5Pswd(user);
			//修改密码
			userService.updateByPrimaryKeySelective(user);
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
			//重新登录一次
			TokenService.login(user, Boolean.TRUE);
		}
		return resultMap;
	}

	/**
	 * 个人资料修改
	 */
	@RequestMapping(value = "updateSelf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSelf(UUser entity) {
		try {
			userService.updateByPrimaryKeySelective(entity);
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "修改失败!");
			LoggerUtils.fmtError(getClass(), e, "修改个人资料出错。[%s]", JSONObject.fromObject(entity).toString());
		}
		return resultMap;
	}
}