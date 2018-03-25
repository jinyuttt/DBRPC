/**    
 * 文件名：ServiceInfo.java    
 *    
 * 版本信息：    
 * 日期：2018年3月24日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.SMC;

/**    
 *     
 * 项目名称：SMC    
 * 类名称：ServiceInfo    
 * 类描述：    服务信息
 * 创建人：jinyu    
 * 创建时间：2018年3月24日 上午1:12:50    
 * 修改人：jinyu    
 * 修改时间：2018年3月24日 上午1:12:50    
 * 修改备注：    
 * @version     
 *     
 */
public class ServiceInfo {
    
    /**
     * 服务名称
     */
public String serverName;

/**
 * 服务IP
 */
public String address;

/**
 * 服务端口
 */
public int port; 

/**
 * 正在使用
 */
public volatile boolean isUseing=false;

@Override
public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    ServiceInfo other = (ServiceInfo) obj;
   if(other.address.equals(this.address)&&other.port==this.port)
   {
       return true;
   }
   else
   {
       return false;
   }
}
}
