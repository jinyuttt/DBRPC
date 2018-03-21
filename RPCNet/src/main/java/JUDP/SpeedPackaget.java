/**    
 * 文件名：SpeedPackaget.java    
 *    
 * 版本信息：    
 * 日期：2018年3月1日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JUDP;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：SpeedPackaget    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月1日 上午12:54:36    
 * 修改人：jinyu    
 * 修改时间：2018年3月1日 上午12:54:36    
 * 修改备注：    
 * @version     
 *     
 */
public class SpeedPackaget  extends NetPackaget {
public SpeedPackaget()
{
    this.type=1;
    data=new byte[9];
}
public SpeedPackaget(byte[]data)
{
    this.type=1;
    this.data=data;
   
    
}
public byte[] get()
{
    return data;
    
}
}
