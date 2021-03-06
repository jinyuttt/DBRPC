/**    
 * 文件名：JXMLAdapter.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JXMLTable;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：JXMLAdapter    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午7:51:47    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午7:51:47    
 * 修改备注：    
 * @version     
 *     
 */
public class JXMLAdapter {
  public void  fillDataTable(DataXMLTable table,ResultSet rs)
  {
      if(table==null)
      {
          return;
      }
      table.clear();
        try {
            ResultSetMetaData columns = rs.getMetaData();
            int size=columns.getColumnCount();
            for(int i=0;i<size;i++)
            {
                DataXmlColumn column=new DataXmlColumn() ;
                column.caption=columns.getColumnLabel(i);
                column.columnName=columns.getColumnName(i);
                column.IntType=String.valueOf(columns.getColumnType(i));
                column.StringType=columns.getColumnClassName(i);
                column.len=String.valueOf(columns.getColumnDisplaySize(i));
                table.addColumn(column);
            };
            //遍历行
          List<String> list=new ArrayList<String>();
        while(rs.next())
        {
            JSONObject obj=new JSONObject();
            for(int i=0;i<size;i++)
            {
             
                obj.put(String.valueOf(i),      rs.getObject(i));
            }
            //
            list.add(obj.toJSONString());
        }
           table.loadData(list); 
            
        } catch (SQLException e) {
      
            e.printStackTrace();
        }
  }
}
