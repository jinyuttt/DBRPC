/**    
 * 文件名：TcpClient.java    
 *    
 * 版本信息：    
 * 日期：2018年2月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package TCP;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
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

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：TcpClient    
 * 类描述：    tcp客户端
 * 创建人：jinyu    
 * 创建时间：2018年2月27日 上午12:59:17    
 * 修改人：jinyu    
 * 修改时间：2018年2月27日 上午12:59:17    
 * 修改备注：    
 * @version     
 *     
 */
@NetType("tcp_Client")
public class TcpClient implements IRPCNet {
    private  Socket client=null;
    private ISocketHander hander=null;
    private  volatile boolean isRun=false;
    private LinkedBlockingQueue<JSocket> queue=new LinkedBlockingQueue<JSocket>();
  public TcpClient(Socket client)
  {
      this.client=client;
  }
  public TcpClient()
  {
      this.client=new Socket();
  }
  
  /**
   * 发送数据
   */
    public void send(byte[] data) {
      DataOutputStream stream = null;
    try {
        stream = new   DataOutputStream(client.getOutputStream());
    } catch (IOException e1) {
     
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
                 try {
                    stream.write(tmp);
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
      try {
          isRun=false;
        client.close();
    } catch (IOException e) {
    
        e.printStackTrace();
    }

    }

    /**
     * 绑定数据
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
        SocketAddress bindpoint=new InetSocketAddress(ip,port);
        try {
            client.bind(bindpoint);
        } catch (IOException e) {
         
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 发送数据
     */
    public void send(String ip, int port, byte[] data) {
      
        send(data);
    }
    
    /**
     * 链接数据
     */
    public boolean connect(String ip, int port) {
        SocketAddress endpoint=new InetSocketAddress(ip,port);
        try {
            client.connect(endpoint);
        } catch (IOException e) {
           
            e.printStackTrace();
            return false;
        }
        return true;
    }
   
    public void start(ISocketHander hander) {
      this.hander=hander;
      startThread();
    }
    
    /**
     * 开启线程接收
     * @Title: startThread   
     * @Description: 开启线程接收         
     * void      
     * @throws
     */
    private void startThread()
    {
        if(isRun)
        {
            return;
        }
        isRun=true;
        Thread rec=new Thread(new Runnable() {

            public void run() {
               byte[] rec=new byte[65535];
              while(isRun)
              {
                
                  try {
                      if(client.isClosed())
                      {
                          break;
                      }
                  int r= client.getInputStream().read(rec);
                  byte[] tmp=new byte[r];
                  System.arraycopy(rec, 0, tmp, 0, r);
                  String from=client.getRemoteSocketAddress().toString();
                  byte[] recBytes=BufferManager.getInstance().add(from, tmp);
                  if(recBytes!=null)
                  {
                      JSocket recvicer=new JSocket();
                      recvicer.buffer=recBytes;
                      recvicer.localIP=client.getLocalAddress().getHostName();
                      recvicer.localPort=client.getLocalPort();
                      recvicer.srcIP=client.getRemoteSocketAddress().toString();
                      recvicer.srcPort=client.getPort();
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
                  
                
               } 
                  catch(SocketException ex)
                  {
                      isRun=false;
                      break;
                  }
                  catch (IOException e) {
              
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
        send(data);
        return 0;
    }
    public byte[] recvice() {
        this.startThread();
      try {
        return queue.take().buffer;
    } catch (InterruptedException e) {
      
        e.printStackTrace();
    }
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
