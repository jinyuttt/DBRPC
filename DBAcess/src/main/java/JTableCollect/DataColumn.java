/**    
 * 文件名：DataColumn.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JTableCollect;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataColumn    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午3:33:00    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午3:33:00    
 * 修改备注：    
 * @version     
 *     
 */
public class DataColumn {
    /**
     * DataColumn所屬的DataTable
     */
     private JDataTable table; 
     /**
      * DataColumn的欄位名稱
      */
     public String ColumnName; // 欄名，當做DataRow的key
     
     public int columnIndex=-1;
     
     public String caption;
     
     
 
    /**
      * DataColumn被建立時，一定要指定欄名
      * @param columnName 欄名
      */
     public DataColumn(String columnName) {
       this.ColumnName = columnName.toLowerCase();
     }
    
    /**
     * 給DataColumnCollection加入DataColumn時設定所屬的DataTable的方法，同一個package才用到
     * @param table 
     */
     void setTable(JDataTable table)
    {
      this.table = table;
    }
    
    /**
     * 取得DataColumn所屬的DataTable，唯讀
     * @return DataTable
     */
    public JDataTable getTable()
    {
       return this.table;
    }
  
     /**
     * DataColumn物件的toString()，會回傳自己的欄名
     * @return 
     */
     @Override
     public String toString(){
         return this.ColumnName;
     }
}
