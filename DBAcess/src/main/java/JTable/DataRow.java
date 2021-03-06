/**    
 * 文件名：DataRow.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JTable;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataRow    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午2:34:58    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午2:34:58    
 * 修改备注：    
 * @version     
 *     
 */
public class DataRow {
    List<DataColumn> col; 
    /** 
    * @功能描述 返回指定DataColumn类型数据列对象 
    * @作者 王磊 
    */ 
    public DataColumn getColumnObject(String colName) { 
     
    for (DataColumn c : col) { 
    if (c.getKey().toUpperCase().equals(colName.toUpperCase())) { 
    try { 
    return c; 
    } catch (Exception e) { 
    System.out.println("错误描述：" + e.toString()); 
    } 
    } 
    } 
    return null; 
    } 
    /** 
    * @功能描述 返回指定Object类型数据列对象 
    * @作者 王磊 
    */ 
    public Object getColumn(String colName) { 
    for (DataColumn c : col) { 
    if (c.getKey().toUpperCase().equals(colName.toUpperCase())) { 
    try { 
    return c.getValue(); 
    } catch (Exception e) { 
    System.out.println("错误描述：" + e.toString()); 
    } 
    } 
    } 
    return null; 
    } 
    /** 
    * @功能描述 返回指定int类型数据列对象 
    * @作者 王磊 
    */ 
    public int getIntColumn(String colName) { 
    for (DataColumn c : col) { 
    if (c.getKey().toUpperCase().equals(colName.toUpperCase())) { 
    try { 
    return Integer.parseInt(c.getValue().toString()); 
    } catch (Exception e) { 
    System.out.println("错误描述：" + e.toString()); 
    } 
    } 
    } 
    return 0; 
    } 
    /** 
    * @功能描述 返回指定String类型数据列对象 
    * @作者 王磊 
    */ 
    public String getStringColumn(String colName) { 
    for (DataColumn c : col) { 
    if (c.getKey().toUpperCase().equals(colName.toUpperCase())) { 
    try { 
    return c.getValue().toString(); 
    } catch (Exception e) { 
    System.out.println("错误描述：" + e.toString()); 
    } 
    } 
    } 
    return null; 
    } 
    /** 
    * @功能描述 返回指定String类型数据列对象 
    * @作者 王磊 
    */ 
    public String eval(String colName) { 
    for (DataColumn c : col) { 
    if (c.getKey().toUpperCase().equals(colName.toUpperCase())) { 
    try { 
    return c.getValue() + "";// 此方法将屏蔽错误！！！ 
    } catch (Exception e) { 
    System.out.println("错误描述：" + e.toString()); 
    } 
    } 
    } 
    return null; 
    } 
    /** 
    * @功能描述 返回指定Date类型数据列对象 
    * @作者 王磊 
    */ 
    public Date getDateColumn(String colName) { 
    for (DataColumn c : col) { 
    if (c.getKey().toUpperCase().equals(colName.toUpperCase())) { 
    try { 
        String string = "2016-10-24";
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return  sdf.parse(string);
    } catch (Exception e) { 
    System.out.println("错误描述：" + e.toString()); 
    } 
    } 
    } 
    return null; 
    } 
    /** 
    * @功能描述 返回指定Blob类型数据列对象 
    * @作者 王磊 
    */ 
    public Blob getBlobColumn(String colName) { 
    for (DataColumn c : col) { 
    if (c.getKey().toUpperCase().equals(colName.toUpperCase())) { 
    try { 
    return (Blob) c.getValue(); 
    } catch (Exception e) { 
    System.out.println("错误描述：" + e.toString()); 
    } 
    } 
    } 
    return null; 
    } 
    /** 
    * @功能描述 返回指定Blob类型数据列对象 
    * @作者 王磊 
    */ 
    public float getFloatColumn(String colName) { 
    for (DataColumn c : col) { 
    if (c.getKey().toUpperCase().equals(colName.toUpperCase())) { 
    try { 
    return Float.parseFloat(c.getValue().toString()); 
    } catch (Exception e) { 
    System.out.println("错误描述：" + e.toString()); 
    } 
    } 
    } 
    return 0; 
    } 
    public DataRow(List<DataColumn> c) { 
    col = c; 
    } 
    public List<DataColumn> getCol() { 
    return col; 
    } 
    public void setCol(List<DataColumn> col) { 
    this.col = col; 
    } 
}
