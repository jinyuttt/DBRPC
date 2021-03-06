/**    
 * 文件名：RPCUdpServer.java    
 *    
 * 版本信息：    
 * 日期：2018年2月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import FastRPC.RPCNet.BufferManager;
import FastRPC.RPCNet.JSocket;
import FastRPC.RPCNet.NetType;
import INet.IRPCNet;
import INet.ISocketHander;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：RPCUdpServer    
 * 类描述：    udp服务端
 * 创建人：jinyu    
 * 创建时间：2018年2月27日 上午1:34:07    
 * 修改人：jinyu    
 * 修改时间：2018年2月27日 上午1:34:07    
 * 修改备注：    
 * @version     
 *     
 */
@NetType("udp_Server")
public class RPCUdpServer extends Thread  implements IRPCNet{
    private DatagramSocket datagramSocket = null;
    private   int srcPort=0;
    private  String srcAddress="";
    private ISocketHander hander;
    private DatagramPacket datagramPacket;
    private int maxSzie=65535;
    private byte[] buffer=null;
    private volatile boolean isRun=true;
    public synchronized  void setSize(int size)
    {
        buffer=new byte[maxSzie];
    }
    public void run(){    
        while(isRun){   
           
            datagramPacket = new DatagramPacket(buffer, buffer.length);
            // receive()来等待接收UDP数据报
            try {
                datagramSocket.receive(datagramPacket);
                byte[] tmp=new byte[datagramPacket.getLength()];
                System.arraycopy(buffer, 0, tmp, 0, tmp.length);
                String from=datagramPacket.getAddress().getHostName()+":"+datagramPacket.getPort();
                byte[] recbytes=BufferManager.getInstance().add(from, tmp);
               if(recbytes!=null)
               {
                   JSocket jclient=new JSocket();
                   jclient.isTcpType=true;
                   udpSocket c=new udpSocket(datagramSocket);
                   c.srcIP=datagramPacket.getAddress().getHostName();
                   c.port=datagramPacket.getPort();
                   jclient.sockset=c;
                   hander.recvicer(jclient);
               }
            } catch (IOException e) {
               
                e.printStackTrace();
            }
           
          
        }    
    
      }   
    public void start(ISocketHander hander) {
     
        try {
            /******* 接收数据流程**/
            // 创建一个数据报套接字，并将其绑定到指定port上
            if(srcAddress.isEmpty())
            {
              datagramSocket = new DatagramSocket(srcPort);
            }
            else
            {
                InetAddress laddr=InetAddress.getByName(srcAddress);
                datagramSocket=new  DatagramSocket(srcPort, laddr);
            }
            this.start();
        }
        catch(Exception ex)
        {
            
        }
          
          
         
    }

   
    public int sendData(byte[] data) {
      
        return 0;
    }

    
    public byte[] recvice() {
       
        return null;
    }

  
    public String getAddress() {
         return this.srcAddress;
    }

  
    public void setAddress(String ip) {
      this.srcAddress=ip;

    }

    
    public int getPort() {
      return this.srcPort;
    }

   
    public void setPort(int port) {
      this.srcPort=port;
    }
    public void close() {
        isRun=false;
        datagramSocket.close();
        
    }
    
    /**
     * 无用
     */
    public boolean connect(String ip, int port) {
     
        return false;
    }
    
    /**
     * 无用
     */
    public void send(String ip, int port, byte[] data) {
     
        
    }
    
    /**
     * 设置地址
     */
    public boolean bind(String ip, int port) {
        this.srcAddress=ip;
        this.srcPort=port;
        return true;
    }

}
