package comfanlingjun.core.shiro.listenter;


import comfanlingjun.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * shiro 会话 监听
 */
public class CustomSessionListener implements SessionListener {

	private ShiroSessionRepository shiroSessionRepository;

	/**
	 * 一个回话的生命周期开始
	 */
	@Override
	public void onStart(Session session) {
		System.out.println("on start");
	}

	/**
	 * 一个回话的生命周期结束
	 */
	@Override
	public void onStop(Session session) {
		System.out.println("on stop");
	}

	@Override
	public void onExpiration(Session session) {
		shiroSessionRepository.deleteSession(session.getId());
	}

	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}
}