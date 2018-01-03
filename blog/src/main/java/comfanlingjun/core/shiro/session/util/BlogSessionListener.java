package comfanlingjun.core.shiro.session.util;


import comfanlingjun.core.shiro.session.ShiroSessionDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * shiro 会话 监听
 * 容器启动，加载此类
 */
public class BlogSessionListener implements SessionListener {

	//注入 ShiroSessionDaoImpl
	private ShiroSessionDao shiroSessionDao;

	@Override
	public void onStart(Session session) {
	}

	@Override
	public void onStop(Session session) {
	}

	@Override
	public void onExpiration(Session session) {
		shiroSessionDao.deleteSession(session.getId());
	}

	public ShiroSessionDao getShiroSessionDao() {
		return shiroSessionDao;
	}

	public void setShiroSessionDao(ShiroSessionDao shiroSessionDao) {
		this.shiroSessionDao = shiroSessionDao;
	}
}