package com.baowei;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class TestSet {
	public static void main(String[] args) {
		// 获取数据库的连接
		Jedis jedis = new Jedis("192.168.2.116", 6379);

		// 若存在，先删除这个key，方便测试
		jedis.del("testset");

		// 向Redis的Set里面添加数据
		jedis.sadd("testset", "zhangsan");
		jedis.sadd("testset", "lisi");
		jedis.sadd("testset", "wangwu");
		jedis.sadd("testset", "zhaoliu");

		// 向Redis的Set里面移除数据
		jedis.srem("testset", "zhangsan");

		// 关于Redis的Set的相关操作
		// 获取所有加入的Value
		Set<String> set = jedis.smembers("testset");
		for (String str : set) {
			System.out.println(str);
		}
		// 判断who是不是testlist集合的元素
		Boolean sismember = jedis.sismember("testset", "who");
		System.out.println(sismember);
		// 返回testlist集合的个数
		Long scard = jedis.scard("testset");
		System.out.println(scard);

	}
}
