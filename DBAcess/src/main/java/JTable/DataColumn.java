/**    
 * 文件名：DataColumn.java    
 *    
 * 版本信息：    
 * 日期：2018年3月17日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JTable;

/**    
 *     
 * 项目名称：DBAcess    
 * 类名称：DataColumn    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月17日 下午2:35:31    
 * 修改人：jinyu    
 * 修改时间：2018年3月17日 下午2:35:31    
 * 修改备注：    
 * @version     
 *     
 */
public class DataColumn {
    String key; 
    Object value; 
    public DataColumn(String k, Object v) { 
    key = k; 
    value = v; 
    } 
    public String getKey() { 
    return key; 
    } 
    public Object getValue() { 
    return value; 
    } 
    public void setKey(String key) { 
    this.key = key; 
    } 
    public void setValue(Object value) { 
    this.value = value; 
    } 
}
