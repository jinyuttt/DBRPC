/**    
 * 文件名：ServiceManager.java    
 *    
 * 版本信息：    
 * 日期：2018年3月24日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.SMC;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**    
 *     
 * 项目名称：SMC    
 * 类名称：ServiceManager    
 * 类描述：    注册服务，获取服务
 * 创建人：jinyu    
 * 创建时间：2018年3月24日 上午1:05:56    
 * 修改人：jinyu    
 * 修改时间：2018年3月24日 上午1:05:56    
 * 修改备注：    
 * @version     
 *     
 */
public class ServiceManager {
    
    /**
     * 服务保存
     */
   private  ServiceCenter manager=new ServiceCenter();
   
   /**
    * 服务调用
    */
   private ConcurrentHashMap<String,Short> callInfo=new  ConcurrentHashMap<String,Short>();
    /**
     * 
     * @Title: registerServer   
     * @Description: 注册服务 
     * @param server 服务名称
     * @param address  地址
     * @param netType  网络通信
     * @return      
     * boolean      
     * @throws
     */
public boolean registerServer(String server,String address,String netType)
{
    return manager.addService(server, address, netType);
    
}

/**
 * 
 * @Title: getService   
 * @Description:   互获取服务
 * @param name 服务名称
 * @return      
 * ServiceInfo      
 * @throws
 */
public  synchronized  ServiceInfo getService(String name)
{
    List<ServiceInfo>  list= manager.getService(name);
    if(list!=null)
    {
        //轮询
      Short index=     callInfo.getOrDefault(name, null);
      if(index==null)
      {
            callInfo.put(name, (short) 0);
            return list.get(0);
      }
      else
      {
          ServiceInfo info=  list.get(index);
          if(!info.isUseing)
          {
              return info;
          }
          else
          {
              //开启查找
          ServiceInfo find= search( list, index);
          if(find==null)
          {
              //没有找到，就继续使用当前的，根据使用时间来的
              return info;
          }
          }
      }
    }
    return null;
}

/**
 * 
 * @Title: search   
 * @Description: 查找可用服务
 * @param list
 * @param index
 * @return      
 * ServiceInfo      
 * @throws
 */
private  ServiceInfo search( List<ServiceInfo>  list,short index)
{
    int start=index;
    ServiceInfo  find=null;
   while(true)
   {
       ServiceInfo srv=  list.get(++start);
       if(!srv.isUseing)
       {
               find=srv;
               break;
       }
       if(start%list.size()==index)
       {
             //说明找了一圈
             break;
       }
   }
    return find;
    
}
}
