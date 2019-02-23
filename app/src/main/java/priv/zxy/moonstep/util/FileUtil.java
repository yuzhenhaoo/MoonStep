package priv.zxy.moonstep.util;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author 张晓翼
 * @createTime 2019/2/22 0022
 * @Describe 文件操作的工具类
 */

public class FileUtil {
    /**
     *  复制单个文件
     *  可以通过Handler看到传递进度
     * @param oldPath 原文件路径 如：c:/fqf.txt String
     * @param newPath 复制后路径 如：f:/fqf.txt
     */
    public static void copyFile(String oldPath, String newPath, Handler handler) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {//文件存在时
                InputStream inStream = new FileInputStream(oldPath);//读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                int value = 0 ;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;//字节数 文件大小
                    value ++ ;  //计数
                    System.out.println("完成" + bytesum + "  总大小" + oldfile.getTotalSpace());
                    fs.write(buffer, 0, byteread);
                    Message msg  = new Message(); //创建一个msg对象
                    msg.what =110 ;
                    msg.arg1 = value ; //当前的value
                    handler.sendMessage(msg) ;

                    Thread.sleep(10);//每隔10ms发送一消息，也就是说每隔10ms value就自增一次，将这个value发送给主线程处理
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     *  复制单个文件
     *  不进行传输进度的回调
     * @param oldPath 原文件路径 如：c:/fqf.txt String
     * @param newPath 复制后路径 如：f:/fqf.txt
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {//文件存在时
                InputStream inStream = new FileInputStream(oldPath);//读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int value = 0 ;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;//字节数 文件大小
                    value ++ ;  //计数
                    System.out.println("完成" + bytesum + "  总大小" + oldfile.getTotalSpace());
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

}
