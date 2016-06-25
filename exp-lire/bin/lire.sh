#!/bin/bash

CWD=$(cd "$(dirname "$0")" && pwd)
APP_LIB=$CWD/../lib

for i in "$APP_LIB"/*.jar; do
    CP_JARS=$CP_JARS:$i
done
CP_JARS=`echo $CP_JARS | cut -c2-`
LIB_JARS=`echo $CP_JARS | sed 's/:/,/g'`

CLASSPATH=$CP_JARS:$CWD/../config/
export CLASSPATH

case "$1" in
  'index')
    java thn.lire.Indexer "$@"
    ;;
  
  'index.descriptors')
    java thn.lire.MultiDescriptorsIndexer "$@"
    ;;
  
  'pindex')
    java thn.lire.parallel.Indexer "$@"
    ;;
  
  'pindex.features')
    java thn.lire.parallel.MultiFeaturesIndexer "$@"
    ;;
    
  'search')
    java thn.lire.Searcher "$@"
    ;;
    
  *)
    echo
    echo "Usage: $0 { index | index.descriptors | pindex | pindex.features | searcher }"
    echo

esac
exit 0


