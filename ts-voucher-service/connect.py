#coding:utf-8
import pymysql


#连接配置信息
config = {
    'host':'ts-vouncher-mysql',
    'port':3306,
    'user':'root',
    'password':'root',
    'db':'voucherservice'
}
# 创建连接
conn = pymysql.connect(**config)