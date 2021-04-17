#!/bin/sh

RETVAL=0
prog="MYJSVC"

#应用程序的目录
MYJSVC_HOME=/tmp/myjsvc-demo
#用户
MYJSVC_USER=${USER}

# for multi instances adapt those lines.
TMP_DIR=${MYJSVC_HOME}/tmp
PID_FILE=${MYJSVC_HOME}/tlstat.pid

#程序运行是所需的jar包，commons-daemon.jar是不能少的
CLASSPATH=""
for file in ${MYJSVC_HOME}/lib/*.jar
do
    if [ ! -n ${CLASSPATH} ] ;then
         CLASSPATH=${file}
    else
         CLASSPATH="${CLASSPATH}:$file"
    fi
done


case "$1" in
start)
#
# Start TlStat Data Serivce
#
jsvc -debug -user ${MYJSVC_USER} \
-home ${JAVA_HOME} -Djava.io.tmpdir=${TMP_DIR} \
-wait 10 -pidfile ${PID_FILE} \
-outfile ${MYJSVC_HOME}/log/myjsvc.out \
-errfile '&1' -cp ${CLASSPATH} com.afonddream.jsvc.App

#
# To get a verbose JVM
#-verbose \
# To get a debug of jsvc.
#-debug \
exit $?
;;

stop)
#
# Stop TlStat Data Serivce
#
jsvc -debug -stop -pidfile ${PID_FILE} com.afonddream.jsvc.App
exit $?
;;

*)
echo "Usage myjsvc start/stop"
exit 1;;
esac