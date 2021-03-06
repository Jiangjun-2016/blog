package comfanlingjun.code.service.permission;

import comfanlingjun.code.model.commons.URole;
import comfanlingjun.core.mybatis.page.Pagination;
import comfanlingjun.code.vo.permission.RolePermissionAllocationVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService {

	int deleteByPrimaryKey(Long id);

	int insert(URole record);

	int insertSelective(URole record);

	URole selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(URole record);

	int updateByPrimaryKey(URole record);

	Pagination<URole> findPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize);

	Map<String, Object> deleteRoleById(String ids);

	Pagination<RolePermissionAllocationVO> findRoleAndPermissionPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize);

	//根据用户ID查询角色（urole），放入到Authorization里。
	Set<String> findRoleByUserId(Long userId);

	List<URole> findNowAllPermission();

	//初始化数据
	void initData();
}