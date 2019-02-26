package com.htcapp;

import com.htcapp.tcpserver.NettyServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationRunner{
    private static final Logger logger= LoggerFactory.getLogger(ApplicationStartup.class);

    @Value("${tcp.server}")
    private String host;
    @Value("${tcp.port}")
    private Integer port;

    @Autowired
    private NettyServerBootstrap nettyServerBootstrap;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     nettyServerBootstrap.start(port);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }).start();

    }
}
