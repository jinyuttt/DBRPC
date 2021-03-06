/**    
 * 文件名：DataTable.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DataTable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import JTable.DataColumn;
import JTable.DataRow;
import JTable.JListDataTable;
import JTableCollect.JDataTable;
import JXMLTable.DataXMLTable;
import JXMLTable.JXMLAdapter;
import data.DataTable;
import data.common.JdbcAdapter;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataTable    
 * 类描述：    创建不同类型DataTable
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午2:28:32    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午2:28:32    
 * 修改备注：    
 * @version     
 *     
 */
public class ComDataTable {
    
    /**
     * 
     * @Title: create   
     * @Description: 创建DataTable   
     * @return      
     * DataTable      
     * @throws
     */
public  static DataTable  create()
{
    DataTable dtb = new DataTable();
    return dtb;
}

/**
 * 
 * @Title: create   
 * @Description: 创建DataTable
 * @param rs
 * @return
 * @throws Exception      
 * DataTable      
 * @throws
 */
public static DataTable create(ResultSet rs) throws Exception
{
    DataTable dtb = new DataTable();
    data.common.JdbcAdapter dAdapter = new JdbcAdapter();
    dAdapter.fillDataTable(dtb, rs);
    return dtb;
}

/**
 * 
 * @Title: createList   
 * @Description:  创建DataTable   行列使用List
 * 不在特殊场景不建议使用
 * @param rs
 * @return
 * @throws Exception      
 * JListDataTable      
 * @throws
 */
public static JListDataTable createList(ResultSet rs) throws Exception
{
    JListDataTable table=new JListDataTable();
    List<DataRow> rows=new ArrayList<DataRow>();
     ResultSetMetaData columns = rs.getMetaData();
     int size=columns.getColumnCount();
     while(rs.next())
     {
         List<DataColumn> list=new ArrayList<DataColumn>();
         for(int i=0;i<size;i++)
         {
             DataColumn col=new DataColumn(columns.getColumnName(i),rs.getObject(i));
             list.add(col);
             //
        
         }
         DataRow e=new DataRow(list);
         rows.add(e);
     }
    table.setRow(rows);
    return table;
}

/**
 * 
 * @Title: createJDataTable   
 * @Description: 创建DataTable   行列使用不同结构  
 *  设置了行列，可以不用列信息，建议使用
 * @param rs
 * @return
 * @throws Exception      
 * JDataTable      
 * @throws
 */
public static JDataTable createJDataTable(ResultSet rs) throws Exception
{
    JDataTable dtb = new JDataTable();
    //
    ResultSetMetaData columns = rs.getMetaData();
    int size=columns.getColumnCount();
    //
    for(int i=0;i<size;i++)
    {
        JTableCollect.DataColumn column=new  JTableCollect.DataColumn(columns.getColumnName(i)) ;
        column.caption=columns.getColumnLabel(i);
        column.columnIndex=i;
        dtb.Columns.Add(column);
    }
    while(rs.next())
    {
        for(int i=0;i<size;i++)
        {
         JTableCollect.DataRow row=dtb.NewRow();
         //可以没有列信息，直接存储数据
         row.setValue(i, rs.getObject(i));
        }
    }
    return dtb;
}

/**
 * 
 * @Title: createXMLDataTable   
 * @Description: 创建DataTable   使用XML构建，跨组件，网络环境使用   
 * @param rs
 * @return
 * @throws Exception      
 * DataXMLTable      
 * @throws
 */
public static DataXMLTable createXMLDataTable(ResultSet rs) throws Exception
{
    DataXMLTable dtb = new DataXMLTable();
    JXMLAdapter adp=new JXMLAdapter();
    adp.fillDataTable(dtb, rs);;
    return dtb;
}
}
