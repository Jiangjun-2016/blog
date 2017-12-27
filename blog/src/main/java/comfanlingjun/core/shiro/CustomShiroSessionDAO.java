package comfanlingjun.core.shiro;

import comfanlingjun.commons.utils.LoggerUtils;
import comfanlingjun.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * 重写实现增删改查方法
 * <p>
 * 通过重写AbstractSessionDAO ，来实现 Session共享。
 * 再重写 Session  的时候（其实也不算重写），因为和HttpSession 没有任何实现或者继承关系。
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO {

	//set注入 JedisShiroSessionRepository  Session操作 CRUD
	private ShiroSessionRepository shiroSessionRepository;

	@Override
	public void update(Session session) throws UnknownSessionException {
		getShiroSessionRepository().saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if (session == null) {
			LoggerUtils.error(getClass(), "Session 不能为null");
			return;
		}
		Serializable id = session.getId();
		if (id != null)
			getShiroSessionRepository().deleteSession(id);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return getShiroSessionRepository().getAllSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		getShiroSessionRepository().saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return getShiroSessionRepository().getSession(sessionId);
	}

	public ShiroSessionRepository getShiroSessionRepository() {
		return shiroSessionRepository;
	}

	public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
		this.shiroSessionRepository = shiroSessionRepository;
	}
}
