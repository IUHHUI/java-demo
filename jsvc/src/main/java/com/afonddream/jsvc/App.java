package com.afonddream.jsvc;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(value = "com.afonddream.jsvc")
@EnableAutoConfiguration
@SpringBootApplication
@Configuration
@EnableScheduling
@EnableConfigurationProperties
public class App implements Daemon {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void init(DaemonContext daemonContext) throws DaemonInitException, Exception {
        LOGGER.info("execute init method : init(DaemonContext daemonContext)");
    }

    @Override
    public void start() throws Exception {
        SpringApplication.run(App.class);
        LOGGER.info("execute start method : SpringApplication.run(App.class)");
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("execute stop method！");
    }

    @Override
    public void destroy() {
        LOGGER.info("execute destroy method!");
    }

}


