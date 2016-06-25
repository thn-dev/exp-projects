#!/bin/bash

CWD=$(cd "$(dirname "$0")" && pwd)
APP_LIB=$CWD/../lib
APP_CONFIG=$CWD/../config/

for i in "$APP_LIB"/*.jar; do
    CP_JARS=$CP_JARS:$i
done
CP_JARS=`echo $CP_JARS | cut -c2-`
LIB_JARS=`echo $CP_JARS | sed 's/:/,/g'`

CLASSPATH=$CP_JARS:$APP_CONFIG
export CLASSPATH

APP_CLASS=

java $APP_CLASS "$@"

