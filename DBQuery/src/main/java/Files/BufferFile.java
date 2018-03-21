/**    
 * 文件名：BufferFile.java    
 *    
 * 版本信息：    
 * 日期：2018年3月21日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package Files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**    
 *     
 * 项目名称：DBQuery    
 * 类名称：BufferFile    
 * 类描述： 读写文件（回调与获取方法只能其一，回调优先）
 * 创建人：jinyu    
 * 创建时间：2018年3月21日 上午1:51:32    
 * 修改人：jinyu    
 * 修改时间：2018年3月21日 上午1:51:32    
 * 修改备注：    
 * @version     
 *     
 */
public class BufferFile {
    
    /**
     * 缓存大小
     */
    private int bufferSize=1024*1024*50;
    
    /**
     * 数据接收区
     */
    private ArrayBlockingQueue<byte[]> queue=new ArrayBlockingQueue<byte[]>(10);
    
    /**
     *外部回调
     */
    private IRFile recvier=null;
    
    /**
     * 是否读取结束
     */
    private  volatile boolean isEnd=false;
    public void setRecvicer(IRFile rec)
    {
        this.recvier=rec;
    }
@SuppressWarnings("resource")
public void  readFile(String file) throws IOException
{
    byte[] b=new byte[bufferSize];
    File f = new File(file);   
    FileInputStream fis=new FileInputStream(f) ;
    BufferedInputStream   bis = new BufferedInputStream(fis,bufferSize);
       int r=0;
          while( (r= bis.read(b))!=-1)
          {
              byte[] tmp=new byte[r];
              System.arraycopy(b, 0, tmp, 0, r);
              if(recvier!=null)
              {
                  recvier.recvie(tmp);
              }
              else
              {
                    try {
                        queue.put(tmp);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
              }
          }
          isEnd=true;
          bis.close();
          fis.close();
}

/**
 * 
 * @Title: isReadEnd   
 * @Description: 读取是否结束
 * @return      
 * boolean      
 * @throws
 */
public boolean isReadEnd()
{
    return isEnd;
}
/**
 * 
 * @Title: getBytes   
 * @Description: 获取读取的数据  
 * @return      
 * byte[]      
 * @throws
 */
public byte[] getBytes()
{
   try {
    return queue.poll(100, TimeUnit.MICROSECONDS);
} catch (InterruptedException e) {
    e.printStackTrace();
}
return null;
}

/**
 * 
 * @Title: writeFile   
 * @Description:写入文件 
 * @param file
 * @param data
 * @throws IOException      
 * void      
 * @throws
 */
public void writeFile(String file,byte[]data) throws IOException
{
    FileOutputStream fos;
        fos = new FileOutputStream(file,true);
    BufferedOutputStream bos=new BufferedOutputStream(fos,bufferSize);
    bos.write(data,0,data.length);
    bos.flush();
    bos.close();
    fos.close();
}
}
