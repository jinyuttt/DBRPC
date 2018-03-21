/**    
 * 文件名：DBClient.java    
 *    
 * 版本信息：    
 * 日期：2018年3月22日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.DBClient;

import java.util.ArrayList;
import java.util.List;

import DBRPC.DBModel.ReturnResult;
import DBRPC.DBModel.SQLModel;
import FastRPC.RPCCore.ParameterConvert;
import FastRPC.RPCCore.RPCParameter;
import FastRPC.RPCCore.RPCProxyClient;

/**    
 *     
 * 项目名称：DBClient    
 * 类名称：DBClient    
 * 类描述：    方法调用
 * 创建人：jinyu    
 * 创建时间：2018年3月22日 上午12:36:27    
 * 修改人：jinyu    
 * 修改时间：2018年3月22日 上午12:36:27    
 * 修改备注：    
 * @version     
 *     
 */
public class DBClient {
    
    /**
     * 
     * @Title: executeBit   
     * @Description: 执行二进制序列化
     * @param fun 服务名称
     * @param param  参数
     * @return      
     * ReturnResult      
     * @throws
     */
public static  ReturnResult  executeBit(String fun,SQLModel param)
{
    RPCProxyClient<ReturnResult>  client=new RPCProxyClient<ReturnResult> ();
   RPCParameter e = ParameterConvert.ObjectTo("params", param);
    List<RPCParameter> list=new ArrayList<RPCParameter>();
    list.add(e);
    return    client.execute(fun, list, ReturnResult.class);
    
}

/**
 * 
 * @Title: executeJSON   
 * @Description: 执行JSOn序列化
 * @param fun  服务名称
 * @param param  参数
 * @return      
 * ReturnResult      
 * @throws
 */
public static  ReturnResult  executeJSON(String fun,SQLModel param)
{
    RPCProxyClient<ReturnResult>  client=new RPCProxyClient<ReturnResult> ();
   RPCParameter e = ParameterConvert.JsonTo("params", param);
    List<RPCParameter> list=new ArrayList<RPCParameter>();
    list.add(e);
    return    client.execute(fun, list, ReturnResult.class);
    
}
}
