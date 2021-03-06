/**    
 * 文件名：DataBuffer.java    
 *    
 * 版本信息：    
 * 日期：2018年2月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JUDP;

import java.util.HashMap;

import FastRPC.RPCNet.AppData;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：DataBuffer    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年2月27日 下午11:04:18    
 * 修改人：jinyu    
 * 修改时间：2018年2月27日 下午11:04:18    
 * 修改备注：    
 * @version     
 *     
 */
public class DataBuffer {
private HashMap<String,AppData[]> hash=new HashMap<String,AppData[]>();


public void add(String id,int index,int count,byte[]data)
{
    AppData[] app= hash.getOrDefault(id, null);
    if(app==null)
    {
         app=new AppData[count];
        hash.put(id, app);
    }
    app[index]=new AppData(data);
}


public byte[] getData(String id,int index)
{
    AppData[] app= hash.getOrDefault(id, null);
    if(app==null)
    {
        return null;
    }
    if(app[index]==null)
    {
        return null;
    }
    return app[index].get();
}

public void remove(String id,int index)
{
    AppData[] app= hash.getOrDefault(id, null);
    if(app!=null)
    {
        app[index]=null;
    }
}
public void remove(String id)
{
    hash.remove(id);
}
public boolean check()
{
    return hash.isEmpty();
}
}
