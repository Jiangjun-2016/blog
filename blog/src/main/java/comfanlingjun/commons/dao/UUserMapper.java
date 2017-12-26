package comfanlingjun.commons.dao;

import comfanlingjun.commons.model.UUser;
import comfanlingjun.permission.bo.URoleBo;

import java.util.List;
import java.util.Map;

public interface UUserMapper {
	int deleteByPrimaryKey(Long id);

	int insert(UUser record);

	int insertSelective(UUser record);

	UUser selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UUser record);

	int updateByPrimaryKey(UUser record);

	UUser login(Map<String, Object> map);

	UUser findUserByEmail(String email);

	List<URoleBo> selectRoleByUserId(Long id);

}