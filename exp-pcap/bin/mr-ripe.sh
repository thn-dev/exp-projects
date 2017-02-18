#!/bin/bash

CWD=$(cd "$(dirname "$0")" && pwd)
APP_LIB=$CWD/../lib

for i in "$APP_LIB"/*.jar; do
  CP_JARS=$CP_JARS:$i
done

CP_JARS=`echo $CP_JARS | cut -c2-`
LIB_JARS=`echo $CP_JARS | sed 's/:/,/g'`

APP_JAR=$APP_LIB/exp-pcap.jar
APP_CONFIG=$CWD/../config/ripe.properties
APP_NAME=thn.exp.pcap.hadoop.ripe.PcapDriver

HADOOP_CLASSPATH=$CP_JARS:$HADOOP_CLASSPATH
export HADOOP_CLASSPATH

hadoop jar $APP_JAR $APP_NAME -D mapred.reduce.tasks=0 -D mapred.child.java.opts="-Dconfig.file=$APP_CONFIG" $*
