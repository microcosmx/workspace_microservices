FROM java:8
MAINTAINER fenqi <fenqi@mogujie.com>

# Install Hbase.
# ADD hbase-1.2.0-cdh5.7.0.tgz /tmp/
RUN \
  cd /tmp && \
  wget https://github.com/sel-fish/hbase-1.2.0-cdh5.7.0/releases/download/mj23/hbase-1.2.0-cdh5.7.0.tgz && \
  tar xvzf hbase-1.2.0-cdh5.7.0.tgz && \
  mv hbase-1.2.0-cdh5.7.0 /opt/hbase && \
  export JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"

# Expose Hbase ports
#
#   2181 – zookeeper port
#   60000 – master api port
#   60010 – master web port
#   60020 – regionserver api port
#   60030 – regionserver web port
EXPOSE 2181 60000 60010 60020 60030

# Define default command.
CMD ["/opt/hbase/bin/hbase", "master", "start"]