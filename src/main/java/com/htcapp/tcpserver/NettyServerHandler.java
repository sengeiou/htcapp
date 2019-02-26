package com.htcapp.tcpserver;

import com.htcapp.tcpserver.domain.AnaResult;
import com.htcapp.tcpserver.domain.TcpResult;
import com.htcapp.tcpserver.utils.AnalysisUtils;
import com.htcapp.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger= LoggerFactory.getLogger(NettyServerHandler.class);

    @Autowired
    private HandleResult handleResult;
    @Value("${camera}")
    private String rootPath;

    @Value("${enconding}")
    private String enconding;

    public NettyServerHandler(){
    }
    public NettyServerHandler(String rootPath,String enconding,HandleResult handleResult){
        this.rootPath=rootPath;
        this.enconding=enconding;
        this.handleResult=handleResult;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) {
            String recieved = (String) msg;
            TcpResult tcpResult=null;
            AnaResult anaResult=null;

            try {
                anaResult=AnalysisUtils.analysis(recieved,rootPath);
                tcpResult=handleResult.handle(anaResult);
                String result=JsonUtils.objectToString(tcpResult);
                ctx.writeAndFlush(getSendByteBuf(result));
            } catch (Exception e) {
                tcpResult=TcpResult.build(500,e.getMessage());
                ctx.writeAndFlush(getSendByteBuf(JsonUtils.objectToString(tcpResult)));
                logger.error(e.getMessage());
                ctx.close();
            }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
    }

    private ByteBuf getSendByteBuf(String message) {
        byte[] req = new byte[0];
        try {
            req = message.getBytes(enconding);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);

        return pingMessage;
    }
}