/**    
 * 文件名：DataRow.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JXMLTable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.alibaba.fastjson.JSONObject;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataRow    
 * 类描述：    行信息
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午5:20:08    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午5:20:08    
 * 修改备注：    
 * @version     
 *     
 */
public class DataXmlRow {
    
    /**
     * name value
     */
private HashMap<String,Object>  rowValue=new HashMap<String,Object>();

private HashMap<String,DataXmlColumn> hashColumn=null;
/**
 * id,name
 */
private HashMap<String,String>  keyName=new HashMap<String,String>();
private int[] keyindexs=null;
private int index=0;
public int id=-1;
public void setColumn(List<DataXmlColumn> list)
{
    if(list==null)
    {
        return;
    }
    hashColumn=new HashMap<String,DataXmlColumn>();
      for(int i=0;i<list.size();i++)
      {
          DataXmlColumn col=list.get(i);
          hashColumn.put(col.columnName, col);
      }
}
/**
 * 
 * @Title: loadRow   
 * @Description: 添加数据
 * @param obj
 * @return      
 * boolean      
 * @throws
 */
public boolean loadRow(Object[]obj)
{
    if(obj==null||obj.length<keyName.size())
    {
        return false;
    }
  
    //
   for(int i=0;i<keyindexs.length;i++)
   {
       setValue(keyindexs[i],obj[i]);
   }
return true;
}

/**
 * 
 * @Title: getRow   
 * @Description: 获取行数据，行数据是JSON格式
 * @return      
 * String      
 * @throws
 */
public String getRow()
{
   JSONObject data=new JSONObject();
    Iterator<Map.Entry<String, String>>  it=  keyName.entrySet().iterator();  
      while(it.hasNext()){  
          Map.Entry<String, String> item=   it.next();  
          String key=item.getKey();  
          Object value=rowValue.getOrDefault(item.getValue(),null);  
           data.put(key, value);
      }  
return data.toJSONString();
}

public void setIndex(int index)
{
    this.index=index;
}
public int getIndex()
{
    return index;
}

/**
 * 
 * 创建一个新的实例 DataXmlRow.    
 *    
 * @param map
 * @param id
 */
public DataXmlRow(HashMap<String,String> map,int id)
{
    keyName=map;
    keyindexs=new int[keyName.size()];
    Iterator<Entry<String, String>> it= keyName.entrySet().iterator();
    int index=0;
    while(it.hasNext())
    {
        Entry<String, String> v=it.next();
        String key=v.getKey();
        String value=v.getValue();
        rowValue.put(value, null);//初始化
        keyindexs[index++]=Integer.valueOf(key);
    }
    Arrays.sort(keyindexs);
}

/**
 * 
 * 创建一个新的实例 DataXmlRow.    
 *    
 * @param index
 * @param map
 * @param id
 */
public  DataXmlRow(HashMap<String,String> index,HashMap<String,Object> map,int id)
{
    keyName=index;//id,name map:id value
     Iterator<Entry<String, Object>> it = map.entrySet().iterator();
     while(it.hasNext())
     {
        Entry<String, Object> item = it.next();
         String name=keyName.getOrDefault(item.getKey(),null);
                 if(name!=null)
                 {
                     rowValue.put(name, item.getValue());
                 }
     }
    
}

/**
 * 
 * @Title: setValue   
 * @Description: 设置数据  
 * @param columnName
 * @param value      
 * void      
 * @throws
 */
public void setValue(String columnName,Object value)
{
    rowValue.put(columnName, value);
}

/**
 * 
 * @Title: setValue   
 * @Description: 设置数据  
 * @param column
 * @param value      
 * void      
 * @throws
 */
public void setValue(int column,Object value)
{
    if(column>keyindexs.length)
    {
        return;
    }
  String name=  keyName.getOrDefault(keyindexs[column], null);
  if(name!=null)
  {
      rowValue.put(name, value);
  }
}

/**
 * 
 * @Title: getValue   
 * @Description: 获取数据   
 * @param columnName
 * @return      
 * Object      
 * @throws
 */
public Object getValue(String columnName)
{
   Object obj=  rowValue.getOrDefault(columnName, null);
   if(hashColumn!=null)
   {
         DataXmlColumn cls=hashColumn.getOrDefault(columnName, null);
         if(cls!=null)
         {
             try
             {
               Object tmp=   Class.forName(cls.StringType).newInstance();
               return   Class.forName(cls.StringType).getMethod("ValueOf", String.class).invoke(tmp, obj);
             }
             catch(Exception ex)
             {
                 
             }
         }
   }
return obj;
}

/**
 * 
 * @Title: getValue   
 * @Description:获取数据
 * @param column
 * @return      
 * Object      
 * @throws
 */
public Object getValue(int column)
{
    if(column>keyindexs.length)
    {
        return null;
    }
    String name=  keyName.getOrDefault(keyindexs[column], null);
  return  getValue(name);
}
}
