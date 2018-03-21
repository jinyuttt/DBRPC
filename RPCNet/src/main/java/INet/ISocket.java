/**    
 * 文件名：ISocket.java    
 *    
 * 版本信息：    
 * 日期：2018年2月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package INet;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：ISocket    
 * 类描述：    封装客户端内部通信对象
 * 返回客户端
 * 创建人：jinyu    
 * 创建时间：2018年2月27日 上午12:16:58    
 * 修改人：jinyu    
 * 修改时间：2018年2月27日 上午12:16:58    
 * 修改备注：    
 * @version     
 *     
 */
public interface ISocket {

  //直接发送数据
public  void send(byte[]data);


//关闭
public void close();


}
