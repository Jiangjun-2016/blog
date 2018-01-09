package comfanlingjun.user.service;

import comfanlingjun.commons.model.UUser;
import comfanlingjun.core.mybatis.page.Pagination;
import comfanlingjun.permission.vo.URoleVO;
import comfanlingjun.permission.vo.UserRoleAllocationVO;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Map;

public interface UUserService {

	int deleteByPrimaryKey(Long id);

	UUser insert(UUser record);

	UUser insertSelective(UUser record);

	UUser selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UUser record);

	int updateByPrimaryKey(UUser record);

	UUser login(String email, String pswd);

	UUser findUserByEmail(String email);

	Pagination<UUser> findByPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize);

	Map<String, Object> deleteUserById(String ids);

	Map<String, Object> updateForbidUserById(Long id, Long status);

	Pagination<UserRoleAllocationVO> findUserAndRole(ModelMap modelMap, Integer pageNo, Integer pageSize);

	List<URoleVO> selectRoleByUserId(Long id);

	Map<String, Object> addRoleForUser(Long userId, String ids);

	Map<String, Object> deleteRoleByUserIds(String userIds);
}