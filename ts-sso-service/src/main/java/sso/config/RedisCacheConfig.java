package sso.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration

@EnableCaching //启用缓存，这个注解很重要；

public class RedisCacheConfig extends CachingConfigurerSupport {


    /**
     * 缓存管理器.
     *
     * @param redisTemplate
     * @return
     */

    @Bean

    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {

        CacheManager cacheManager = new RedisCacheManager(redisTemplate);

        return cacheManager;

    }


    /**
     * redis模板操作类,类似于jdbcTemplate的一个类;
     * <p>
     * <p>
     * <p>
     * 虽然CacheManager也能获取到Cache对象，但是操作起来没有那么灵活；
     * <p>
     * <p>
     * <p>
     * 这里在扩展下：RedisTemplate这个类不见得很好操作，我们可以在进行扩展一个我们
     * <p>
     * <p>
     * <p>
     * 自己的缓存类，比如：RedisStorage类;
     *
     * @param factory : 通过Spring进行注入，参数在application.properties进行配置；
     * @return
     */

    @Bean

    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();

        redisTemplate.setConnectionFactory(factory);

        return redisTemplate;

    }
}
