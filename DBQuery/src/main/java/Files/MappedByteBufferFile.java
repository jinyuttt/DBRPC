/**    
 * 文件名：MappedByteBufferFile.java    
 *    
 * 版本信息：    
 * 日期：2018年3月21日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package Files;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**    
 *     
 * 项目名称：DBQuery    
 * 类名称：MappedByteBufferFile    
 * 类描述：    
 * 创建人：jinyu    
 * 创建时间：2018年3月21日 上午12:38:06    
 * 修改人：jinyu    
 * 修改时间：2018年3月21日 上午12:38:06    
 * 修改备注：    
 * @version     
 *     
 */
public class MappedByteBufferFile {
    final int BUFFER_SIZE = 1024*1024*10;// 缓冲区大小为3M
public byte[] readFile(String file)
{
    FileInputStream fis;
    try {
        fis = new FileInputStream(file);
    } catch (FileNotFoundException e) {
        return null;
    }  
    byte[] dst = new byte[BUFFER_SIZE];// 每次读
    FileChannel fc = fis.getChannel();    
    MappedByteBuffer inputBuffer = null;
    try {
        inputBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
            
            if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
 
                for (int i = 0; i < BUFFER_SIZE; i++)
 
                    dst[i] = inputBuffer.get(offset + i);
 
            } else {
 
                for (int i = 0; i < inputBuffer.capacity() - offset; i++)
 
                    dst[i] = inputBuffer.get(offset + i);
 
            }
 
        }

    } catch (IOException e) {
    
    }  
 finally
 {
     inputBuffer.flip();  
    try {
        fc.close();
    } catch (IOException e) {
     
        e.printStackTrace();
    }  
    try {
        fis.close();
    } catch (IOException e) {
     
        e.printStackTrace();
    } 
 }
    return dst;
}
@SuppressWarnings("resource")
public void writeFile(String file,byte[]data)
{
    FileChannel fc;
    try {
        fc = new RandomAccessFile(file,"rw").getChannel();
    } catch (FileNotFoundException e) {
    return ;
    }  
    long length = 0;
    try {
        length = fc.size();
    } catch (IOException e) {
     
        e.printStackTrace();
    } //有来设置映射区域的开始位置  
    MappedByteBuffer mbb = null;
    try {
        mbb = fc.map(FileChannel.MapMode.READ_WRITE,length,data.length);
    } catch (IOException e) {
        e.printStackTrace();
    }  
    mbb.put(data); //写入新数据  
}
}
