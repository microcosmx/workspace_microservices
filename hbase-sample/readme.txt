
clean images:
docker volume rm $(docker volume ls -qf dangling=true)
docker images|grep none|awk '{print $3 }'|xargs docker rmi

cdh access:
http://quickstart.cloudera
hbase: 
http://127.0.0.1:60010
http://127.0.0.1:8085
http://127.0.0.1:9095
hdfs: 
http://127.0.0.1:50070
yarn:
http://127.0.0.1:8088/


cdh image:
docker ps
docker commit mycdh cloudera/quickstart_local



cdh:
docker run -d --name mycdh --hostname=mycdh factual/docker-cdh5-dev
docker run --name mycdh --hostname=quickstart.cloudera --privileged=true -t -i -p 7180:7180 cloudera/quickstart:latest /usr/bin/docker-quickstart
docker run -t -i -d --name mycdh --hostname=quickstart.cloudera --privileged=true -p 7180:7180 cloudera/quickstart:latest /usr/bin/docker-quickstart
docker run --name mycdh --hostname=quickstart.cloudera --privileged=true -t -i -d \
	-p 80:80 -p 2181:2181 -p 7180:7180 -p 8888:8888 -p 9000:9000 -p 7182:7182 -p 8086:8086 -p 5678:5678 \
	-p 60000:60000 -p 60010:60010 -p 60020:60020 -p 60030:60030 -p 8080:8080 -p 8085:8085 -p 9090:9090 -p 9095:9095 \
	-p 7077:7077 -p 7078:7078 -p 18080:18080 -p 18081:18081 -p 16000:16000 -p 8005:8005 -p 21000:21000 -p 21050:21050 \
	-p 8032:8032 -p 8020:8020 -p 50010:50010 -p 50070:50070 -p 8033:8033 -p 8088:8088 -p 8090:8090 -p 19888:19888 \
	-p 3306:3306 -p 8030:8030 -p 10020:10020 -p 25000:25000 \
	cloudera/quickstart:latest /usr/bin/docker-quickstart
docker attach mycdh
docker run --name mycdh --hostname=quickstart.cloudera --privileged=true -t -i -d \
	-p 80:80 -p 2181:2181 -p 7180:7180 -p 8888:8888 -p 9000:9000 -p 7182:7182 -p 8086:8086 -p 5678:5678 \
	-p 60000:60000 -p 60010:60010 -p 60020:60020 -p 60030:60030 -p 8080:8080 -p 8085:8085 -p 9090:9090 -p 9095:9095 \
	-p 7077:7077 -p 7078:7078 -p 18080:18080 -p 18081:18081 -p 16000:16000 -p 8005:8005 -p 21000:21000 -p 21050:21050 \
	-p 8032:8032 -p 8020:8020 -p 50010:50010 -p 50070:50070 -p 8033:8033 -p 8088:8088 -p 8090:8090 -p 19888:19888 \
	-p 3306:3306 -p 8030:8030 -p 10020:10020 -p 25000:25000 \
	-v /Users/admin/work/workspace_data:/opt/workspace_data \
	cloudera/quickstart_local /usr/bin/docker-quickstart

cloudera manager:
/home/cloudera/cloudera-manager
/home/cloudera/cloudera-manager --express
http://quickstart.cloudera:7180
cloudera/cloudera


mysql:
mysql -u retail_dba -p
cloudera


sqoop:
sqoop import-all-tables \
    -m 1 \
    --connect jdbc:mysql://quickstart.cloudera:3306/retail_db \
    --username=retail_dba \
    --password=cloudera \
    --compression-codec=snappy \
    --as-parquetfile \
    --warehouse-dir=/user/hive/warehouse \
    --hive-import

sqoop import-all-tables \
    -m 1 \
    --connect jdbc:mysql://172.17.0.3:3306/mysql \
    --username=root \
    --password=root \
    --compression-codec=snappy \
    --as-parquetfile \
    --warehouse-dir=/user/hive/warehouse \
    --hive-import
    
hadoop:
hadoop fs -ls /user/hive/warehouse/
hadoop fs -ls /user/hive/warehouse/categories/
access:
http://127.0.0.1:50070/explorer.html#/


hue:
http://127.0.0.1:8888/accounts/login/?next=/
cloudera/cloudera


impala:
http://127.0.0.1:8888/impala/#query
invalidate metadata;
show tables;
DML SQL:
	select p.product_id, p.product_name, r.revenue
	from products p inner join
	(select oi.order_item_product_id, sum(cast(oi.order_item_subtotal as float)) as revenue
	from order_items oi inner join orders o
	on oi.order_item_order_id = o.order_id
	where o.order_status <> 'CANCELED'
	and o.order_status <> 'SUSPECTED_FRAUD'
	group by order_item_product_id) r
	on p.product_id = r.order_item_product_id
	order by r.revenue desc
	limit 10;


unstructured:
[cloudera@quickstart ~]$ sudo -u hdfs hadoop fs -mkdir /user/hive/warehouse/original_access_logs
[cloudera@quickstart ~]$ sudo -u hdfs hadoop fs -copyFromLocal /opt/examples/log_files/access.log.2 /user/hive/warehouse/original_access_logs

Hive Query Editor:
	CREATE EXTERNAL TABLE intermediate_access_logs (
	    ip STRING,
	    date STRING,
	    method STRING,
	    url STRING,
	    http_version STRING,
	    code1 STRING,
	    code2 STRING,
	    dash STRING,
	    user_agent STRING)
	ROW FORMAT SERDE 'org.apache.hadoop.hive.contrib.serde2.RegexSerDe'
	WITH SERDEPROPERTIES (
	    'input.regex' = '([^ ]*) - - \\[([^\\]]*)\\] "([^\ ]*) ([^\ ]*) ([^\ ]*)" (\\d*) (\\d*) "([^"]*)" "([^"]*)"',
	    'output.format.string' = "%1$$s %2$$s %3$$s %4$$s %5$$s %6$$s %7$$s %8$$s %9$$s")
	LOCATION '/user/hive/warehouse/original_access_logs';
	
	CREATE EXTERNAL TABLE tokenized_access_logs (
	    ip STRING,
	    date STRING,
	    method STRING,
	    url STRING,
	    http_version STRING,
	    code1 STRING,
	    code2 STRING,
	    dash STRING,
	    user_agent STRING)
	ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
	LOCATION '/user/hive/warehouse/tokenized_access_logs';
	
	ADD JAR /usr/lib/hive/lib/hive-contrib.jar;
	
	INSERT OVERWRITE TABLE tokenized_access_logs SELECT * FROM intermediate_access_logs;


spark:
spark-shell --master yarn-client
