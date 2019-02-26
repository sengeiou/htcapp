/*
package com.htcapp.tcpserver;


import com.htcapp.tcpserver.domain.AnaResult;
import com.htcapp.service.PayService;
import com.htcapp.tcpserver.domain.TcpResult;
import com.htcapp.tcpserver.utils.*;
import com.htcapp.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Arrays;

*/
/*
*  处理NIO的请求：
*     1. 上传停车场信息
*     2. 处理车牌数据
*     3.
* *//*

@Component
@Qualifier("tcpProtocol")
public class EchoSelectorProtocol implements TCPProtocol{
	private static final Logger logger= LoggerFactory.getLogger(EchoSelectorProtocol.class);
	private int bufSize;
	private AnaResult anaResult;

	@Value("${camera}")
	private String rootPath;

	@Autowired
	private HandleResult handleResult;

	@Autowired
	private PayService payService;

	public void setBufSize(int bufSize) {
		this.bufSize = bufSize;
	}

	@Override
	public void handleRead2(String result,SelectionKey key) throws Exception {

		byte [] bytes=result.getBytes("GBK");
		int length=bytes.length;
		//获取读取的结果
		TcpResult tcpResult=null;
		SocketChannel clntChan= (SocketChannel) key.channel();
		try {
			anaResult =null;// AnalysisUtils.analysis(bytes,length,rootPath);
			tcpResult=handleResult.handle(anaResult);
		} catch (Exception e) {
			logger.error(e.getMessage());
			tcpResult=TcpResult.build(500,e.getMessage());
		}

		ByteBuffer byteBuff=ByteBuffer.allocate(bufSize);
		byteBuff.clear();
		byteBuff.put(JsonUtils.objectToString(tcpResult).getBytes("GBK"));
		//byteBuff.put(bytes);
		byteBuff.flip();
		clntChan.register(key.selector(), SelectionKey.OP_WRITE,byteBuff);
	}

*/
/*	@Autowired
	private PayService payService;*//*


	public void handleAccept(SelectionKey key) throws IOException {
		SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
	    clntChan.configureBlocking(false);
	    clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize*bufSize));
	  }

	  public String  handleRead(SelectionKey key) throws IOException {
	    SocketChannel clntChan = (SocketChannel) key.channel();
	    ByteBuffer buf = (ByteBuffer) key.attachment();
	    int length=0;
	    int temp;
		while ((temp = clntChan.read(buf)) > 0) {
			length += temp;
		}

		  if (temp==-1){
			  clntChan.close();
			return null;
		}else{
		  	return new String(buf.array(),0,length,"GBK");
		}

	  }

	public void handleWrite(SelectionKey key) throws IOException {
	    ByteBuffer buf = (ByteBuffer) key.attachment();
	    SocketChannel clntChan = (SocketChannel) key.channel();
	    clntChan.write(buf);
		clntChan.register(key.selector(), SelectionKey.OP_READ,ByteBuffer.allocate(bufSize*bufSize));
	  }
}
*/
