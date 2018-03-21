/**    
 * 文件名：DataColumnCollection.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JTableCollect;

import java.util.ArrayList;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataColumnCollection    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午3:33:53    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午3:33:53    
 * 修改备注：    
 * @version     
 *     
 */
public class DataColumnCollection extends ArrayList<DataColumn> {
    /**    
     * serialVersionUID:
     *    
     */    
    
    private static final long serialVersionUID = 1L;
    /**
     * DataColumnCollection所屬的DataTable，唯讀
     */
    private JDataTable Table;
    
    /**
     * DataColumnCollection被建立時，一定要指定所屬的DataTable
     * @param table 
     */
    public DataColumnCollection(JDataTable table)
    {
      this.Table = table;
    }
    
    /**
     * 取得DataColumnCollection所屬的DataTable
     * @return DataTable
     */
    public JDataTable getTable()
    {
      return this.Table;
    }
    
    /**
     * 加入一個DataColumn物件，程式碼會設定該DataColumn的DataTable和呼叫Add()方法的DataColumnCollection同一個DataTable
     * @param column 
     */
    public void Add(DataColumn column)
    {  
       column.setTable(this.Table);
       this.add(column);
    }
    
    /**
     * 給欄位名稱
     * 加入一個DataColumn物件，程式碼會設定該DataColumn的DataTable和呼叫Add()方法的DataColumnCollection同一個DataTable
     * @param columnName
     * @return 
     */
    public DataColumn Add(String columnName)
    {  
       DataColumn column = new DataColumn(columnName.toLowerCase());
       column.setTable(this.Table);
       this.add(column);
       return column;
    }
     
    /**
     * 依據欄名，取得DataColumn
     * @param columnName 欄名
     * @return DataColumn
     */
    public DataColumn get(String columnName)
    {   
        
       DataColumn column = null;
       for(DataColumn dataColumn :this)
       {
           if (dataColumn.ColumnName.toLowerCase().equals(columnName.toLowerCase())) {
               return dataColumn;
           }
       
       }
        return column;
    }
    

}
