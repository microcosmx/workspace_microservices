

es:
docker run -d -p 9200:9200 -p 9300:9300 --name myelastic elasticsearch
docker run -d -p 9200:9200 -p 9300:9300 -v /Users/admin/work/workspace_microservices/elasticsearch/config:/usr/share/elasticsearch/config --name myelastic elasticsearch


run:
mvn exec:java -Dexec.mainClass="org.es.sample.DocSample"