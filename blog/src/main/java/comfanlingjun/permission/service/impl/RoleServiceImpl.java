package comfanlingjun.permission.service.impl;

import comfanlingjun.commons.dao.URoleMapper;
import comfanlingjun.commons.dao.URolePermissionMapper;
import comfanlingjun.commons.dao.UUserMapper;
import comfanlingjun.commons.model.URole;
import comfanlingjun.commons.utils.LoggerUtils;
import comfanlingjun.core.mybatis.BaseMybatisDao;
import comfanlingjun.core.mybatis.page.Pagination;
import comfanlingjun.permission.bo.RolePermissionAllocationBo;
import comfanlingjun.permission.service.RoleService;
import comfanlingjun.core.shiro.token.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RoleServiceImpl extends BaseMybatisDao<URoleMapper> implements RoleService {

	@Resource
	URoleMapper roleMapper;
	@Resource
	UUserMapper userMapper;
	@Resource
	URolePermissionMapper rolePermissionMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(URole record) {
		return roleMapper.insert(record);
	}

	@Override
	public int insertSelective(URole record) {
		return roleMapper.insertSelective(record);
	}

	@Override
	public URole selectByPrimaryKey(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(URole record) {
		return roleMapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(URole record) {
		return roleMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public Pagination<URole> findPage(Map<String, Object> resultMap,
									  Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

	@Override
	public Pagination<RolePermissionAllocationBo> findRoleAndPermissionPage(
			Map<String, Object> resultMap, Integer pageNo, Integer pageSize) {
		return super.findPage("findRoleAndPermission", "findCount", resultMap, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> deleteRoleById(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String resultMsg = "删除成功。";
			String[] idArray = new String[]{};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[]{ids};
			}

			c:
			for (String idx : idArray) {
				Long id = new Long(idx);
				if (new Long(1).equals(id)) {
					resultMsg = "操作成功，But'系统管理员不能删除。";
					continue c;
				} else {
					count += this.deleteByPrimaryKey(id);
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("resultMsg", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public Set<String> findRoleByUserId(Long userId) {
		return roleMapper.findRoleByUserId(userId);
	}

	@Override
	public List<URole> findNowAllPermission() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", TokenService.getUserId());
		return roleMapper.findNowAllPermission(map);
	}

	/**
	 * 每20分钟执行一次
	 */
	@Override
	public void initData() {
		roleMapper.initData();
	}

}