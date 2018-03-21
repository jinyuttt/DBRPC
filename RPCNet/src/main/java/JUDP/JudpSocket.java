/**    
 * 文件名：JudpSocket.java    
 *    
 * 版本信息：    
 * 日期：2018年3月1日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JUDP;

import INet.ISocket;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：JudpSocket    
 * 类描述：    客户端返回对象
 * 创建人：jinyu    
 * 创建时间：2018年3月1日 上午2:34:37    
 * 修改人：jinyu    
 * 修改时间：2018年3月1日 上午2:34:37    
 * 修改备注：    
 * @version     
 *     
 */
public class JudpSocket implements ISocket {

    Session session=null;
    public String addr;
    public int port;
  public JudpSocket (Session point)
  {
      session=point;
  }
  
    public void recvice(byte[] data) {
       

    }

   
    public void send(byte[] data) {
       
        session.sendData(addr, port, data);

    }

    /* (non-Javadoc)    
     * @see FastRPC.RPCNet.ISocket#close()    
     */
    public void close() {
        session.stop();

    }

}
