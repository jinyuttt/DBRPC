package INet;

public interface IRPCNet {
public void start(ISocketHander hander);
public int sendData(byte[]data);
public byte[] recvice();
public String getAddress();
public  void  setAddress(String ip);
public int getPort();
public void setPort(int port);
public void close();
public boolean connect(String ip,int port);
//发送给IP udp
public  void send(String ip,int port,byte[]data);
//绑定
public boolean bind(String ip,int port);
}
