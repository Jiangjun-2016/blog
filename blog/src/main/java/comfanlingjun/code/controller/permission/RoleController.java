package comfanlingjun.code.controller.permission;

import comfanlingjun.code.controller.commons.BaseController;
import comfanlingjun.code.model.commons.URole;
import comfanlingjun.code.utils.LoggerUtils;
import comfanlingjun.core.mybatis.page.Pagination;
import comfanlingjun.code.service.permission.RoleService;
import comfanlingjun.code.utils.UserPwdUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("role")
public class RoleController extends BaseController {

	@Resource
	RoleService roleService;

	/**
	 * 角色列表
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(String findContent, ModelMap modelMap) {
		modelMap.put("findContent", findContent);
		Pagination<URole> role = roleService.findPage(modelMap, pageNo, pageSize);
		return new ModelAndView("role/index", "page", role);
	}

	/**
	 * 角色添加
	 */
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRole(URole role) {
		try {
			int count = roleService.insertSelective(role);
			resultMap.put("status", 200);
			resultMap.put("successCount", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加角色报错。source[%s]", role.toString());
		}
		return resultMap;
	}

	/**
	 * 删除角色，根据ID，但是删除角色的时候，需要查询是否有赋予给用户，如果有用户在使用，那么就不能删除。
	 */
	@RequestMapping(value = "deleteRoleById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleById(String ids) {
		return roleService.deleteRoleById(ids);
	}

	/**
	 * 我的权限页面
	 */
	@RequestMapping(value = "mypermission", method = RequestMethod.GET)
	public ModelAndView mypermission() {
		return new ModelAndView("permission/mypermission");
	}

	/**
	 * 我的权限 bootstrap tree data
	 */
	@RequestMapping(value = "getPermissionTree", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPermissionTree() {
		//查询我所有的角色 ---> 权限
		List<URole> roles = roleService.findNowAllPermission();
		//把查询出来的roles 转换成bootstarp 的 tree数据
		List<Map<String, Object>> userList = UserPwdUtil.toTreeData(roles);
		return userList;
	}
}