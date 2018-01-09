package comfanlingjun.code.service.permission;

import comfanlingjun.code.model.commons.UPermission;
import comfanlingjun.core.mybatis.page.Pagination;
import comfanlingjun.code.vo.permission.UPermissionVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PermissionService {

	int deleteByPrimaryKey(Long id);

	UPermission insert(UPermission record);

	UPermission insertSelective(UPermission record);

	UPermission selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UPermission record);

	int updateByPrimaryKey(UPermission record);

	Map<String, Object> deletePermissionById(String ids);

	Pagination<UPermission> findPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize);

	List<UPermissionVO> selectPermissionById(Long id);

	Map<String, Object> addPermissionForRole(Long roleId, String ids);

	Map<String, Object> deleteByRids(String roleIds);

	//根据用户ID查询权限（permission），放入到Authorization里。
	Set<String> findPermissionByUserId(Long userId);
}