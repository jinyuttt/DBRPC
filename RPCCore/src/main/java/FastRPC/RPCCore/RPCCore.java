/**    
 * 文件名：RPCCore.java    
 *    
 * 版本信息：    
 * 日期：2018年3月3日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package FastRPC.RPCCore;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import Http.HttpServer;
import INet.IRPCNet;
import INet.ISocketHander;
import IRPCNet.RPCRecvicer;

/**    
 *     
 * 项目名称：RPCCore    
 * 类名称：RPCCore    
 * 类描述：    初始化
 * 创建人：jinyu    
 * 创建时间：2018年3月3日 下午1:05:38    
 * 修改人：jinyu    
 * 修改时间：2018年3月3日 下午1:05:38    
 * 修改备注：    
 * @version     
 *     
 */
public class RPCCore {
    public static String applicationDir="";
    HashMap<String,String> hash=new HashMap<String,String>();
public void  Init()
{
    Properties properties = new Properties();
    FileInputStream in = null;
    HashMap<String,String> map=new HashMap<String,String>();
    try {
        in = new FileInputStream(applicationDir+"/application.properties");
        properties.load(in);
        in.close();
     
    } catch (Exception e1) {
        e1.printStackTrace();
    }
    String addr= properties.getProperty("address","");
    String port=properties.getProperty("port","8888");
    String netType=properties.getProperty("nettype", "tcp");
    String netJar=properties.getProperty("netjar", "net.jar");
    String serverDir=properties.getProperty("server", "server");
    map.put("address", addr);
    map.put("port", port);
    map.put("nettype", netType);
    map.put("server", serverDir);
    map.put("netjar", netJar);
    hash.putAll(map);
}
public void init(HashMap<String,String> map)
{
     Init();
  
    if(map!=null)
    {
       hash.putAll(map);
    }
    
    
}
public void UtilInit()
{
    Init();
    String addr=hash.get("address");
    String port=hash.get("port");
    String netType=hash.get("nettype");
    String serverDir=hash.get("server");
    String netJar=hash.get("netjar");
    //
     StringBuffer buf=new StringBuffer();
     buf.append("addr:"+addr);
     buf.append("\r\n");
     buf.append("port:"+port);
     buf.append("\r\n");
     buf.append("netType:"+netType);
     buf.append("\r\n");
     buf.append("serverDir:"+serverDir);
     buf.append("\r\n");
     buf.append("netJar:"+netJar);
     buf.append("\r\n");
     System.out.println(buf.toString());
    //获取网络包
     HashMap<String,String>   netMap=  PackageUtil.getNet(applicationDir+"/"+netJar);
     //服务
     RPCRegister.registerPath(applicationDir+"/"+serverDir);
     //启动网络
     String netName=netType+"_Server";
     String clsName=netMap.getOrDefault(netName, null);
     if(clsName!=null)
     {
         try {
          Object netObj= Class.forName(clsName).newInstance();
          IRPCNet socket=(IRPCNet)netObj;
          socket.setAddress(addr);
          socket.setPort(Integer.valueOf(port));
          ISocketHander hander=new RPCRecvicer();
           socket.start(hander);
        } catch (Exception e) {
           
            e.printStackTrace();
        }
     }
     //启动http监听
     HttpServer httpSrc=new HttpServer();
     httpSrc.UtilInit(applicationDir+"/"+serverDir);
     httpSrc.start();
}
}
