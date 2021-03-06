/**    
 * 文件名：NetRecvicer.java    
 *    
 * 版本信息：    
 * 日期：2018年2月27日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package IRPCNet;

import FastRPC.RPCCore.RequestHander;
import FastRPC.RPCNet.JSocket;
import INet.ISocketHander;

/**    
 *     
 * 项目名称：RPCNet    
 * 类名称：NetRecvicer    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年2月27日 上午12:54:55    
 * 修改人：jinyu    
 * 修改时间：2018年2月27日 上午12:54:55    
 * 修改备注：    
 * @version     
 *     
 */
public class RPCRecvicer implements ISocketHander {
    public void recvicer(JSocket recvicer) {
        RequestHander hander=new RequestHander();
        byte[] result=hander.process(recvicer.buffer);
        recvicer.sockset.send(result);
        if(!recvicer.isTcpType)
        {
            //tcp类型由客户端关闭
            recvicer.sockset.close();
        }
    }

}
