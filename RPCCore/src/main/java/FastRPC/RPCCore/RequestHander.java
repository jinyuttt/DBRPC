/**    
 * 文件名：RequestHander.java    
 *    
 * 版本信息：    
 * 日期：2018年3月3日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package FastRPC.RPCCore;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**    
 *     
 * 项目名称：RPCCore    
 * 类名称：RequestHander    
 * 类描述：    分析接收参数
 * 创建人：jinyu    
 * 创建时间：2018年3月3日 上午1:46:55    
 * 修改人：jinyu    
 * 修改时间：2018年3月3日 上午1:46:55    
 * 修改备注：    
 * @version     
 *     
 */
public class RequestHander {
    private static ExecutorService server=Executors.newCachedThreadPool();
    public static int timeOut=6000;//10分钟
    public static volatile boolean isJob=true;
    
    /**
     * 
     * @Title: hander   
     * @Description: 参数分析 
     * @param request 主要实现
     * @return      返回数据
     * ReturnValue      
     * @throws
     */
private ReturnValue hander(byte[]request)
{
    ByteBuffer buf=ByteBuffer.wrap(request);
    int len=buf.getInt();
    byte[] name=new byte[len];
    buf.get(name);
    int rlen=buf.getInt();
    byte[] rType=new byte[rlen];
    buf.get(rType);
    byte[] param=new byte[buf.limit()-buf.position()];
    buf.get(param);
    String rpcName=new String(name).trim().toLowerCase();
    String rSer=new String(rType).trim().toLowerCase();
    RPCServiceInfo ser= RPCServer.getInstance().getSerivce(rpcName);
    ReturnValue rv=new ReturnValue();
    Object result = null;
   try {
       if(param.length>0)
       {
          NetParameter p=  (NetParameter) RPCSerialization.ConvertBitObj(param, NetParameter.class);
          ArrayList<RPCParameter> list=(ArrayList<RPCParameter>) p.list;
     HashMap<String,Object> hash = new HashMap<String,Object>();
    for(int i=0;i<list.size();i++)
     {
         RPCParameter tmp=list.get(i);
         Object v=null;
         if(tmp.clsType.equals("String"))
         {
             String cls= ser.hashPara.getOrDefault(tmp.name, null);
             if(cls!=null)
             {
                v=RPCSerialization.ConvertBasicObj(cls, tmp.value);
             }
             
         }
         else if(tmp.clsType.equals("json"))
         {
             v=RPCSerialization.ConvertJsonObj(tmp.value);
         }
         else if(tmp.clsType.equals("bit"))
         {
             String cls= ser.hashPara.getOrDefault(tmp.name, null);
             Class<?> klass = null;
            try {
                klass = Class.forName(cls);
            } catch (ClassNotFoundException e) {
              
                e.printStackTrace();
            }
             v=RPCSerialization.ConvertBitObj(tmp.value, klass);
         }
         else if(tmp.clsType.equals("byte[]"))
         {
             v=tmp.value;
         }
         hash.put(tmp.name, v);
     }
     Object[] args=new Object[ser.parameter.length];
    for(int i=0;i<args.length;i++)
    {
       String pname= ser.parameter[i];
       Object v=hash.getOrDefault(pname, null);
       if(v!=null)
       {
           args[i]=v;
       }
       else
       {
           String cls= ser.hashPara.getOrDefault(pname, null);
           if(cls!=null)
           {
               try
               {
               Class<?>  kclass=Class.forName(cls);
               args[i]=kclass.newInstance();
               }
               catch(Exception ex)
               {
                   rv.error=ex.getMessage();
                   ex.printStackTrace();
               }
           }
       }
    }
       
    try {
       result= ser.method.invoke(ser.getObj(), args);
    } catch (Exception e) {
        rv.error=e.getMessage();
        e.printStackTrace();
    }
       }
       else
       {
           try {
             result= ser.method.invoke(ser.getObj());
        } catch (Exception e) {
            rv.error=e.getMessage();
            e.printStackTrace();
        }
       }
       //
       if(result!=null)
       {
         rv.clsType=result.getClass().getName();
         boolean r=BasicType.isBasicType(result);
         if(r)
         {
             rv.value=String.valueOf(result).getBytes("UTF-8");
             rv.clsType="String";
         }
          else
          {
              if(result instanceof byte[]||result instanceof Byte[])
              {
                  rv.value=(byte[]) result;
                  rv.clsType="byte[]";
              }
            if(rSer.equals("json"))
             {
               rv.value=RPCSerialization.ConvertBitJson(result);
               rv.clsType="json";
             }
             else if(rSer.equals("bit"))
             {
                rv.value=RPCSerialization.ConvertBit(result);
                rv.clsType="bit";
              }
           }
       }
       return rv;
} 
   catch (IOException e) {
    
    e.printStackTrace();
}
return null;
    
}

/**
 * 
 * @Title: ConvertResult   
 * @Description: 封装传送值  
 * @param r 结果值
 * @return      
 * byte[]      
 * @throws
 */
private byte[] ConvertResult(ReturnValue r)
{
    try
    {
    byte[] error=r.error.getBytes("UTF-8");
    byte[] cls=r.clsType.getBytes("UTF-8");
    byte[] result=r.value;
    if(result==null)
    {
        result=new byte[0];
    }
    byte[] data=new byte[8+error.length+cls.length+result.length];
    ByteBuffer buf=ByteBuffer.wrap(data);
    buf.putInt(error.length);
    buf.put(error);
    buf.putInt(cls.length);
    buf.put(cls);
    buf.putInt(result.length);
    buf.put(result);
    return data;
    }
    catch(Exception ex)
    {
        return null;
    }
}

/**
 * 
 * @Title: process   
 * @Description: 处理参数调用 
 * @param param
 * @return      
 * byte[]      
 * @throws
 */
public byte[] process(byte[]param)
{
   
    ReturnValue v=null;
    if(isJob)
    {
         v=  taksJob(param);
    }
    else
    {
        v=executeJob(param);
    }
    return this.ConvertResult(v);
  
}

/**
 * 
 * @Title: executeJob   
 * @Description: 执行处理
 * @param param 
 * @return      
 * ReturnValue      
 * @throws
 */
private ReturnValue executeJob(byte[]param)
{
    try
    {
    ReturnValue r=this.hander(param);
    return r;
    }catch(Exception ex)
    {
        ex.printStackTrace();
    }
    return null;
}
private  ReturnValue taksJob(final byte[]param)
{
    ReturnValue r=new  ReturnValue();
    r.clsType="String";
    Callable<ReturnValue> callTask=new  Callable<ReturnValue>() {

        public ReturnValue call() throws Exception {
            ReturnValue r=null; 
           try
           {
             r=hander(param);
            return r;
           }
           catch(Exception ex)
           {
               r=new ReturnValue();
               r.error=ex.getMessage();
               r.clsType="String";
               ex.printStackTrace();
           }
        return r;
        }
        
    };
    FutureTask<ReturnValue> futureTask=new FutureTask<ReturnValue>(callTask);
    server.execute(futureTask);
    try {
         r=futureTask.get(timeOut, TimeUnit.SECONDS);
         return r;
    } catch (InterruptedException e) {
       r.error=e.getMessage();
        e.printStackTrace();
    } catch (ExecutionException e) {
        r.error=e.getMessage();
        e.printStackTrace();
    } catch (TimeoutException e) {
        r.error="执行超时";
      System.out.println("执行超时");
    }
    return r;
}
}
