/**    
 * 文件名：udpSocket.java    
 *    
 * 版本信息：    
 * 日期：2018年3月3日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import FastRPC.RPCNet.Subpackage;
import INet.ISocket;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：udpSocket    
 * 类描述：   回发数据 
 * 创建人：jinyu    
 * 创建时间：2018年3月3日 下午3:17:48    
 * 修改人：jinyu    
 * 修改时间：2018年3月3日 下午3:17:48    
 * 修改备注：    
 * @version     
 *     
 */
public class udpSocket implements ISocket {
    DatagramSocket datagramSocket=null;
    public String srcIP="";
    public int port=0;
  public udpSocket(DatagramSocket client)
  {
      datagramSocket=client;
  }
    /* (non-Javadoc)    
     * @see INet.ISocket#send(byte[])    
     */
    public void send(byte[] data) {
        InetAddress rip = null;
        try {
           rip = InetAddress.getByName(srcIP);
        } catch (UnknownHostException e1) {
        
            e1.printStackTrace();
        }
        short index=0;
        int id=Subpackage.getPackagetId();
        byte[]tmp= null;
         do
            {
               tmp=Subpackage.subpackaget(data, index, id);
               if(tmp!=null)
               {
                   DatagramPacket sendPacket = new DatagramPacket(tmp, tmp.length, rip,port);
                   try {
                      datagramSocket.send(sendPacket);
                  } catch (IOException e) {
                  
                      e.printStackTrace();
                  }
                   index++;
               }
            }while(tmp!=null);
     

    }

    /* (non-Javadoc)    
     * @see INet.ISocket#close()    
     */
    public void close() {
      

    }

}
