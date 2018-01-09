package comfanlingjun.code.model.commons;

import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * 用户{@link UUser} 和角色 {@link URole} 中间表
 */
public class UUserRole implements Serializable {

	private static final long serialVersionUID = -5671629113606542404L;

	/**
	 * {@link UUser.id}
	 */
	private Long uid;

	/**
	 * {@link URole.id}
	 */
	private Long rid;

	public UUserRole(Long uid, Long rid) {
		this.uid = uid;
		this.rid = rid;
	}

	public UUserRole() {
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}