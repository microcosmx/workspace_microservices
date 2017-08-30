
rest url:
http://rest-service-end:16000/greeting?cal=50
return json:
{"id":2,"result":false}


run:
java -jar target/rest-service-end-0.1.0.jar
java -jar /Users/admin/work/workspace_microservices/1-services/rest-service-end/target/rest-service-end-0.1.0.jar

dtrace run:
su root
/Users/admin/work/workspace_jvm/java-source-samples/sample/dtrace/hotspot/method_invocation_tree.d -c "java -jar /Users/admin/work/workspace_microservices/1-services/rest-service-end/target/rest-service-end-0.1.0.jar"
/Users/admin/work/workspace_jvm/java-source-samples/sample/dtrace/hotspot/method_invocation_tree.d -p 20985


docker resources:
docker build -t my/rest-service-end .
docker run -d -p 15000:15000 \
	--name rest-service-end \
  	-m 300M --memory-swap 600M \
  	--cpu-period=100000 --cpu-quota=50000 \
  	my/rest-service-end