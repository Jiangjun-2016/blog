package comfanlingjun.core.shiro.cache.core;

import comfanlingjun.core.shiro.cache.ShiroCacheService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

/**
 * 用户缓存
 * 用于注入 securityManager 安全管理器
 */
public class CustomShiroCacheService implements CacheManager, Destroyable {

	//set注入 ShiroCacheServiceImpl
	private ShiroCacheService shiroCacheService;

	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return getShiroCacheService().getCache(name);
	}

	@Override
	public void destroy() throws Exception {
		shiroCacheService.destroy();
	}

	public ShiroCacheService getShiroCacheService() {
		return shiroCacheService;
	}

	public void setShiroCacheService(ShiroCacheService shiroCacheService) {
		this.shiroCacheService = shiroCacheService;
	}
}