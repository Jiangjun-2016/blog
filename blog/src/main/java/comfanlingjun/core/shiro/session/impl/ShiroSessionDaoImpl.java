package comfanlingjun.core.shiro.session.impl;

import comfanlingjun.commons.utils.LoggerUtils;
import comfanlingjun.commons.utils.SerializeUtil;
import comfanlingjun.core.shiro.session.util.CustomSessionService;
import comfanlingjun.core.shiro.utils.redis.JedisService;
import comfanlingjun.core.shiro.session.ShiroSessionDao;
import comfanlingjun.core.shiro.utils.vo.SessionStatus;
import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;

/**
 * Jedis Session 管理 CRUD
 * <p>
 * 在进入SampleRealm进行认证前，
 * 先进入CustomShiroSessionDAO 进行 Session 的监听生命周期
 * BlogShiroSessionCycle 注入 ShiroSessionDaoImpl 进行 Session 的CRUD
 * <p>
 * 在进入SampleRealm进行认证前，先进入JedisShiroSessionRepository进行Session管理
 * <p>
 * JedisShiroSessionRepository的session管理调用JedisManager来操作Redis数据库
 */
public class ShiroSessionDaoImpl implements ShiroSessionDao {

	/**
	 * 这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
	 */
	public static final String REDIS_SHIRO_ALL = "*sojson-shiro-demo-session:*";
	public static final String REDIS_SHIRO_SESSION = "sojson-shiro-demo-session:";
	private static final int SESSION_VAL_TIME_SPAN = 18000;
	private static final int DB_INDEX = 1;

	//redis数据库操作类，用过Jedis
	private JedisService jedisService;

	@Override
	public void saveSession(Session session) {
		if (session == null || session.getId() == null)
			throw new NullPointerException("session is empty");
		try {
			byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session.getId()));
			//不存在才添加。
			if (null == session.getAttribute(CustomSessionService.SESSION_STATUS)) {
				//Session 踢出自存存储。
				SessionStatus sessionStatus = new SessionStatus();
				session.setAttribute(CustomSessionService.SESSION_STATUS, sessionStatus);
			}
			byte[] value = SerializeUtil.serialize(session);
			/**这里是我犯下的一个严重问题，但是也不会是致命，
			 * 我计算了下，之前上面不小心给我加了0，也就是 18000 / 3600 = 5 个小时
			 * 另外，session设置的是30分钟的话，并且加了一个(5 * 60)，一起算下来，session的失效时间是 5:35 的概念才会失效
			 * 我原来是存储session的有效期会比session的有效期会长，而且最终session的有效期是在这里【SESSION_VAL_TIME_SPAN】设置的。
			 *
			 * 这里没有走【shiro-config.properties】配置文件，需要注意的是【spring-shiro.xml】 也是直接配置的值，没有走【shiro-config.properties】
			 *
			 * PS  : 注意： 这里我们配置 redis的TTL单位是秒，而【spring-shiro.xml】配置的是需要加3个0（毫秒）。
			 long sessionTimeOut = session.getTimeout() / 1000;
			 Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
			 */
			/*
			直接使用 (int) (session.getTimeout() / 1000) 的话，session失效和redis的TTL 同时生效
             */
			getJedisService().saveValueByKey(DB_INDEX, key, value, (int) (session.getTimeout() / 1000));
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "save session error，id:[%s]", session.getId());
		}
	}

	@Override
	public void deleteSession(Serializable id) {
		if (id == null) {
			throw new NullPointerException("session id is empty");
		}
		try {
			getJedisService().deleteByKey(DB_INDEX,
					SerializeUtil.serialize(buildRedisSessionKey(id)));
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]", id);
		}
	}

	@Override
	public Session getSession(Serializable id) {
		if (id == null)
			throw new NullPointerException("session id is empty");
		Session session = null;
		try {
			byte[] value = getJedisService().getValueByKey(DB_INDEX, SerializeUtil
					.serialize(buildRedisSessionKey(id)));
			session = SerializeUtil.deserialize(value, Session.class);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]", id);
		}
		return session;
	}

	@Override
	public Collection<Session> getAllSessions() {
		Collection<Session> sessions = null;
		try {
			sessions = getJedisService().AllSession(DB_INDEX, REDIS_SHIRO_SESSION);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "获取全部session异常");
		}

		return sessions;
	}

	private String buildRedisSessionKey(Serializable sessionId) {
		return REDIS_SHIRO_SESSION + sessionId;
	}

	public JedisService getJedisService() {
		return jedisService;
	}

	public void setJedisService(JedisService jedisService) {
		this.jedisService = jedisService;
	}
}
