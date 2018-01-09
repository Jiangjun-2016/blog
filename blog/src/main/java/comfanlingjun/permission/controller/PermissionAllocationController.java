package comfanlingjun.permission.controller;

import comfanlingjun.commons.controller.BaseController;
import comfanlingjun.core.mybatis.page.Pagination;
import comfanlingjun.permission.vo.RolePermissionAllocationVO;
import comfanlingjun.permission.vo.UPermissionVO;
import comfanlingjun.permission.service.PermissionService;
import comfanlingjun.permission.service.RoleService;
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
 * Permission 权限 许可
 * Allocation 分配
 * <p>
 * 权限分配
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("permission")
public class PermissionAllocationController extends BaseController {

	@Resource
	PermissionService permissionService;
	@Resource
	RoleService roleService;

	/**
	 * 权限分配
	 */
	@RequestMapping(value = "allocation")
	public ModelAndView allocation(ModelMap modelMap, Integer pageNo, String findContent) {
		modelMap.put("findContent", findContent);
		Pagination<RolePermissionAllocationVO> rolePermissionAllocationVO = roleService.findRoleAndPermissionPage(modelMap, pageNo, pageSize);
		modelMap.put("page", rolePermissionAllocationVO);
		return new ModelAndView("permission/allocation");
	}

	/**
	 * 根据角色ID查询权限
	 */
	@RequestMapping(value = "selectPermissionById")
	@ResponseBody
	public List<UPermissionVO> selectPermissionById(Long id) {
		List<UPermissionVO> permissionVO = permissionService.selectPermissionById(id);
		return permissionVO;
	}

	/**
	 * 操作角色的权限
	 *
	 * @param roleId 角色ID
	 * @param ids    权限ID，以‘,’间隔
	 */
	@RequestMapping(value = "addPermissionForRole")
	@ResponseBody
	public Map<String, Object> addPermissionForRole(Long roleId, String ids) {
		return permissionService.addPermissionForRole(roleId, ids);
	}

	/**
	 * 根据角色id清空权限。
	 *
	 * @param roleIds 角色ID ，以‘,’间隔
	 */
	@RequestMapping(value = "clearPermissionByRoleIds")
	@ResponseBody
	public Map<String, Object> clearPermissionByRoleIds(String roleIds) {
		return permissionService.deleteByRids(roleIds);
	}
}