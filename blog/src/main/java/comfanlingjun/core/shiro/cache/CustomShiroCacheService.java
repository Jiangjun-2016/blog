package comfanlingjun.core.shiro.cache;

import comfanlingjun.core.shiro.utils.redis.JedisService;
import comfanlingjun.core.shiro.utils.redis.ShiroCacheJedisService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

import javax.annotation.Resource;

/**
 * 核心类
 * <p>
 * 用户缓存
 * 用于注入 securityManager 安全管理器
 * <p>
 * 容器启动执行此bean的getCache方法,最终注入JedisService
 */
public class CustomShiroCacheService implements CacheManager, Destroyable {

	@Resource
	JedisService jedisService;

	//容器启动时候注入Cache,其实就是JedisService
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new ShiroCacheJedisService<K, V>(name, jedisService);
	}

	@Override
	public void destroy() throws Exception {
		//如果和其他系统，或者应用在一起就不能关闭
		//getJedisService().getJedis().shutdown();
	}
}