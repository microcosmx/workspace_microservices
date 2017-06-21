
rest url:
http://localhost:15000/greeting?name=jay
http://rest-service:15000/greeting?name=jay
return json:
{"id":2,"content":"Hello, jay!"}

build:
mvn -Dmaven.test.skip=true clean package

docker:
docker build -t my/rest-service .
docker run -p 15000:15000 --name my-rest-service my/rest-service
docker run -d -p 15000:15000 --name my-rest-service my/rest-service

docker resources:
memory:
指定限制内存大小并且设置 memory-swap 值为 -1，表示容器程序使用内存受限，而 swap 空间使用不受限制（宿主 swap 支持使用多少则容器即可使用多少。
如果不添加--memory-swap选项，则表示容器中程序可以使用100M内存和100Mswap内存，默认情况下，--memory-swap 会被设置成 memory 的 2倍。
-m 为物理内存上限，而 --memory-swap 则是 memory + swap 之和，当压测值是 --memory-swap 上限时，则容器中的进程会被直接 OOM kill
cpu:
比如说A容器配置的--cpu-period=100000 --cpu-quota=50000，那么A容器就可以最多使用50%个CPU资源，如果配置的--cpu-quota=200000，那就可以使用200%个CPU资源。
sample:
docker run -d -p 15000:15000 \
	--name my-rest-service \
  	-m 300M --memory-swap 600M \
  	--cpu-period=100000 --cpu-quota=50000 \
  	my/rest-service
docker run -p 15000:15000 \
	--name my-rest-service \
  	-m 1000M --memory-swap 2000M \
  	my/rest-service

memory allocation:
http://localhost:15000/memory

monitoring:
docker run \
  --volume=/:/rootfs:ro \
  --volume=/var/run:/var/run:rw \
  --volume=/sys:/sys:ro \
  --volume=/var/lib/docker/:/var/lib/docker:ro \
  --publish=17000:8080 \
  --detach=true \
  --name=cadvisor \
  google/cadvisor:latest


mongodb:
docker run -d -p 27017:27017 --name my-rest-service-mongo mongo
docker run -p 15000:15000 --name my-rest-service --link my-rest-service-mongo:my-rest-service-mongo my/rest-service

!!!!!notice: please add following lines into /etc/hosts to simulate the network access:
127.0.0.1	rest-service
127.0.0.1	rest-client



endpoint actuator:
http://localhost:15000/greeting?name=jay
http://localhost:15000/health
http://localhost:15000/info
http://localhost:15000/beans
http://localhost:15000/env
http://localhost:15000/dump
http://localhost:15000/mappings
http://localhost:15000/trace
http://localhost:15000/autoconfig
http://localhost:15000/metrics
