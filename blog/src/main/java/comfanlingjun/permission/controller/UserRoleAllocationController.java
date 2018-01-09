package comfanlingjun.permission.controller;

import comfanlingjun.commons.controller.BaseController;
import comfanlingjun.core.mybatis.page.Pagination;
import comfanlingjun.permission.vo.URoleVO;
import comfanlingjun.permission.vo.UserRoleAllocationVO;
import comfanlingjun.permission.service.PermissionService;
import comfanlingjun.user.service.UUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色分配
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("role")
public class UserRoleAllocationController extends BaseController {

	@Resource
	UUserService userService;
	@Resource
	PermissionService permissionService;

	/**
	 * 用户角色权限分配
	 */
	@RequestMapping(value = "allocation")
	public ModelAndView allocation(ModelMap modelMap, Integer pageNo, String findContent) {
		modelMap.put("findContent", findContent);
		Pagination<UserRoleAllocationVO> userRoleAllocationVO = userService.findUserAndRole(modelMap, pageNo, pageSize);
		modelMap.put("page", userRoleAllocationVO);
		return new ModelAndView("role/allocation");
	}

	/**
	 * 根据用户ID查询权限
	 */
	@RequestMapping(value = "selectRoleByUserId")
	@ResponseBody
	public List<URoleVO> selectRoleByUserId(Long id) {
		List<URoleVO> roleList = userService.selectRoleByUserId(id);
		return roleList;
	}

	/**
	 * 操作用户的角色
	 *
	 * @param userId 用户ID
	 * @param ids    角色ID，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value = "addRoleForUser")
	@ResponseBody
	public Map<String, Object> addRoleForUser(Long userId, String ids) {
		return userService.addRoleForUser(userId, ids);
	}

	/**
	 * 根据用户id清空角色。
	 *
	 * @param userIds 用户ID ，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value = "clearRoleByUserIds")
	@ResponseBody
	public Map<String, Object> clearRoleByUserIds(String userIds) {
		return userService.deleteRoleByUserIds(userIds);
	}
}