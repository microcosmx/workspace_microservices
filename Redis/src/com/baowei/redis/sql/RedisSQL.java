package com.baowei.redis.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.baowei.entity.User;
import com.baowei.json.util.GsonUtil;

public class RedisSQL {
	public static void main(String[] args) {

		// 将User表的内容放到Redis里面,下面为key
		// 将User表的内容field:id value: json 保存一个记录
		final String SYS_USER_TABLE = "SYS_USER_TABLE";
		// 用于保存性别为男性的User用户的Redis的Set
		final String SYS_USER_TABLE_SEX_MAN = "SYS_USER_TABLE_SEX_MAN";
		// 用于保存性别为女性的用户的Redis的Set
		final String SYS_USER_TABLE_SEX_FEMAN = "SYS_USER_TABLE_SEX_FEMAN";
		// 用于保存age为25岁的用户的Redis的Set
		final String SYS_USER_TABLE_AGE_25 = "SYS_USER_TABLE_AGE_25";
		// 获取数据库的连接
		Jedis jedis = new Jedis("localhost", 6379);

		// 模拟数据库的表User的数据
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setId(i);
			user.setName("zhang" + i);
			user.setSex("man");
			user.setAge(20 + i);
			users.add(user);
		}
		for (int i = 5; i < 10; i++) {
			User user = new User();
			user.setId(i);
			user.setName("zhang" + i);
			user.setSex("feman");
			user.setAge(20 + i);
			users.add(user);
		}

		// 通过Hash存放
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < users.size(); i++) {
			map.put(users.get(i).getId() + "",
					GsonUtil.object2Json(users.get(i)));

			// 将性别为男性的User用户
			// 加入到Redis中key为SYS_USER_TABLE_SEX_MAN的Set集合里面
			// 只需要加入User用户的id就可以了
			if ("man".equals(users.get(i).getSex())) {
				jedis.sadd(SYS_USER_TABLE_SEX_MAN, users.get(i).getId() + "");
			}
			// 将性别为女性的User用户
			// 加入到Redis中key为SYS_USER_TABLE_SEX_FEMAN的Set集合里面
			// 只需要加入User用户的id就可以了
			if ("feman".equals(users.get(i).getSex())) {
				jedis.sadd(SYS_USER_TABLE_SEX_FEMAN, users.get(i).getId() + "");
			}
			// 如果有age,birthday等，使用类似的方法
			// 在Redis里面，新建立一个Set，用来保存符合条件的相关User的id信息
			if ("25".equals(users.get(i).getAge() + "")) {
				jedis.sadd(SYS_USER_TABLE_AGE_25, users.get(i).getId() + "");
			}
		}
		// 查看转换结果
		// Set<String> keySet = map.keySet();
		// for (String key : keySet) {
		// System.out.println(map.get(key));
		// }

		// 保存User表的数据到Redis中
		// key为SYS_USER_TABLE
		// 每条记录一 feild :id value:json 的形式保存
		jedis.hmset(SYS_USER_TABLE, map);

		// ===============================
		// 模拟SQL的where多条件查询
		// 查询性别为女的user用户
		Set<String> sinter = jedis.sinter(SYS_USER_TABLE_SEX_FEMAN);
		for (String key : sinter) {
			// 根据id到Redis中的SYS_USER_TABLE,查询符合条件的user
			String hkey = jedis.hget(SYS_USER_TABLE, key);
			System.out.println(hkey);
			// 将查询到的json数据,转换为User用户
			User user = GsonUtil.json2Object(hkey, User.class);
			System.out.println(user);

		}

		// 查询年龄为25岁，性别为女的user用户
		Set<String> sinter2 = jedis.sinter(SYS_USER_TABLE_SEX_FEMAN,
				SYS_USER_TABLE_AGE_25);
		for (String key : sinter2) {
			// 根据id到Redis中的SYS_USER_TABLE,查询符合条件的user
			String hkey = jedis.hget(SYS_USER_TABLE, key);
			System.out.println(hkey);
			// 将查询到的json数据,转换为User用户
			User user = GsonUtil.json2Object(hkey, User.class);
			System.out.println(user);
		}
	}
}
