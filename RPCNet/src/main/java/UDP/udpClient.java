package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

import FastRPC.RPCNet.BufferManager;
import FastRPC.RPCNet.JSocket;
import FastRPC.RPCNet.NetType;
import FastRPC.RPCNet.Subpackage;
import INet.IRPCNet;
import INet.ISocketHander;

@NetType("udp_Client")
public class udpClient implements IRPCNet {
    DatagramSocket datagramSocket = null;
    public String srcIP="";
    public int  srcPort=0;
    private ISocketHander hander=null;
    private  volatile boolean isRun=false;
    private LinkedBlockingQueue<JSocket> queue=new LinkedBlockingQueue<JSocket>();
   public udpClient()
   {
       try {
        datagramSocket=new DatagramSocket();
    } catch (SocketException e) {
    
        e.printStackTrace();
    }
   }
    
    public udpClient(DatagramSocket datagramSocket) {
      this.datagramSocket=datagramSocket;
}

    public void recvice(byte[] data) {
    
    }

    /**
     * 发送数据
     * 通过 srcIP,srcPort
     */
    public void send(byte[] data) {
     
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(srcIP);
        } catch (UnknownHostException e1) {
        
            e1.printStackTrace();
        }
        // 创建发送类型的数据报：  
       
          short index=0;
          int id=Subpackage.getPackagetId();
         byte[]tmp= null;
              do
              {
                 tmp=Subpackage.subpackaget(data, index, id);
                 if(tmp!=null)
                 {
                     DatagramPacket sendPacket = new DatagramPacket(tmp, tmp.length, ip,srcPort);
                     try {
                        datagramSocket.send(sendPacket);
                    } catch (IOException e) {
                    
                        e.printStackTrace();
                    }
                     index++;
                 }
              }while(tmp!=null);
       
    }

    /**
     * 关闭
     */
    public void close() {
        datagramSocket.close();

    }

    /**
     * 绑定本地IP
     */
    public boolean bind(String ip, int port) {
        if(ip==null||ip.trim().isEmpty())
        {
            try {
                ip=InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
               
                e.printStackTrace();
                return false;
            }
        }
        SocketAddress addr=new InetSocketAddress(ip,port);
        try {
            datagramSocket.bind(addr);
        } catch (SocketException e) {
           
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 无用
     */
    public boolean connect(String ip, int port) {
      
        return true;
    }

    /**
     * 发送数据
     */
    public void send(String ip, int port, byte[] data) {
        InetAddress rip = null;
        try {
           rip = InetAddress.getByName(ip);
        } catch (UnknownHostException e1) {
        
            e1.printStackTrace();
        }
        // 创建发送类型的数据报：  
       
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

    public void start(ISocketHander hander) {
       
        this.hander=hander;
        startThread();
    }

    private void startThread() {
        if(isRun)
        {
            return;
        }
        isRun=true;
        Thread rec=new Thread(new Runnable() {

            public void run() {
               byte[] rec=new byte[65535];
               DatagramPacket datagramPacket = new DatagramPacket(rec, rec.length);
              while(isRun)
              {
                  try {
                    
                    datagramSocket.receive(datagramPacket);
                      byte[] tmp=new byte[datagramPacket.getLength()];
                      System.arraycopy(rec, 0, tmp, 0, tmp.length);
                      String from=datagramPacket.getAddress().getHostName()+":"+datagramPacket.getPort();
                      byte[] recbytes=BufferManager.getInstance().add(from, tmp);
                     if(recbytes!=null)
                     {
                         JSocket recvicer=new JSocket();
                         recvicer.isTcpType=true;
                         udpSocket c=new udpSocket(datagramSocket);
                         c.srcIP=datagramPacket.getAddress().getHostName();
                         c.port=datagramPacket.getPort();
                         recvicer.sockset=c;
                         hander.recvicer(recvicer);
                         if(hander!=null)
                         {
                           
                           //说明是回执
                             hander.recvicer(recvicer);
                         }
                         else
                         {
                           //说明是回执
                             try {
                               queue.put(recvicer);
                           } catch (InterruptedException e) {
                             
                               e.printStackTrace();
                           }
                             //说明是调用recvice方法；可以跳出
                             isRun=false;
                             break;
                     }
                     }
                  } catch (IOException e) {
                     
                      e.printStackTrace();
                  }
              }
                
            }
            
        });
        rec.setDaemon(true);
        rec.setName("tcp_client");
        if(!rec.isAlive())
        {
           rec.start();
        }
        
    }

    public int sendData(byte[] data) {
       
        return 0;
    }

    public byte[] recvice() {
      
        return null;
    }

    public String getAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAddress(String ip) {
        // TODO Auto-generated method stub
        
    }

    public int getPort() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setPort(int port) {
        // TODO Auto-generated method stub
        
    }

}
