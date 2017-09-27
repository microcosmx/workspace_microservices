package com.baowei;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class TestHashMap {
	public static void main(String[] args) {

		// 获取数据库的连接
		Jedis jedis = new Jedis("192.168.2.116", 6379);

		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "zhangsan");
		map.put("age", "18");
		map.put("qq", "123456");

		// 添加数据到Redis的Hash
		jedis.hmset("user", map);

		// 获取map字段的某个属性的数据
		String name = jedis.hget("user", "name");
		System.out.println(name);

		// 获取map的多个属性的数据
		List<String> user = jedis.hmget("user", "name", "age");
		System.out.println(user);

		// 获取Hash的键值的所有的属性以及数据
		Iterator<String> iterator = jedis.hkeys("user").iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			System.out.println(key + ":" + jedis.hget("user", key));
		}

		// 判断一个Hash是否包含一个 field 的属性
		boolean contains = jedis.hkeys("user").contains("name");
		System.out.println(contains);
	}
}
