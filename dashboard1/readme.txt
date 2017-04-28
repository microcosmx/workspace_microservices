
rest url:
http://localhost
http://localhost:10000/greeting/?name=jay
http://localhost:10000/wd/hub/static/resource/hub.html

docker:
docker build -t my/nginx .
docker run -d -p 10000:8080 --hostname my-nginx --link selenium-standalone-chrome:ssc --name my-nginx my/nginx
docker exec -it my-nginx /bin/sh