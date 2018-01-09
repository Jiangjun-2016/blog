package comfanlingjun.user.controller;

import comfanlingjun.commons.controller.BaseController;
import comfanlingjun.commons.model.UUser;
import comfanlingjun.core.mybatis.page.Pagination;
import comfanlingjun.core.shiro.session.core.CustomShiroSessionService;
import comfanlingjun.user.vo.UserOnlineVO;
import comfanlingjun.user.service.UUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户会员管理
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("member")
public class MemberController extends BaseController {

	//用户手动操作Session
	@Resource
	CustomShiroSessionService customShiroSessionService;
	@Resource
	UUserService userService;

	/**
	 * 用户列表管理
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(ModelMap map, Integer pageNo, String findContent) {
		map.put("findContent", findContent);
		Pagination<UUser> page = userService.findByPage(map, pageNo, pageSize);
		map.put("page", page);
		return new ModelAndView("member/list");
	}

	/**
	 * 在线用户管理
	 */
	@RequestMapping(value = "online")
	public ModelAndView online() {
		List<UserOnlineVO> list = customShiroSessionService.getAllUser();
		return new ModelAndView("member/online", "list", list);
	}

	/**
	 * 在线用户详情
	 */
	@RequestMapping(value = "onlineDetails/{sessionId}", method = RequestMethod.GET)
	public ModelAndView onlineDetails(@PathVariable("sessionId") String sessionId) {
		UserOnlineVO bo = customShiroSessionService.getSession(sessionId);
		return new ModelAndView("member/onlineDetails", "bo", bo);
	}

	/**
	 * 改变Session状态
	 */
	@RequestMapping(value = "changeSessionStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeSessionStatus(Boolean status, String sessionIds) {
		return customShiroSessionService.changeSessionStatus(status, sessionIds);
	}

	/**
	 * 根据ID删除，
	 *
	 * @param ids 如果有多个，以“,”间隔。
	 */
	@RequestMapping(value = "deleteUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserById(String ids) {
		return userService.deleteUserById(ids);
	}

	/**
	 * 禁止登录
	 *
	 * @param id     用户ID
	 * @param status 1:有效，0:禁止登录
	 */
	@RequestMapping(value = "forbidUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> forbidUserById(Long id, Long status) {
		return userService.updateForbidUserById(id, status);
	}

}