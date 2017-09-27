package com.baowei;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 利用一致性哈希算法，实现Redis的集群
 */
public class TestShardedJedisPool {
	public static void main(String[] args) {

		// 主从 哨兵 使用 share
		// 利用一致性哈希算法，实现Redis的集群
		// 集群的信息,一般包含多台信息
		List<JedisShardInfo> shards = Arrays.asList(new JedisShardInfo(
				"192.168.2.116", 6379));
		// 连接池信息
		GenericObjectPoolConfig goConfig = new GenericObjectPoolConfig();
		goConfig.setMaxTotal(100);
		goConfig.setMaxIdle(20);
		goConfig.setMaxWaitMillis(-1);
		goConfig.setTestOnBorrow(true);

		// 构造ShardedJedisPool
		ShardedJedisPool pool = new ShardedJedisPool(goConfig, shards);

		// 使用ShardedJedisPool来存数据
		pool.getResource().set("haha", "haha");

		// 释放资源
		pool.close();

	}
}
