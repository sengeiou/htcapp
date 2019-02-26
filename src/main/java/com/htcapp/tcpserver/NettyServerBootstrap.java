package com.htcapp.tcpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
@Component
public class NettyServerBootstrap {

    private static final Logger logger= LoggerFactory.getLogger(NettyServerBootstrap.class);
    //指定一个请求当中获取的最大数据
    @Value("${bufSize}")
    private String bufSize;


    @Value("${linkCount}")
    private String linkCount;

    @Value("${filePath}")
    private String rootPath;

    @Value("${enconding}")
    private String enconding;

    @Autowired
    private NettyServerHandler nettyServerHandler;


    private byte [] bytes={(byte) 0xbb, (byte) 0x88};

    private int port;


    public void start(int port) {
        this.port = port;
        bind();
    }


    private void bind() {


        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();


        try {


            ServerBootstrap bootstrap = new ServerBootstrap();


            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, Integer.valueOf(linkCount)); //连接数
            bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel)
                        throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    p.addLast(new DelimiterBasedFrameDecoder(Integer.valueOf(bufSize),
                            Unpooled.copiedBuffer(bytes)));
                    p.addLast(new StringDecoder(Charset.forName(enconding)));
                    p.addLast(nettyServerHandler);
                }
            });
            ChannelFuture f = bootstrap.bind(port).sync();
            if (f.isSuccess()) {
                System.out.println("启动Netty服务成功，端口号：" + this.port);
            }
// 关闭连接
            f.channel().closeFuture().sync();


        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}