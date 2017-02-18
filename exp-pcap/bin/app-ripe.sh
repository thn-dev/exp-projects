#!/bin/bash

CWD=$(cd "$(dirname "$0")" && pwd)
APP_LIB=$CWD/../lib

for i in "$APP_LIB"/*.jar; do
  CP_JARS=$CP_JARS:$i
done

APP_CONFIG=$CWD/../config/ripe.properties
APP_NAME=thn.exp.pcap.ripe.TestApp

CLASSPATH=.:$CP_JARS
export CLASSPATH

java $APP_NAME $*
