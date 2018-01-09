package comfanlingjun.code.vo.permission;


import comfanlingjun.code.model.commons.UPermission;
import comfanlingjun.code.utils.StringUtils;

import java.io.Serializable;

/**
 * 权限选择
 */
public class UPermissionVO extends UPermission implements Serializable {

	private static final long serialVersionUID = 5595282310632066245L;

	//是否勾选
	private String marker;

	//urole Id
	private String roleId;

	public boolean isCheck() {
		return StringUtils.equals(roleId, marker);
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}