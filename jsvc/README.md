https://commons.apache.org/proper/commons-daemon/procrun.html

## jsvc
Jsvc uses 3 processes:

1. a launcher process

2. a controller process

3. a controlled process.

   The controlled process is also the main java thread,
   if the JVM crashes the controller will restart it in the next minute.

Jsvc is a daemon process so it should be started as root
 and the `-user` parameter allows to downgrade to an unprivilegded user.

When the `-wait` parameter is used, the launcher process waits
 until the controller says "I am ready",
  otherwise it returns after creating the controller process.


## Spring Boot Maven plugin

Spring Boot Maven plugin的5个Goals

1. spring-boot:repackage
    在mvn package之后，再次打包可执行的jar/war，同时保留mvn package生成的jar/war为.origin
2. spring-boot:run
    运行Spring Boot应用
3. spring-boot:start
    在mvn integration-test阶段，进行Spring Boot应用生命周期的管理
4. spring-boot:stop
    在mvn integration-test阶段，进行Spring Boot应用生命周期的管理
5. spring-boot:build-info
    生成Actuator使用的构建信息文件build-info.properties

## play

### deploy
``` shell
  mkdir -p  /tmp/myjsvc-demo/lib/
  mvn package
  cp -r  target/classes/lib  /tmp/myjsvc-demo/
  cp target/jsvc-1.0.jar /tmp/myjsvc-demo/lib
```

### jsvc run

``` shell
sh src/main/resources/server.sh start
sh src/main/resources/server.sh stop
```


