package comfanlingjun.core.shiro.utils.redis;

import comfanlingjun.code.utils.LoggerUtils;
import comfanlingjun.code.utils.SerializeUtil;
import comfanlingjun.code.utils.StringUtils;
import comfanlingjun.core.shiro.session.impl.ShiroSessionDaoImpl;
import org.apache.shiro.session.Session;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 对Redis操作
 */
public class JedisService {

	@Resource
	private JedisPool jedisPool;

	/**
	 * 查询
	 */
	public byte[] getValueByKey(int dbIndex, byte[] key) throws Exception {
		Jedis jedis = null;
		byte[] result = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			result = jedis.get(key);
		} catch (Exception e) {
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
		return result;
	}

	/**
	 * 删除
	 */
	public void deleteByKey(int dbIndex, byte[] key) throws Exception {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			Long result = jedis.del(key);
			LoggerUtils.fmtDebug(getClass(), "删除Session结果：%s", result);
		} catch (Exception e) {
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
	}

	/**
	 * 保存
	 */
	public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime)
			throws Exception {
		Jedis jedis = null;
		boolean isBroken = false;
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			jedis.set(key, value);
			if (expireTime > 0) {
				jedis.expire(key, expireTime);
			}
		} catch (Exception e) {
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
	}

	/**
	 * 获取所有Session
	 */
	public Collection<Session> AllSession(int dbIndex, String redisShiroSession) throws Exception {
		Jedis jedis = null;
		boolean isBroken = false;
		Set<Session> sessions = new HashSet<Session>();
		try {
			jedis = getJedis();
			jedis.select(dbIndex);
			Set<byte[]> byteKeys = jedis.keys((ShiroSessionDaoImpl.REDIS_SHIRO_ALL).getBytes());
			if (byteKeys != null && byteKeys.size() > 0) {
				for (byte[] bs : byteKeys) {
					Session obj = SerializeUtil.deserialize(jedis.get(bs),
							Session.class);
					if (obj instanceof Session) {
						sessions.add(obj);
					}
				}
			}
		} catch (Exception e) {
			isBroken = true;
			throw e;
		} finally {
			returnResource(jedis, isBroken);
		}
		return sessions;
	}

	/**
	 * 获得Jedis
	 */
	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (JedisConnectionException e) {
			String message = StringUtils.trim(e.getMessage());
			if ("Could not get a resource from the pool".equalsIgnoreCase(message)) {
				System.out.println("请检查你的redis服务");
				System.out.println("项目退出中....生产环境中--来自JedisManage");
				System.exit(0);//停止项目
			}
			throw new JedisConnectionException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return jedis;
	}

	/**
	 * 关闭
	 */
	public void returnResource(Jedis jedis, boolean isBroken) {
		if (jedis == null) {
			return;
		}
		/**
		 * @deprecated starting from Jedis 3.0 this method will not be exposed.
		 * Resource cleanup should be done using @see {@link Jedis#close()}
		 */
		// if (isBroken){
		// getJedisPool().returnBrokenResource(jedis);
		// }else{
		// getJedisPool().returnResource(jedis);
		// }
		jedis.close();
	}
}