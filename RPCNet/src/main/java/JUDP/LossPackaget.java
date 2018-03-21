/**    
 * 文件名：LossPackaget.java    
 *    
 * 版本信息：    
 * 日期：2018年3月1日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JUDP;

import java.nio.ByteBuffer;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：LossPackaget    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月1日 上午12:55:36    
 * 修改人：jinyu    
 * 修改时间：2018年3月1日 上午12:55:36    
 * 修改备注：    
 * @version     
 *     
 */
public class LossPackaget extends NetPackaget {
    ByteBuffer buf=null;
public LossPackaget(byte[]data)
{
    this.type=2;
  
}
public int id;
public int index;
}
