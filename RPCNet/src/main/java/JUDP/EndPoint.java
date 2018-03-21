/**    
 * 文件名：EndPoint.java    
 *    
 * 版本信息：    
 * 日期：2018年2月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import INet.ISocketHander;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：EndPoint    
 * 类描述：    网络发送
 * 创建人：jinyu    
 * 创建时间：2018年2月27日 下午10:44:04    
 * 修改人：jinyu    
 * 修改时间：2018年2月27日 下午10:44:04    
 * 修改备注：    
 * @version     
 *     
 */
public class EndPoint {
    private  DatagramSocket datagramSocket = null;
    private  HashMap<String,Session> sessions=new  HashMap<String,Session>();
    private boolean isStop=false;
    private ISocketHander hander=null;
   public EndPoint()
   {
       try {
        datagramSocket=new DatagramSocket();   
    } catch (SocketException e) {
      
        e.printStackTrace();
    }
   }
   public EndPoint(int port)
   {
       try {
        datagramSocket=new DatagramSocket(port);
    } catch (SocketException e) {
     
        e.printStackTrace();
    }
   }
   public EndPoint(String ip,int port)
   {
       InetAddress laddr = null;
    try {
        laddr = InetAddress.getByName(ip);
    } catch (UnknownHostException e) {
     
        e.printStackTrace();
    }
       try {
        datagramSocket=new  DatagramSocket(port, laddr);
    } catch (SocketException e) {
       
        e.printStackTrace();
    }
   }
   
   /**
    * 
    * @Title: addSession   
    * @Description:  添加通信对象 
    * @param from  来源地址 ip+":"+port
    * @param session      
    * void      
    * @throws
    */
   public void addSession(String from,Session session)
   {
       this.sessions.put(from, session);
   }
   
   /**
    * 
    * @Title: send   
    * @Description: 发送数据
    * @param addr  地址
    * @param port 端口
    * @param data  数据
    * void      
    * @throws
    */
   public void send(String addr,int port,byte[]data)
   {
       InetAddress ip = null;
       try {
           ip = InetAddress.getByName(addr);
       } catch (UnknownHostException e1) {
       
           e1.printStackTrace();
       }
       DatagramPacket p=new DatagramPacket(data, data.length, ip, port);
        try {
            datagramSocket.send(p);
        } catch (IOException e) {
         
            e.printStackTrace();
        }
   }
   
   /**
    * 
    * @Title: close   
    * @Description: 关闭通信         
    * void      
    * @throws
    */
   public void close()
   {
       isStop=true;
       datagramSocket.close();
   }
   
   /**
    * 
    * @Title: recvie   
    * @Description: 接收数据        
    * void      
    * @throws
    */
   private void recvie()
   {
       byte[] data=new byte[65535];
       while(!isStop)
       {
           DatagramPacket datagramPacket=new DatagramPacket(data, data.length);
           try {
               datagramSocket.receive(datagramPacket);
               byte[] tmp=new byte[datagramPacket.getLength()];
               System.arraycopy(data, 0, tmp, 0, tmp.length);
               String ip = datagramPacket.getAddress().getHostAddress();
               int port = datagramPacket.getPort();
               Session session= sessions.getOrDefault(ip+":"+port, null);
               String localIP=datagramSocket.getLocalAddress().getHostAddress();
               int localPort=datagramSocket.getLocalPort();
              if(session==null)
              {
                  session=new ClientSession();
                  session.hander=hander;
                  session.endpoint=this;
                  sessions.put(ip+":"+port, session);
              }
              session.getStream().hander(localIP,localPort,ip, port, data);
           } catch (IOException e) {
              
               e.printStackTrace();
           }
       }
   }
   
   /**
    * 
    * @Title: start   
    * @Description:  开启线程接收数据 
    * @param hander   回调对象   
    * void      
    * @throws
    */
   public void start(ISocketHander hander)
   {
       this.hander=hander;
       Thread rec=new Thread(new Runnable() {
        public void run() {
            recvie();
        }
           
       });
       rec.setDaemon(true);
       rec.setName("endpoint_");
       rec.start();
   }
}
