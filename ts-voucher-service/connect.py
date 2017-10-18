
import pymysql

#连接配置信息
config = {
    'host':'127.0.0.1',
    'port':3306,
    'user':'root',
    'password':'lwh319',
    'db':'voucherservice'
}
# 创建连接
conn = pymysql.connect(**config)