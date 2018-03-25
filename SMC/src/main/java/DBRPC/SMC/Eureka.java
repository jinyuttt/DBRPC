/**    
 * 文件名：Eureka.java    
 *    
 * 版本信息：    
 * 日期：2018年3月25日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.SMC;

/**    
 *     
 * 项目名称：SMC    
 * 类名称：Eureka    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月25日 下午12:39:35    
 * 修改人：jinyu    
 * 修改时间：2018年3月25日 下午12:39:35    
 * 修改备注：    
 * @version     
 *     
 */
public class Eureka {
    private ServiceManager instance=null;
   
    /**
     * 
     * @Title: registerServer   
     * @Description:注册服务
     * @param server
     * @param address
     * @param netType
     * @return      
     * boolean      
     * @throws
     */
    public boolean registerServer (String server,String address,String netType)
    {
        return instance.registerServer(server, address, netType);
    }
    
    /**
     * 
     * @Title: requestAddress   
     * @Description: 获取服务地址   
     * @param name
     * @return      
     * String      
     * @throws
     */
    public  String  requestAddress(String name)
    {
        ServiceInfo srv= instance.getService(name);
        if(srv!=null)
        {
            return   srv.address+":"+srv.port;
        }
        else
        {
            return  "";
        }
    }
    public byte[] requetServer(byte[]data)
    {
        return data;
        
    }
}
