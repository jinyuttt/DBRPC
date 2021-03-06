/**    
 * 文件名：DataTable.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JTable;

import java.util.ArrayList;
import java.util.List;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataTable    
 * 类描述：   列表
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午2:34:25    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午2:34:25    
 * 修改备注：    
 * @version     
 *     
 */
public class JListDataTable {
    List<DataRow> row; 
    public JListDataTable() { 
    } 
    public JListDataTable(List<DataRow> r) { 
    row = r; 
    } 
    public List<DataRow> getRow() { 
    return row; 
    } 
    public void setRow(List<DataRow> row) { 
    this.row = row; 
    } 
    /** 
    * @功能描述 双表根据两表关联字段连接，要求两表必须包含公告字段，并且每一行数据公共字段相等 。dt1对应colname1 ,dt2 
    * 对应colName2 
    * @可能的错误 未完成 
    * @作者 王磊 
    * @修改说明 
    * @修改人 
    */ 
    public static JListDataTable joinTable(JListDataTable dt1, JListDataTable dt2, String colName1, 
    String colName2) { 
    List<DataRow> newRows = new ArrayList<DataRow>(); 
    List<DataRow> rows1 = dt1.getRow(); 
    List<DataRow> rows2 = dt2.getRow(); 
    int i1 = rows1.size(); 
    int i2 = rows2.size(); 
    List<DataRow> temp = new ArrayList<DataRow>(); 
    String tempC = ""; 
    if (i1 > i2) { 
    temp = rows1; 
    rows1 = rows2; 
    rows2 = temp; 
    tempC = colName1; 
    colName1 = colName2; 
    colName2 = tempC; 
    } 
    for (DataRow r1 : rows1) { 
    String value1 = r1.eval(colName1); 
    for (DataRow r2 : rows2) { 
    String value2 = r2.eval(colName2); 
    if (value1.equals(value2)) { 
    List<DataColumn> cols = new ArrayList<DataColumn>(); 
    for (DataColumn c : r1.getCol()) { 
    cols.add(c); 
    } 
    for (DataColumn c : r2.getCol()) { 
    cols.add(c); 
    } 
    DataRow rr = new DataRow(cols); 
    newRows.add(rr); 
    } 
    } 
    } 
    JListDataTable dt = new JListDataTable(newRows); 
    return dt; 
    } 
    public static void outTable(JListDataTable dt) { 
    for (DataRow r : dt.getRow()) { 
    for (DataColumn c : r.getCol()) { 
    System.out.print(c.getKey() + ":" + c.getValue() + " "); 
    } 
    wl(""); 
    } 
    } 
    public static void wl(String s) { 
    System.out.println(s); 
    } 
}
