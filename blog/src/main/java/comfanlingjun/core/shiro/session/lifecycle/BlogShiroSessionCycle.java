package comfanlingjun.core.shiro.session.lifecycle;

import comfanlingjun.commons.utils.LoggerUtils;
import comfanlingjun.core.shiro.session.ShiroSessionDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

/**
 * 核心类
 * <p>
 * 监听Shiro——Session的生命周期
 * <p>
 * 重写实现增删改查方法
 * <p>
 * 通过重写AbstractSessionDAO ，来实现 Session共享。
 * 再重写 Session  的时候（其实也不算重写），因为和HttpSession 没有任何实现或者继承关系。
 * <p>
 * 在进入SampleRealm进行认证前，
 * 先进入CustomShiroSessionDAO 进行 Session 的监听生命周期
 * BlogShiroSessionCycle 注入 ShiroSessionDaoImpl 进行 Session 的CRUD
 */
public class BlogShiroSessionCycle extends AbstractSessionDAO {

	@Resource
	private ShiroSessionDao shiroSessionDao;

	//web端操作均通过此方法
	@Override
	public void update(Session session) throws UnknownSessionException {
		shiroSessionDao.saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if (session == null) {
			LoggerUtils.error(getClass(), "Session 不能为null");
			return;
		}
		Serializable id = session.getId();
		if (id != null)
			shiroSessionDao.deleteSession(id);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return shiroSessionDao.getAllSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		shiroSessionDao.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return shiroSessionDao.getSession(sessionId);
	}

}