package com.baowei;

import java.util.List;

import redis.clients.jedis.Jedis;

public class TestList {
	public static void main(String[] args) {
		// 获取数据库的连接
		Jedis jedis = new Jedis("192.168.2.116", 6379);
		// 若存在，先删除这个key，方便测试
		jedis.del("testlist");
		// 向key为testlist的Redis的List类型,存放数据
		// 注意lpush 和 rpush的区别
		// 可以当队列使用
		// lpush
		jedis.lpush("testlist", "zhangsan");
		jedis.lpush("testlist", "lisi");
		jedis.lpush("testlist", "wangwu");
		// rpush
		jedis.rpush("testlist", "zhaoliu");

		// 获取key为testlist的长度
		System.out.println(jedis.llen("testlist"));
		// 获取key为testlist的数据
		List<String> lists = jedis.lrange("testlist", 0, -1);
		for (int i = 0; i < lists.size(); i++) {
			System.out.println(lists.get(i));
		}
	}
}
