/**    
 * 文件名：JSocket.java    
 *    
 * 版本信息：    
 * 日期：2018年2月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package FastRPC.RPCNet;

import INet.ISocket;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：JSocket    
 * 类描述：    封装操作接口
 * 创建人：jinyu    
 * 创建时间：2018年2月27日 上午12:15:55    
 * 修改人：jinyu    
 * 修改时间：2018年2月27日 上午12:15:55    
 * 修改备注：    
 * @version     
 *     
 */
public class JSocket {
   public ISocket sockset;
   public String srcIP;
   public int  srcPort;
   public String localIP;
   public int localPort;
   public boolean isTcpType=false;
   public byte[] buffer;
}
