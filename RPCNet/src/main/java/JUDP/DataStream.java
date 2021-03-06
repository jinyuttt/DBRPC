/**    
 * 文件名：DataStream.java    
 *    
 * 版本信息：    
 * 日期：2018年2月28日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JUDP;

import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

import FastRPC.RPCNet.BufferManager;
import FastRPC.RPCNet.JSocket;
import FastRPC.RPCNet.PacketIn;
import INet.ISocketHander;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：DataStream    
 * 类描述：    数据处理
 * 创建人：jinyu    
 * 创建时间：2018年2月28日 上午2:07:54    
 * 修改人：jinyu    
 * 修改时间：2018年2月28日 上午2:07:54    
 * 修改备注：    
 * @version     
 *     
 */
public class DataStream {
    Session session=null;
    ISocketHander hander;
     private LinkedBlockingQueue<JSocket> queue=new LinkedBlockingQueue<JSocket>();
public DataStream(Session session) {
      this.session=session;
    }

/**
 * 
 * @Title: hander   
 * @Description: 处理接收的数据   
 * @param ip
 * @param port
 * @param data      
 * void      
 * @throws
 */
public void  hander(String localIP, int localPort,String ip,int port,byte[]data)
{
    PacketIn pack=new PacketIn(data,0,data.length);
    if(pack.Normalize())
    {
        String from=ip+":"+port;
        byte[] recBytes= BufferManager.getInstance().add(from, pack.getBuffer());
        if(recBytes==null)
        {
            return;
        }
        ByteBuffer buf=ByteBuffer.wrap(recBytes);
        byte type=buf.get();
   switch(type)
   {
        case 1:
          session.endpoint.send(ip, port, recBytes);
          break;
        case 0:
           JSocket recvicer=new JSocket();
           recvicer.buffer=recBytes;
           recvicer.srcIP=ip;
           recvicer.srcPort=port;
           recvicer.localIP=localIP;
           recvicer.localPort=localPort;
           JudpSocket c=new JudpSocket(session);
           c.addr=ip;
           c.port=port;
           recvicer.sockset=c;
           if(hander!=null)
           {
             hander.recvicer(recvicer);
           }
           else
           {
               try {
                queue.put(recvicer);
            } catch (InterruptedException e) {
              
                e.printStackTrace();
            }
           }
      break;
        case 2:
            LossPackaget p=new LossPackaget(recBytes);
            session.addLoss(ip, port, p.id, p.index);
            break;
        case 3:
            session.shutdown();
            break;
        case 4:
            session.shutdown();
            break;
            
    }
}

}
/**
 * 
 * @Title: getRec   
 * @Description: 返回接收的数据   
 * @return      
 * JSocket      
 * @throws
 */
public JSocket  getRec()
{
   try {
    return  queue.take();
} catch (InterruptedException e) {
  
    e.printStackTrace();
}
return null;
}


}
