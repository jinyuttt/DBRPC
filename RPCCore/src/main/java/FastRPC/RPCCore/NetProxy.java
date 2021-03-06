/**
 * 
 */
package FastRPC.RPCCore;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import INet.IRPCNet;

/**
 * @author jinyu
 *  通信类
 */
public class NetProxy {
    public class InnerProxy
    {
        private  List<SocketProxy> socket;
        public String netType="tcp";
        public String address="127.0.0.1";
        public int port=8888;
        public volatile int index=0;
        public Class<?> cls=null;
        public String name="";
        public synchronized SocketProxy get()
        {
            if(socket.isEmpty())
            {
                SocketProxy p=new SocketProxy();
                try {
                    p.socket=(IRPCNet) cls.newInstance();
                    p.name=name;
                    p.address=address;
                    p.port=port;
                    p.socket.connect(address, port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return p;
            }
            SocketProxy proxy=  socket.remove(0);
            proxy.address=address;
            proxy.port=port;
            proxy.socket.connect(address, port);
            return proxy;
        }
        public synchronized void free(SocketProxy e)
        {
            socket.add(e);
        }
        
    }
    public class SocketProxy
    {
        private  IRPCNet socket;
        public String name="";
        public String address="127.0.0.1";
        public int port=8888;
        public void set(IRPCNet client)
        {
            this.socket=client;
        }
        public void close()
        {
            socket.close();
        }
        public void sendData(byte[]data)
        {
            socket.send(address, port, data);
        }
        public byte[] recvice()
        {
            return socket.recvice();
        }
    }
  private static NetProxy  instance=null;
  public static String netJar="net.jar";
  public  static int maxSize=5;
  private static boolean isInit=true;
  
  /**
   * 通信对象
   */
  private HashMap<String,InnerProxy> sockets=new HashMap<String,InnerProxy>();
  
  /**
   * 通信类信息
   */
  private HashMap<String,String> hashCls=new HashMap<String,String>();
  
  /**
   * 配置信息
   * 主要是服务地址
   */
  private HashMap<String,String> hash=new HashMap<String,String>();
  public static NetProxy getInstance()
  {
      if(instance==null)
      {
          instance=new NetProxy();
         
      }
      return instance;
  }
  public NetProxy()
  {
      
  }
  
  private  void init()
  {
      Properties properties = new Properties();
      FileInputStream in = null;
      HashMap<String,String> map=new HashMap<String,String>();
      try {
          in = new FileInputStream("client.properties");
          properties.load(in);
          in.close();
       
      } catch (Exception e1) {
          e1.printStackTrace();
      }
      String addr= properties.getProperty("address","tcp -h 127.0.0.1 -p 8888");
      netJar=properties.getProperty("netjar", "net.jar");
      map.put("address", addr);
      map.put("netjar", netJar);
      //
      for(Object key:properties.keySet())
      {
          map.put(key.toString().trim().toLowerCase(), properties.getProperty(key.toString()));
      }
      String size= map.getOrDefault("maxSzie", "5");
      maxSize=Integer.valueOf(size);
      hash.putAll(map);
      
  }
  
  /**
   * 注册服务地址
   * @Title: srcAddressInit   
   * @Description:   注册服务地址
   * @param map      
   * void      
   * @throws
   */
  public  void  srcAddressInit(HashMap<String,String> map)
  {
    if(map!=null)
    {
      hash.putAll(map);
    }
  }
 

 public void UtilInit()
  {
      init();
      hashCls=PackageUtil.getNet(netJar);
      isInit=false;
  }
  
  public synchronized SocketProxy getProxy(String name)
  {
      if(name==null||name.trim().isEmpty())
      {
          name="address";
      }
      String pname=name.trim().toLowerCase();
      InnerProxy tmp= sockets.getOrDefault(pname, null);
      if(tmp==null)
      {      if(isInit)
            {
                 this.UtilInit();
            }
              tmp=new InnerProxy();
              tmp.socket=new ArrayList<SocketProxy>();
              String host=hash.getOrDefault(pname, null);
              tmp.name=name.trim().toLowerCase();
              if(host==null)
              {
                  host=hash.getOrDefault("address", null);
              }
              if(host!=null)
              {
                  host=host.trim().toLowerCase();
                  String[]type=host.split(" ");
                  StringBuffer buf=new StringBuffer();
                  for(int j=0;j<type.length;j++)
                  {
                      if(!type[j].trim().isEmpty())
                      {
                          buf.append(type[j].trim().toLowerCase()+";");
                      }
                  }
                  //
                  String[] addr=buf.toString().split(";");
                  tmp.netType=addr[0].trim();
                  for(int k=1;k<addr.length;k++)
                  {
                      if(addr[k].equals("-h"))
                      {
                          tmp.address=addr[k+1].trim();
                      }
                      else if(addr[k].equals("-p"))
                      {
                          tmp.port=Integer.valueOf(addr[k+1].trim());
                      }
                  }
                  //
                  String clsName= hashCls.getOrDefault(tmp.netType+"_Client", null);
                  if(clsName==null)
                  {
                      clsName= hashCls.getOrDefault("tcp_Client", null);
                  }
                  if(clsName!=null)
                  {
                     Class<?> klss = null;
                    try {
                        klss = Class.forName(clsName);
                    } catch (ClassNotFoundException e1) {
                     
                        e1.printStackTrace();
                    }
                     tmp.cls=klss;
                    for(int i=0;i<maxSize;i++)
                    {
                       SocketProxy p=new SocketProxy();
                       p.name=name.trim().toLowerCase();
                       IRPCNet socket = null;
                        try {
                            socket = (IRPCNet)klss.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                         p.socket=socket;
                         tmp.socket.add(p);
                   
                    }
                  }
              
          }
             
           sockets.put(name, tmp);  
      }
     
    return tmp.get();
      
  }
  public void freeProxy(SocketProxy proxy)
  {
      proxy.socket.close();
      InnerProxy inner=  sockets.get(proxy.name);
      inner.free(proxy);
  }
}
