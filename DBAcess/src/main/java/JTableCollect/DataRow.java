/**    
 * 文件名：DataRow.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JTableCollect;

import java.util.LinkedHashMap;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataRow    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午3:30:16    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午3:30:16    
 * 修改备注：    
 * @version     
 *     
 */
public class DataRow extends LinkedHashMap<String, Object> {
    /**    
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）    
     *    
     */    
    
    private static final long serialVersionUID = 1L;
    /**
     * 在getValue()和setValue()時候，程式碼須透過此成員的欄位名稱來找出Map字典裡的物件
     */
    private DataColumnCollection columns;
    /**
     * 此資料列所屬的DataTable，唯讀
     */
    private JDataTable table; 
    
  
    /**
     * DataRow被建立時，必須指定所屬的DataTable
     * @param DataRow所屬的DataTable 
     */
    public DataRow(JDataTable table) {
       this.table = table;
       this.columns = table.Columns;
    }

    
   /**
     * 取得DataRow所屬的DataTable
     * @return DataTable
     */
   public JDataTable getTable()
   {
     return this.table;
   }

   /**
    * 設定該列該行的值
    * @param columnindex 行索引(從0算起)
    * @param value 要設定的值
    */
    public void setValue(int columnindex,Object value) {
        setValue(this.columns.get(columnindex), value);
   }
  
   /**
     * 設定該列該行的值
     * @param columnName 行名稱
     * @param value 要設定的值
     */
    public void setValue(String columnName,Object value) {
        this.put(columnName.toLowerCase(), value);
    }

    /**
     * 設定該列該行的值
     * @param column DataColumn物件
     * @param value 要設定的值
     */
    private void setValue(DataColumn column,Object value) {
        if (column != null) {
            String lowerColumnName = column.ColumnName.toLowerCase();
          if (this.containsKey(lowerColumnName))
               this.remove(lowerColumnName);
            this.put(lowerColumnName, value);
        }
    }
 
   /**
     * 取得該列該行的值
     * @param columnIndex 行索引(從0算起)
     * @return Object
     */
   public Object getValue(int columnIndex) {
       String columnName = this.columns.get(columnIndex).ColumnName.toLowerCase();//取得Key
       return this.get(columnName);
   }

   /**
    * 取得該列該行的值
    * @param columnName 行名稱
    * @return Object
    */
   public Object getValue(String columnName) {
       return this.get(columnName.toLowerCase());//利用欄名(Key)來取值
   }
  
  /**
    * 取得該列該行的值
    * @param column DataColumn物件
    * @return Object
    */
   public Object getValue(DataColumn column) {
     return this.get(column.ColumnName.toLowerCase());//利用欄名(Key)來取值
  }
  
}
