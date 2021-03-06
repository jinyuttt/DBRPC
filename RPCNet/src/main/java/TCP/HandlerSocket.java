/**    
 * 文件名：HandlerThread.java    
 *    
 * 版本信息：    
 * 日期：2018年2月22日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package TCP;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import FastRPC.RPCNet.BufferManager;
import FastRPC.RPCNet.JSocket;
import INet.ISocketHander;
/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：HandlerThread    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年2月22日 下午2:32:54    
 * 修改人：jinyu    
 * 修改时间：2018年2月22日 下午2:32:54    
 * 修改备注：    
 * @version     
 *     
 */
public class HandlerSocket {
    ExecutorService executorService = Executors.newCachedThreadPool();
    private int maxSzie=65535;
    private byte[] buffer=new byte[maxSzie];
    public synchronized  void setSize(int size)
    {
        maxSzie=size;
        buffer=new byte[maxSzie];
    }
    Socket client=null;
  ISocketHander hander;
    public HandlerSocket(Socket client, ISocketHander hander)
    {
        this.client=client;
        this.hander=hander;
        buffer=new byte[maxSzie];
    }
    public void Process() {
        
        executorService.execute(new Runnable() {

            public void run() {
                DataInputStream inputStream = null;
                try {
                    inputStream = new DataInputStream(client.getInputStream());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }//得到一个输入流，接收客户端传递的信息
            
              
                String ip=client.getInetAddress().getHostAddress();
                int port=client.getPort();
                String from=ip+":"+port;
                while(true)
                {
                   try {
                    int r=inputStream.read(buffer, 0, buffer.length);
                    if(r==-1)
                    {
                        break;
                    }
                    else
                    {
                            byte[] tmp=new byte[r];
                            System.arraycopy(buffer, 0, tmp, 0, r);
                           byte[] recbytes= BufferManager.getInstance().add(from, tmp);
                           if(recbytes!=null)
                           {
                               JSocket jclient=new JSocket();
                               TcpSocket c=new TcpSocket(client);
                               jclient.isTcpType=true;
                               jclient.sockset=c;
                               jclient.buffer=recbytes;
                               hander.recvicer(jclient);
                           }
                    }
                } catch (IOException e) {
                  
                    e.printStackTrace();
                }
                }
            }
            
        });
    }

}
