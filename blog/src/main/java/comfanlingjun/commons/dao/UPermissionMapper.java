package comfanlingjun.commons.dao;


import comfanlingjun.commons.model.UPermission;
import comfanlingjun.permission.vo.UPermissionVO;

import java.util.List;
import java.util.Set;

public interface UPermissionMapper {
	int deleteByPrimaryKey(Long id);

	int insert(UPermission record);

	int insertSelective(UPermission record);

	UPermission selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UPermission record);

	int updateByPrimaryKey(UPermission record);

	List<UPermissionVO> selectPermissionById(Long id);

	//根据用户ID获取权限的Set集合
	Set<String> findPermissionByUserId(Long id);
}