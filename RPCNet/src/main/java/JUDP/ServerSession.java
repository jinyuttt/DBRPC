package JUDP;

import INet.ISocketHander;

public class ServerSession extends Session {
public ServerSession()
{
    endpoint=new EndPoint();
}
public ServerSession(int port)
{
    endpoint=new EndPoint(port);
}
public ServerSession(String ip,int port)
{
    endpoint=new EndPoint(ip,port);
}
public void start(ISocketHander hander)
{
    endpoint.start(hander);
}

}
