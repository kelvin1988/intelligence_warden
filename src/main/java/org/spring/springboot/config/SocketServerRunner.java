package org.spring.springboot.config;

import org.spring.springboot.service.impl.SocketServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Order(2)
public class SocketServerRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("SocketServerRunner,threadId:" +Thread.currentThread().getId());
        Thread sockectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //起socket服务
                SocketServer server = new SocketServer();
                server.startSocketServer(8088);
            }
        });

        sockectThread.start();

        Thread.sleep(100);
        if(sockectThread.isAlive())
            System.out.println(Calendar.getInstance().getTime().toString() + " --- SocketServer start success ...");
        else
            System.out.println(Calendar.getInstance().getTime().toString() + " --- SocketServer start failed ...");
    }
}
