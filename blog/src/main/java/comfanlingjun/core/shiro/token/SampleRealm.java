package comfanlingjun.core.shiro.token;

import comfanlingjun.commons.model.UUser;
import comfanlingjun.permission.service.PermissionService;
import comfanlingjun.permission.service.RoleService;
import comfanlingjun.user.service.UUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;


/**
 * shiro 认证 + 授权   重写
 * 登录操作在进入过滤器前，先SampleRealm进行认证,然后进行过滤器操作，最后再进入SampleRealm类进行授权操作
 * 在SampleRealm认证前，会在登录Controller生成Token实体,方便SampleRealm取出信息认证
 */
public class SampleRealm extends AuthorizingRealm {

	@Resource
	UUserService userService;
	@Resource
	PermissionService permissionService;
	@Resource
	RoleService roleService;

	public SampleRealm() {
		super();
	}

	/**
	 * 认证信息，主要针对用户登录，
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		//取出在登录Controller生成的Token实体信息
		ShiroToken token = (ShiroToken) authcToken;
		UUser user = userService.login(token.getUsername(), token.getPswd());
		if (null == user) {
			throw new AccountException("帐号或密码不正确！");
			/**
			 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
			 */
		} else if (UUser._0.equals(user.getStatus())) {
			throw new DisabledAccountException("帐号已经禁止登录！");
		} else {
			//更新登录时间 last login time
			user.setLastLoginTime(new Date());
			userService.updateByPrimaryKeySelective(user);
		}
		return new SimpleAuthenticationInfo(user, user.getPswd(), getName());
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Long userId = TokenService.getUserId();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里。
		Set<String> roles = roleService.findRoleByUserId(userId);
		info.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService.findPermissionByUserId(userId);
		info.setStringPermissions(permissions);
		return info;
	}

	/**
	 * 清空当前用户权限信息
	 */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}