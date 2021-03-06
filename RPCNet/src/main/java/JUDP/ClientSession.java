/**    
 * 文件名：ClientSession.java    
 *    
 * 版本信息：    
 * 日期：2018年2月28日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package JUDP;

import FastRPC.RPCNet.NetType;
import INet.IRPCNet;
import INet.ISocketHander;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：ClientSession    
 * 类描述：    judp客户端
 * 创建人：jinyu    
 * 创建时间：2018年2月28日 上午1:49:58    
 * 修改人：jinyu    
 * 修改时间：2018年2月28日 上午1:49:58    
 * 修改备注：    
 * @version     
 *     
 */
@NetType("judp_Client")
public class ClientSession extends Session implements IRPCNet{
public ClientSession()
{
    this.endpoint=new EndPoint();
    this.start();
}
public ClientSession(int port)
{
    this.endpoint=new EndPoint(port);
    this.start();
}
public ClientSession(String ip,int port)
{
    this.endpoint=new EndPoint(ip,port);
    this.start();
}




/**
 * 绑定本地地址
 */
public boolean bind(String ip, int port) {
    this.endpoint=new EndPoint(ip,port);
    return true;
}

/**
 * 无用
 */
public boolean connect(String ip, int port) {
   
    return false;
}

/**
 * 发送数据
 */
public void send(String ip, int port, byte[] data) {
  this.sendData(ip, port, data);
    
}

/**
 * 回调赋值
 */
public void start(ISocketHander hander) {
   this.hander=hander;
}

/**
 * 接口实现  无用 TCP类型使用
 */
public int sendData(byte[] data) {
   
    return 0;
}

/**
 * 接收数据
 */
public byte[] recvice() {
  return this.getRecvice().buffer;
}

/**
 * 返回远端地址 无用   接口实现
 */
public String getAddress() {
 
    return null;
}

/**
 * 设置地址  无用  接口实现
 */
public void setAddress(String ip) {
  
    
}

/**
 *   无用  接口实现
 */
public int getPort() {
 
    return 0;
}

/**
 *   无用  接口实现
 */
public void setPort(int port) {
  
    
}

/**
 * 关闭
 */
public void close() {
   stop();
    
}

}
