

run:
docker run -d --name myhbase -p 2181:2181 -p 8080:8080 -p 8085:8085 -p 9090:9090 -p 9095:9095 -p 16010:16010 dajobe/hbase
docker run -d --name myhbase -p 2181:2181 -p 8080:8080 -p 8085:8085 -p 9090:9090 -p 9095:9095 -p 16000:16000 -p 16010:16010 -p 16201:16201 -p 16301:16301 harisekhon/hbase
docker run -d --name myhbase -p 2181:2181 -p 60000:60000 -p 60010:60010 -p 60020:60020 -p 60030:60030 -p 65000:65000 -p 65010:65010 -p 65020:65020 -p 65030:65030 -h hbase oddpoet/hbase-cdh5


cdh:
docker run -d --name mycdh --hostname=mycdh factual/docker-cdh5-dev
docker run --name mycdh --hostname=quickstart.cloudera --privileged=true -t -i -p 7180:7180 cloudera/quickstart:latest /usr/bin/docker-quickstart
docker run -t -i -d --name mycdh --hostname=quickstart.cloudera --privileged=true -p 7180:7180 cloudera/quickstart:latest /usr/bin/docker-quickstart
docker run --name mycdh --hostname=quickstart.cloudera --privileged=true -t -i -d \
	-p 80:80 -p 2181:2181 -p 7180:7180 -p 8888:8888 -p 8032:8032 -p 8020:8020 -p 50010:50010 -p 50070:50070 -p 8033:8033 -p 8088:8088 -p 8090:8090 \
	-p 60000:60000 -p 60010:60010 -p 60020:60020 -p 60030:60030 -p 8080:8080 -p 8085:8085 -p 9090:9090 -p 9095:9095 \
	-p 7077:7077 -p 7078:7078 -p 18080:18080 -p 18081:18081 -p 16000:16000 -p 8005:8005 -p 21000:21000 -p 21050:21050 \
	cloudera/quickstart:latest /usr/bin/docker-quickstart
docker attach mycdh

cloudera manager:
/home/cloudera/cloudera-manager


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





access:
hbase: http://127.0.0.1:60010
hdfs: http://127.0.0.1:50070
localhost:8085
localhost:9095
localhost:16010


