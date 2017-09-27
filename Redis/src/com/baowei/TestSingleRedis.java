package com.baowei;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class TestSingleRedis {

	private static Jedis jedis;
	private static ShardedJedis shard;
	private static ShardedJedisPool pool;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// 单独连接一台redis服务器
		jedis = new Jedis("192.168.2.116", 6379);
		// 主从 哨兵 使用 share
		List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo(
				"192.168.2.116", 6379));
		GenericObjectPoolConfig goConfig = new GenericObjectPoolConfig();
		goConfig.setMaxTotal(100);
		goConfig.setMaxIdle(20);
		goConfig.setMaxWaitMillis(-1);
		goConfig.setTestOnBorrow(true);
		pool = new ShardedJedisPool(goConfig, shards);

		ShardedJedis resource = pool.getResource();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		jedis.disconnect();
		shard.disconnect();
	}

}
