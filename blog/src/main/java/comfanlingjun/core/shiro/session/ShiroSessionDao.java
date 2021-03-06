package comfanlingjun.core.shiro.session;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 * Session操作
 */
public interface ShiroSessionDao {

	/**
	 * 存储Session
	 */
	void saveSession(Session session);

	/**
	 * 删除session
	 */
	void deleteSession(Serializable sessionId);

	/**
	 * 获取session
	 */
	Session getSession(Serializable sessionId);

	/**
	 * 获取所有sessoin
	 */
	Collection<Session> getAllSessions();
}