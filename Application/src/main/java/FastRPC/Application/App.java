package FastRPC.Application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import FastRPC.RPCCore.RPCCore;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       
        URL url = App.class.getProtectionDomain().getCodeSource().getLocation();  
        String filePath = null;  
        try {  
            filePath = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        if (filePath.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"  
            // 截取路径中的jar包名  
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);  
        }  
          
        File file = new File(filePath);  
          
        // /If this abstract pathname is already absolute, then the pathname  
        // string is simply returned as if by the getPath method. If this  
        // abstract pathname is the empty abstract pathname then the pathname  
        // string of the current user directory, which is named by the system  
        // property user.dir, is returned.  
        filePath = file.getAbsolutePath();//得到windows下的正确路径  
        if(filePath!=null)
        {
            System.out.println(filePath);
        }
        RPCCore.applicationDir=filePath;
        System.out.println(RPCCore.applicationDir);
        RPCCore core=new RPCCore();
        core.UtilInit();
        System.out.println("启动");
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
