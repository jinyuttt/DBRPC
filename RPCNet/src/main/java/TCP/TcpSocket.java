/**    
 * 文件名：TcpSocket.java    
 *    
 * 版本信息：    
 * 日期：2018年3月3日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package TCP;

import java.net.Socket;

import INet.ISocket;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：TcpSocket    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月3日 下午3:03:28    
 * 修改人：jinyu    
 * 修改时间：2018年3月3日 下午3:03:28    
 * 修改备注：    
 * @version     
 *     
 */
public class TcpSocket implements ISocket {
    Socket client=null;
    public TcpSocket(Socket socket)
    {
        this.client=socket;
    }
    /* (non-Javadoc)    
     * @see INet.ISocket#connect(java.lang.String, int)    
     */
    public boolean connect(String ip, int port) {
      
        return false;
    }

    /* (non-Javadoc)    
     * @see INet.ISocket#send(byte[])    
     */
    public void send(byte[] data) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)    
     * @see INet.ISocket#send(java.lang.String, int, byte[])    
     */
    public void send(String ip, int port, byte[] data) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)    
     * @see INet.ISocket#close()    
     */
    public void close() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)    
     * @see INet.ISocket#bind(java.lang.String, int)    
     */
    public boolean bind(String ip, int port) {
        // TODO Auto-generated method stub
        return false;
    }

}
