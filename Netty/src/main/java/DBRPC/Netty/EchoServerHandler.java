/**    
 * 文件名：TimeServerHandler.java    
 *    
 * 版本信息：    
 * 日期：2018年3月19日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.Netty;

import java.net.InetSocketAddress;

import FastRPC.RPCNet.JSocket;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**    
 *     
 * 项目名称：Netty    
 * 类名称：TimeServerHandler    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月19日 上午12:43:47    
 * 修改人：jinyu    
 * 修改时间：2018年3月19日 上午12:43:47    
 * 修改备注：    
 * @version     
 *     
 */
public class EchoServerHandler   extends ChannelHandlerAdapter {
   public HanderSocket hander=new HanderSocket();
    @Override

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

            cause.printStackTrace();

            ctx.close();
  
        }
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
      
        JSocket recvicer=new  JSocket();
        recvicer.buffer=(byte[]) msg;
        NettySocket c=new NettySocket();
        c.ctx=ctx;
        recvicer.sockset=c;
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        int port=insocket.getPort();
        recvicer.srcIP=clientIP;
        recvicer.srcPort=port;
        hander.hander.recvicer(recvicer);

    }  

    @Override  
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {  
        ctx.flush();  
    }  

}
