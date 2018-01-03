package comfanlingjun.core.shiro.cache.impl;

import comfanlingjun.core.shiro.utils.redis.JedisService;
import comfanlingjun.core.shiro.cache.util.ShiroCacheJedis;
import comfanlingjun.core.shiro.cache.ShiroCacheService;
import org.apache.shiro.cache.Cache;

/**
 * JRedis管理
 * 用于注入 CustomShiroCacheService 用户缓存
 */
public class ShiroCacheServiceImpl implements ShiroCacheService {

	//set注入 Redis操作
	private JedisService jedisService;

	@Override
	public <K, V> Cache<K, V> getCache(String name) {
		return new ShiroCacheJedis<K, V>(name, getJedisService());
	}

	@Override
	public void destroy() {
		//如果和其他系统，或者应用在一起就不能关闭
		//getJedisService().getJedis().shutdown();
	}

	public JedisService getJedisService() {
		return jedisService;
	}

	public void setJedisService(JedisService jedisService) {
		this.jedisService = jedisService;
	}
}