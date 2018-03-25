/**    
 * 文件名：ServiceCenter.java    
 *    
 * 版本信息：    
 * 日期：2018年3月24日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.SMC;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
/**    
 *     
 * 项目名称：SMC    
 * 类名称：ServiceCenter    
 * 类描述：    服务信息中心
 * 创建人：jinyu    
 * 创建时间：2018年3月24日 上午1:09:43    
 * 修改人：jinyu    
 * 修改时间：2018年3月24日 上午1:09:43    
 * 修改备注：    
 * @version     
 *     
 */
public class ServiceCenter {
    private ConcurrentHashMap<String,List<ServiceInfo>> map=new ConcurrentHashMap<String,List<ServiceInfo>>();

    /**
     * 
     * @Title: addService   
     * @Description: 保存服务信息   
     * @param name
     * @param address
     * @param netType
     * @return      
     * boolean      
     * @throws
     */
 public synchronized  boolean addService(String name,String address,String netType)
{
   List<ServiceInfo> list= map.getOrDefault(name, null);
   if(list==null)
   {
       list=new ArrayList<ServiceInfo>();
       map.put(name, list);
   }
   String[]  serverAddr=address.split(":");
   ServiceInfo info=new ServiceInfo();
  info.address=serverAddr[0];
  info.port=Integer.valueOf(serverAddr[1]);
  info.serverName=name;
  if(!list.contains(info))
  {
      list.add(info);
  }
    return true;
}
 public  synchronized  List<ServiceInfo> getService(String name)
 {
  return   map.getOrDefault(name, null);
 }
}
