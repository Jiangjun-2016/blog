package comfanlingjun.core.shiro.utils.redis;

import comfanlingjun.commons.utils.SerializeUtil;
import comfanlingjun.commons.utils.SpringContextUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * SimpleJedisService 简单封装 Cache
 * <p>
 * SimpleJedisService和 JedisService 没有区别，
 * 单纯的利用JedisService获取jedis实例(jedisService.getJedis();),对Redis直接进行操作
 * <p>
 * 把 shiro的操作 和 业务Cache操作 分开
 */
public class SimpleJedisService {

	public final static JedisService jedisService = SpringContextUtil.getBean("jedisService", JedisService.class);

	private SimpleJedisService() {
	}

	/**
	 * 简单的Get
	 */
	public static <T> T get(String key, Class<T>... requiredType) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] skey = SerializeUtil.serialize(key);
			return SerializeUtil.deserialize(jds.get(skey), requiredType);
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return null;
	}

	/**
	 * 简单的set
	 */
	public static void set(Object key, Object value) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] skey = SerializeUtil.serialize(key);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.set(skey, svalue);
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
	}

	/**
	 * 过期时间的
	 */
	public static void setex(Object key, Object value, int timer) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] skey = SerializeUtil.serialize(key);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.setex(skey, timer, svalue);
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}

	}

	/**
	 * @param <T>
	 * @param mapkey       map
	 * @param key          map里的key
	 * @param requiredType value的泛型类型
	 * @return
	 */
	public static <T> T getVByMap(String mapkey, String key, Class<T> requiredType) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] mkey = SerializeUtil.serialize(mapkey);
			byte[] skey = SerializeUtil.serialize(key);
			List<byte[]> result = jds.hmget(mkey, skey);
			if (null != result && result.size() > 0) {
				byte[] x = result.get(0);
				T resultObj = SerializeUtil.deserialize(x, requiredType);
				return resultObj;
			}
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return null;
	}

	/**
	 * @param mapkey map
	 * @param key    map里的key
	 * @param value  map里的value
	 */
	public static void setVByMap(String mapkey, String key, Object value) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] mkey = SerializeUtil.serialize(mapkey);
			byte[] skey = SerializeUtil.serialize(key);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.hset(mkey, skey, svalue);
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
	}

	/**
	 * 删除Map里的值
	 */
	public static Object delByMapKey(String mapKey, String... dkey) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[][] dx = new byte[dkey.length][];
			for (int i = 0; i < dkey.length; i++) {
				dx[i] = SerializeUtil.serialize(dkey[i]);
			}
			byte[] mkey = SerializeUtil.serialize(mapKey);
			Long result = jds.hdel(mkey, dx);
			return result;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return new Long(0);
	}

	/**
	 * 往redis里取set整个集合
	 */
	public static <T> Set<T> getVByList(String setKey, Class<T> requiredType) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] lkey = SerializeUtil.serialize(setKey);
			Set<T> set = new TreeSet<T>();
			Set<byte[]> xx = jds.smembers(lkey);
			for (byte[] bs : xx) {
				T t = SerializeUtil.deserialize(bs, requiredType);
				set.add(t);
			}
			return set;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return null;
	}

	/**
	 * 获取Set长度
	 */
	public static Long getLenBySet(String setKey) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			Long result = jds.scard(setKey);
			return result;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return null;
	}

	/**
	 * 删除Set
	 */
	public static Long delSetByKey(String key, String... dkey) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			Long result = 0L;
			if (null == dkey) {
				result = jds.srem(key);
			} else {
				result = jds.del(key);
			}
			return result;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return new Long(0);
	}

	/**
	 * 随机 Set 中的一个值
	 */
	public static String srandmember(String key) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			String result = jds.srandmember(key);
			return result;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return null;
	}

	/**
	 * 往redis里存Set
	 */
	public static void setVBySet(String setKey, String value) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			jds.sadd(setKey, value);
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
	}

	/**
	 * 取set
	 */
	public static Set<String> getSetByKey(String key) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			Set<String> result = jds.smembers(key);
			return result;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return null;
	}


	/**
	 * 往redis里存List
	 */
	public static void setVByList(String listKey, Object value) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] lkey = SerializeUtil.serialize(listKey);
			byte[] svalue = SerializeUtil.serialize(value);
			jds.rpush(lkey, svalue);
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
	}

	/**
	 * 往redis里取list
	 */
	public static <T> List<T> getVByList(String listKey, int start, int end, Class<T> requiredType) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] lkey = SerializeUtil.serialize(listKey);
			List<T> list = new ArrayList<T>();
			List<byte[]> xx = jds.lrange(lkey, start, end);
			for (byte[] bs : xx) {
				T t = SerializeUtil.deserialize(bs, requiredType);
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return null;
	}

	/**
	 * 获取list长度
	 */
	public static Long getLenByList(String listKey) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] lkey = SerializeUtil.serialize(listKey);
			Long result = jds.llen(lkey);
			return result;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return null;
	}

	/**
	 * 删除
	 */
	public static Long delByKey(String... dkey) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[][] dx = new byte[dkey.length][];
			for (int i = 0; i < dkey.length; i++) {
				dx[i] = SerializeUtil.serialize(dkey[i]);
			}
			Long result = jds.del(dx);
			return result;
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return new Long(0);
	}

	/**
	 * 判断是否存在
	 */
	public static boolean exists(String existskey) {
		Jedis jds = null;
		boolean isBroken = false;
		try {
			jds = jedisService.getJedis();
			jds.select(0);
			byte[] lkey = SerializeUtil.serialize(existskey);
			return jds.exists(lkey);
		} catch (Exception e) {
			isBroken = true;
			e.printStackTrace();
		} finally {
			returnResource(jds, isBroken);
		}
		return false;
	}

	/**
	 * 释放
	 */
	public static void returnResource(Jedis jedis, boolean isBroken) {
		if (jedis == null)
			return;
		// if (isBroken)
		//     jedisService.getJedisPool().returnBrokenResource(jedis);
		// else
		// 	jedisService.getJedisPool().returnResource(jedis);
		// 版本问题
		jedis.close();
	}
}