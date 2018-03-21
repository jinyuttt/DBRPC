/**    
 * 文件名：NettySocket.java    
 *    
 * 版本信息：    
 * 日期：2018年3月19日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.Netty;

import INet.ISocket;
import io.netty.channel.ChannelHandlerContext;

/**    
 *     
 * 项目名称：Netty    
 * 类名称：NettySocket    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月19日 上午2:42:05    
 * 修改人：jinyu    
 * 修改时间：2018年3月19日 上午2:42:05    
 * 修改备注：    
 * @version     
 *     
 */
public class NettySocket implements ISocket {
    ChannelHandlerContext ctx=null;
    /* (non-Javadoc)    
     * @see INet.ISocket#send(byte[])    
     */
    public void send(byte[] data) {
        ctx.channel().writeAndFlush(data);

    }

    /* (non-Javadoc)    
     * @see INet.ISocket#close()    
     */
    public void close() {
        ctx.close();

    }

}