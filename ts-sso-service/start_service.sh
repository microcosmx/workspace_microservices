#!/bin/bash
java -Xmx200m -jar /app/ts-sso-service-1.0.jar
/usr/local/bin/envoy -c /etc/service-envoy.json --service-cluster station
