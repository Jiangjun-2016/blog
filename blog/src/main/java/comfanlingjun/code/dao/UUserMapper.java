package comfanlingjun.code.dao;

import comfanlingjun.code.model.commons.UUser;
import comfanlingjun.code.vo.permission.URoleVO;

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

	List<URoleVO> selectRoleByUserId(Long id);

}