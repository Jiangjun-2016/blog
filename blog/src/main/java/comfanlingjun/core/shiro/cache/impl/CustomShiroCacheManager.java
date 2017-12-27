package comfanlingjun.core.shiro.cache.impl;

import comfanlingjun.core.shiro.cache.ShiroCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

/**
 * 用户缓存
 * 用于注入 securityManager 安全管理器
 */
public class CustomShiroCacheManager implements CacheManager, Destroyable {

	//set注入 JedisShiroCacheManager
	private ShiroCacheManager shiroCacheManager;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return getShiroCacheManager().getCache(name);
	}

	@Override
	public void destroy() throws Exception {
		shiroCacheManager.destroy();
	}

	public ShiroCacheManager getShiroCacheManager() {
		return shiroCacheManager;
	}

	public void setShiroCacheManager(ShiroCacheManager shiroCacheManager) {
		this.shiroCacheManager = shiroCacheManager;
	}
}