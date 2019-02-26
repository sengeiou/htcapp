/*
package com.htcapp.tcpserver;

import com.htcapp.tcpserver.domain.TcpResult;
import com.htcapp.tcpserver.utils.AnalysisUtils;
import com.htcapp.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.security.auth.kerberos.KerberosKey;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

@Component
public class TcpServer{

    private static final int BUFSIZE = 1024;

    private static  Selector selector;

    private Logger logger= LoggerFactory.getLogger(TcpServer.class);

    private static ServerSocketChannel listnChannel;

    @Autowired
    @Qualifier("tcpProtocol")
    private TCPProtocol  protocol;

    public void run(Integer port,String host) throws IOException {
         selector = Selector.open();
        listnChannel = ServerSocketChannel.open();

        listnChannel.socket().bind(new InetSocketAddress(host, port));
        listnChannel.configureBlocking(false);
        listnChannel.register(selector, SelectionKey.OP_ACCEPT);
        protocol.setBufSize(BUFSIZE);
        System.out.println("服务器已启动。。。。");
        SelectionKey key=null;
        Iterator<SelectionKey> keyIter=null;

        int tag=0;
        while (true){
            try {
                selector.select();//这地方不是阻塞，是不断轮询
                tag=-1;
                Thread.sleep(2000);
                keyIter = selector.selectedKeys().iterator();

                while (keyIter.hasNext()) {
                    key = keyIter.next();
                    if (key.isAcceptable()) {
                        protocol.handleAccept(key);
                        tag=1;
                        keyIter.remove();
                    }
                    if (key.isReadable()) {

                        StringBuilder stringBuilder=new StringBuilder();
                        String temp=null;
                        temp=protocol.handleRead(key);
                        keyIter.remove();
                        if (temp!=null)
                            stringBuilder.append(temp);
                        else {
                            continue;
                        }
                        while(keyIter.hasNext()){
                            key=keyIter.next();
                            if (key.isReadable()){
                                temp=protocol.handleRead(key);
                                if (temp!=null)
                                stringBuilder.append(temp);
                            }else{
                                break;
                            }
                        }
                        protocol.handleRead2(stringBuilder.toString(),key);
                    }
                    if (key.isValid() && key.isWritable()) {
                        protocol.handleWrite(key);
                        keyIter.remove();
                    }
                }
            }catch (Exception e){
                keyIter.remove();
                e.printStackTrace();
            }
        }

    }

}*/
