package comfanlingjun.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Shiro token
 * 用户登录Controller第一步，生成Token实体
 * <p>
 * 如果用户登录 or 访问的页面需要进行过滤器验证
 * <p>
 * 则会在SampleRealm 认证方法doGetAuthenticationInfo  取出Token信息进行认证
 */
public class ShiroToken extends UsernamePasswordToken implements java.io.Serializable {

	private static final long serialVersionUID = -6451794657814516274L;

	//登录密码
	private String pswd;

	public ShiroToken(String username, String pswd) {
		super(username, pswd);
		this.pswd = pswd;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
}