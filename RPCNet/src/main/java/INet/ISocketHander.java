/**    
 * 文件名：ISocketHander.java    
 *    
 * 版本信息：    
 * 日期：2018年2月22日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package INet;

import FastRPC.RPCNet.JSocket;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：ISocketHander    
 * 类描述：    接受数据处理
 * 创建人：jinyu    
 * 创建时间：2018年2月22日 下午2:53:06    
 * 修改人：jinyu    
 * 修改时间：2018年2月22日 下午2:53:06    
 * 修改备注：    
 * @version     
 *     
 */
public interface ISocketHander {
public  void recvicer(JSocket recvicer);
}
