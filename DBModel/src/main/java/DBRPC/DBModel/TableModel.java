/**    
 * 文件名：TableModel.java    
 *    
 * 版本信息：    
 * 日期：2018年3月18日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.DBModel;

import java.util.List;

/**    
 *     
 * 项目名称：DBModel    
 * 类名称：TableModel    
 * 类描述：  表格模型  转为传输
 * 创建人：jinyu    
 * 创建时间：2018年3月18日 上午1:05:18    
 * 修改人：jinyu    
 * 修改时间：2018年3月18日 上午1:05:18    
 * 修改备注：    
 * @version     
 *     
 */
public class TableModel {
  public String tableName="";
public List<DataColumn> columns=null;
public List<Object> rows=null;
}
