/**    
 * 文件名：DataTable.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JXMLTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataTable    
 * 类描述：    表描述
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午4:26:03    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午4:26:03    
 * 修改备注：    
 * @version     
 *     
 */
public class DataXMLTable {
   private int rd=(int) Math.round(Math.random()*8999+100);
 private   Document document =null;
 private   String tableXml="<table name=\"\" caption=\"\" rowNum=\"\", colomnNum=\"\">\r\n" + 
            "<columnCollection>\r\n" + 
            "</columnCollection>\r\n" + 
            "<RowCollection>\r\n" + 
            "<Row>\r\n" + 
            "<\r\n" + 
            "</Row>\r\n" + 
            "</RowCollection>\r\n" + 
            "</table>";
 private  int columnIndex=0;
 private int rowIndex=0;
    public DataXMLTable()
    {
         try {
            document = DocumentHelper.parseText(tableXml);
        } catch (DocumentException e) {
        
            e.printStackTrace();
        }  
    }
    
    /**
     * 
     * @Title: clear   
     * @Description: 清除信息   
     * void      
     * @throws
     */
    public void clear()
    {
        Element columnCollection= document.getRootElement().element("columnCollection");
        document.getRootElement().remove(columnCollection);
        document.getRootElement().addElement("columnCollection");
        Element rowCollection= document.getRootElement().element("RowCollection");
        document.getRootElement().remove(rowCollection);
        document.getRootElement().addElement("RowCollection");
        this.columnIndex=0;
        this.rowIndex=0;
    }
    
    /**
     * 
     * @Title: removeRows   
     * @Description: 删除所有行
     * void      
     * @throws
     */
    public void removeRows()
    {
        Element rowCollection= document.getRootElement().element("RowCollection");
        document.getRootElement().remove(rowCollection);
        document.getRootElement().addElement("RowCollection");
        this.rowIndex=0;
    }
    
    /**
     * 
     * @Title: removeRow   
     * @Description: 删除指定行
     * @param index      
     * void      
     * @throws
     */
    public void removeRow(int index)
    {
        Element rowCollection= document.getRootElement().element("RowCollection");
        List<?> rows=  rowCollection.elements("Row");
        Element row=(Element) rows.get(index);
        rowCollection.remove(row);
    }
    
    /**
     * 
     * @Title: removeColumn   
     * @Description: 删除指定列
     * @param index
     * @return      
     * boolean      
     * @throws
     */
    public boolean removeColumn(int index)
    {
        Element columnCollection= document.getRootElement().element("columnCollection");
        List<?> columnE= columnCollection.elements("Column");
        Element col=(Element) columnE.get(index);
        return columnCollection.remove(col);
    }
    
    /**
     * 
     * @Title: removeColumn   
     * @Description: 删除指定列   
     * @param name
     * @return      
     * boolean      
     * @throws
     */
    public boolean removeColumn(String name)
    {
        Element columnCollection= document.getRootElement().element("columnCollection");
        List<?> columnE= columnCollection.elements("Column");
        for(int i=0;i<columnE.size();i++)
        {
            Element col= (Element) columnE.get(i);
           Attribute colName= col.attribute("name");
           if(colName.getValue().equals(name))
           {
               columnCollection.remove(col);
               break;
           }
        }
        return true;
    }
    
    /**
     * 
     * @Title: addColumn   
     * @Description: 添加列信息
     * @param column      
     * void      
     * @throws
     */
    public  void addColumn(DataXmlColumn column)
    {
        Element columnCollection= document.getRootElement().element("columnCollection");
        Element columnE= columnCollection.addElement("Column");
        columnE.addAttribute("id", String.valueOf(columnIndex++));
        columnE.addAttribute("name", column.columnName);
        columnE.addAttribute("caption", column.caption);
        columnE.addAttribute("IntType", column.IntType);
        columnE.addAttribute("StringType", column.StringType);
    }
    
    /**
     * 
     * @Title: getColumns   
     * @Description: 返回列信息
     * @return      
     * List<DataXmlColumn>      
     * @throws
     */
    private List<DataXmlColumn> getColumns()
    {
        Element columnCollection= document.getRootElement().element("columnCollection");
        List<?> nodes= columnCollection.elements("Column");
        List<DataXmlColumn> list=new ArrayList<DataXmlColumn>();
        for(int i=0;i<nodes.size();i++)
        {
            DataXmlColumn col=new DataXmlColumn();
            Element node=  (Element) nodes.get(i);
            col.id=node.attributeValue("id");
            col.caption=node.attributeValue("name");
            col.columnName=node.attributeValue("caption");
            col.IntType=node.attributeValue("IntType");
            col.StringType=node.attributeValue("StringType");
          list.add(col);
        }
        return list;
        
    }
   
    /**
     * 
     * @Title: addRow   
     * @Description: 添加行  
     * @return      
     * DataXmlRow      
     * @throws
     */
public DataXmlRow addRow()
{
    DataXmlRow rowXml=null;
    HashMap<String,String> map=new  HashMap<String,String>();
    //获取列信息
    Element columnCollection= document.getRootElement().element("columnCollection");
   List<?> nodes= columnCollection.elements("Column");
   for (Iterator<?> it = nodes.iterator(); it.hasNext();) {
       Element elm = (Element) it.next();
       Attribute id= elm.attribute("id");
       Attribute name= elm.attribute("name");
       map.put(id.getValue(), name.getValue());
    }
   rowXml=new DataXmlRow(map,rd);
   rowXml.setIndex(rowIndex++);
   //
   Element rowCollection= document.getRootElement().element("RowCollection");
   Element row= rowCollection.addElement("Row");
   row.addAttribute("id", String.valueOf(rowIndex));
   
return rowXml;
}

/**
 * 
 * @Title: loadData   
 * @Description: 添加新行数据
 * @param data
 * @return      
 * boolean      
 * @throws
 */
public boolean loadData(List<String> data)
{
    Element rowCollection= document.getRootElement().element("RowCollection");
    for(int i=0;i<data.size();i++)
    {
       Element row= rowCollection.addElement("Row");
       row.addAttribute("id", String.valueOf(rowIndex++));
       row.setText(data.get(i));
    }
    return true;
}

/**
 * 
 * @Title: update   
 * @Description: 更新行，必须是存在的   
 * @param row
 * @return      
 * boolean      
 * @throws
 */
public  boolean update(DataXmlRow row)
{
    if(row.id!=rd)
    {
        return false;
    }
    Element rowCollection= document.getRootElement().element("RowCollection");
     List<?> rows=  rowCollection.elements("Row");
     //找到对应的行
     Element find=null;
     for (Iterator<?> it = rows.iterator(); it.hasNext();) {
         Element elm = (Element) it.next();
         Attribute id= elm.attribute("id");
         if(id.getValue().equals(String.valueOf(row.id)))
         {
             find=elm;
         }
      }
     if(find!=null)
     {
          find.setText(row.getRow());
          return true;
         
     }
    return false;
}
 
/**
 * 
 * @Title: getRow   
 * @Description: 获取行
 * @param index
 * @return      
 * DataXmlRow      
 * @throws
 */
public DataXmlRow getRow(int index)
{
    Element rowCollection= document.getRootElement().element("RowCollection");
    List<?> rows=  rowCollection.elements("Row");
    if(index>rows.size()-1)
    {
        return null;
    }
    Element  find= (Element) rows.get(index);
    String json=find.getText();
    HashMap<String,Object> map = 
            JSON.parseObject(json,new TypeReference<HashMap<String,Object>>() {});
    HashMap<String,String> indexKey=new  HashMap<String,String>();
    List<DataXmlColumn> list=getColumns();
    for(int i=0;i<list.size();i++)
    {
        DataXmlColumn col=list.get(i);
        indexKey.put(col.id, col.columnName);
    }
    DataXmlRow row=new DataXmlRow(indexKey,map,rd);
    return row;
}

/**
 * 
 * @Title: getTable   
 * @Description: 返回整个结构   
 * @return      
 * String      
 * @throws
 */
public String getTable()
{
    return document.asXML();
}
}
