#!/bin/bash

CWD=$(cd "$(dirname "$0")" && pwd)
APP_LIB=$CWD/../lib

for i in "$APP_LIB"/*.jar; do
  CP_JARS=$CP_JARS:$i
done

APP_CONFIG=$CWD/../config/pcap4j.properties
APP_NAME=thn.exp.pcap.pcap4j.TestApp

CLASSPATH=.:$CP_JARS
export CLASSPATH

java $APP_NAME $*
