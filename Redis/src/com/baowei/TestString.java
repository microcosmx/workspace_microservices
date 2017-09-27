package com.baowei;

import java.util.List;

import redis.clients.jedis.Jedis;

public class TestString {
	public static void main(String[] args) {
		// 获取数据库的连接
		Jedis jedis = new Jedis("192.168.2.116", 6379);

		// 添加数据
		jedis.set("age", "18");
		jedis.set("name", "zhang");

		// 一次获取一个数据
		String age = jedis.get("age");
		System.out.println(age);

		// 一次获取多个数据
		List<String> lists = jedis.mget("name", "age");
		for (int i = 0; i < lists.size(); i++) {
			String data = lists.get(i);
			System.out.println(data);
		}

		// 对redis数据库的键值，进行加1的操作
		jedis.incr("age");
		String inc_age = jedis.get("age");
		System.out.println(inc_age);

		// 删除某个键
		jedis.del("age");
		// 因为键被删除，所以返回数值为null
		String del_age = jedis.get("age");
		System.out.println(del_age);

		// 拼接
		jedis.append("name", " bao bao ");
		String app_name = jedis.get("name");
		System.out.println(app_name);

	}
}
