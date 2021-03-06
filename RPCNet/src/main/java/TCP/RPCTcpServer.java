/**
 * 
 */
package TCP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import FastRPC.RPCNet.NetType;
import INet.IRPCNet;
import INet.ISocketHander;

/**
 * @author jinyu
 *  TCP服务端
 */
@NetType("tcp_Server")
public class RPCTcpServer extends Thread implements IRPCNet {
     private ServerSocket serverSocket = null;
	 private  int srcPort=0;
	 private  String srcAddress="";
     private ISocketHander hander;
     private  boolean isStop=false;
	 /**
	  * 启动
	  */
	public void start(ISocketHander hander) {
	   this.hander=hander;
	    if(srcAddress.isEmpty())
	    {
	      try {
            serverSocket = new ServerSocket(srcPort);
        } catch (IOException e) {
          
            e.printStackTrace();
        }
	    }
	    else
	    {
	        try {
                serverSocket = new ServerSocket();
                SocketAddress endpoint=new InetSocketAddress(srcAddress,srcPort);
                serverSocket.bind(endpoint);
            } catch (IOException e) {
                e.printStackTrace();
            }
	       
	    }
        this.start();
    

	}

	/**
	 * 发送数据
	 * 无用
	 */
	public int sendData(byte[] data) {
		
		return 0;
	}

	/**
	 * 接受数据
	 * 无用
	 */
	public byte[] recvice() {
	
		return null;
	}

	/**
	 * 返回绑定数据
	 */
	public String getAddress() {
		return this.srcAddress;
	}

	/**
	 * 设置地址
	 */
	public void setAddress(String ip) {
	this.srcAddress=ip;

	}

   /**
    * 获取端口
    */
	public int getPort() {
	return this.srcPort;
	}

	/**
	 * 甚至端口
	 */
	public void setPort(int port) {
	this.srcPort=port;

	}

	 public void run() {
	     //启动线程
        while (!isStop) {
            // 一旦有堵塞, 则表示服务器与客户端获得了连接
            Socket client = null;
           try {
               client = serverSocket.accept();
           } catch (IOException e) {
               
               e.printStackTrace();
           }
            // 处理这次连接
            new HandlerSocket(client,hander).Process();
        }
        
    }

	 /**
	  * 关闭
	  */
    public void close() {
        isStop=true;
        try {
            serverSocket.close();
        } catch (IOException e) {
           
            e.printStackTrace();
        }
    }

    /**
     * 服务端无用
     */
    public boolean connect(String ip, int port) {
        
        return false;
    }

    /**
     * 服务端无用
     */
    public void send(String ip, int port, byte[] data) {
      
        
    }

    /**
     * 服务端无用
     */
    public boolean bind(String ip, int port) {
     
        return false;
    }

}
