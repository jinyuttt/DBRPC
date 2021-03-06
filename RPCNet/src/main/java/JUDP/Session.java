package JUDP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import FastRPC.RPCNet.JSocket;
import FastRPC.RPCNet.Subpackage;
import INet.ISocketHander;

/**    
 * 文件名：Session.java    
 *    
 * 版本信息：    
 * 日期：2018年2月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：Session    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年2月27日 下午10:43:11    
 * 修改人：jinyu    
 * 修改时间：2018年2月27日 下午10:43:11    
 * 修改备注：    
 * @version     
 *     
 */
public abstract class Session {
  public EndPoint endpoint;
  private HashMap<String,DataBuffer> hashCache=new HashMap<String,DataBuffer>();
  private List<CacheBuffer> sendBuf=new ArrayList<CacheBuffer>();
  private List<CacheBuffer> lossBuf=new ArrayList<CacheBuffer>();
  private int maxSize=1000;
  private int num=0;
  private DataStream dataStream=null;
  private HashMap<String,Long> hashSpeed=new HashMap<String,Long>();
  private List<String> address=new ArrayList<String>();
  private  boolean isStop=true;
  public ISocketHander hander;
  
  public DataStream getStream()
  {
      if(dataStream==null)
      {
          dataStream=new DataStream(this);
      }
      return dataStream;
  }
  
  /**
   * 
   *     获取接受数据
   *     如果没有传入回调对象，则空调使用该方法接受
     
   * @param       
     
   * @param  @return    设定文件    
     
   * @return String    DOM对象    
     
   * @Exception 异常对象
   */
  public JSocket getRecvice()
  {
    return getStream().getRec(); 
  }
  /**
   * 
   * 发送数据 
     
   * @param       
     
   * @param  @return    设定文件    
     
   * @return String    DOM对象    
     
   * @Exception 异常对象
   */
  public  void sendData(String addr,int port,byte[]data)
  {
      this.start();
     int id= Subpackage.getPackagetId();
     int count=Subpackage.getCount(data.length);
     short index=0;
      byte[]tmp= null;
         do
         {
            tmp=Subpackage.subpackaget(data, index, id);
            if(tmp!=null)
            {
                endpoint.send(addr, port, tmp);
                index++;
                add(addr,port, String.valueOf(id), index, count, tmp);
            }
         }while(tmp!=null);
         endpoint.addSession(addr+":"+port, this);
  }
  protected void add(String addr,int port,String id,int index,int count,byte[]data)
  {
      DataBuffer buf= hashCache.getOrDefault(addr, null);
      if(buf==null)
      {
          buf=new DataBuffer();
          hashCache.put(addr, buf);
      }
      buf.add(id, index, count, data);
      CacheBuffer e=new CacheBuffer();
      e.id=Integer.valueOf(id);
      e.index=index;
      e.srcIP=addr;
      e.srcPort=port;
      sendBuf.add(e);
      num++;
      if(num>maxSize)
      {
          try {
            Thread.sleep(50);
        } catch (InterruptedException e1) {
         
            e1.printStackTrace();
        }
      }
      if(num>maxSize*1.75)
      {
          try {
            Thread.sleep(200);
        } catch (InterruptedException e1) {
        
            e1.printStackTrace();
        }
      }
  }
  protected void sendSpeed(String ip,int port)
  {
     Long time=hashSpeed.getOrDefault(ip, null);
     if(time==null)
     {
         hashSpeed.put(ip, System.currentTimeMillis());
         address.add(ip+":"+port);
     }
     SpeedPackaget p=new SpeedPackaget();
     endpoint.send(ip, port, p.get()); 
  }

  public  void addLoss(String ip,int port,int id,int index)
  {
      CacheBuffer e=new CacheBuffer();
      e.id=id;
      e.srcIP=ip;
              e.srcPort=port;
              lossBuf.add(e);   
  }
  
  /**
   * 处理发送的数据
   * @Title: start   
   * @Description: TODO(这里用一句话描述这个方法的作用)         
   * void      
   * @throws
   */
  public void start()
  {
      if(!isStop)
      {
         return ;
      }
      isStop=false;
      Thread check=new Thread(new Runnable() {
        public void run() {
          while(!isStop)
          {
             int isNull=2;
              if(!lossBuf.isEmpty())
              {
                  int size=lossBuf.size();
                  List<CacheBuffer> list=new ArrayList<CacheBuffer>();
                  for(int i=0;i<size;i++)
                  {
                      CacheBuffer buf= lossBuf.get(i);
                      DataBuffer dbuf= hashCache.get(buf.srcIP+":"+buf.srcPort);
                      if(dbuf!=null)
                      {
                         endpoint.send(buf.srcIP, buf.srcPort, dbuf.getData(String.valueOf(buf.id), buf.index));
                      }
                      list.add(buf);
                  }
                  lossBuf.removeAll(list);
                  isNull--;
              }
              //
              if(!sendBuf.isEmpty())
              {
                  int size=sendBuf.size();
                  for(int i=0;i<size;i++)
                  {
                      CacheBuffer buf=sendBuf.get(i);
                      if(System.currentTimeMillis()-buf.time>50)
                      {
                          lossBuf.add(buf);
                      }
                  }
                  isNull--;
              }
              //
              for(int i=0;i<address.size();i++)
              {
                  String[] p=address.get(i).split(":");
                  sendSpeed(p[0],Integer.valueOf(p[1]));
              }
              //
              try {
                  if(isNull==0)
                  {
                      Thread.sleep(200);
                  }
                Thread.sleep(50);
            } catch (InterruptedException e) {
               
                e.printStackTrace();
            }
          }
        }
          
      });
      check.setDaemon(true);
      check.setName("session_");
      check.start();
      // 启动接收
      this.endpoint.start(hander);
  }

   public void remove(String addr)
   {
       hashCache.remove(addr);
       hashSpeed.remove(addr);
   }
   public void remove(String addr,String id)
   {
       DataBuffer e=hashCache.get(addr);
       e.remove(id);
   }
   public void remove(String addr,String id,int index)
   {
       DataBuffer e=hashCache.get(addr);
       e.remove(id,index);
   }
   public void shutdown()
   {
       isStop=true;
       hashCache.clear();
       sendBuf.clear();
       lossBuf.clear();
       hashSpeed.clear();
       endpoint.close();
   }
   public void stop()
   {
       do
       {
           if(hashCache.isEmpty())
           {
               shutdown();
           }
           try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        
            e.printStackTrace();
        }
       }
       while(!hashCache.isEmpty());
   }
}
