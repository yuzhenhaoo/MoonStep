package priv.zxy.moonstep.helper;

import android.content.Context;
import android.util.Log;

import com.baidu.mapapi.map.MapView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.utils.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/19
 * 描述:
 **/
public class FileHelper {

    private static final String TAG = "FileHelper";

    private static FileHelper instance;

    public static FileHelper getInstance() {
        if (instance == null){
            synchronized (FileHelper.class){
                instance = new FileHelper();
            }
        }
        return instance;
    }

    /**
     * 这里定义的是一个文件保存的方法，写入到文件中，所以是输出流
     * 文件保存的路径为/data/data/<包名>/files/
     * */
    public void save(String filename, String filecontent){
        try{
            FileOutputStream output = Application.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
            output.write(filecontent.getBytes());  //将String字符串以字节流的形式写入到输出流中
            output.close();         //关闭输出流
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /*
     * 这里定义的是文件读取的方法
     * */
    public String read(String filename){
        try{
            //打开文件输入流
            FileInputStream input = Application.getContext().openFileInput(filename);
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            //读取文件内容:
            while ((len = input.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            //关闭输入流
            input.close();
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * 设置个性化地图config文件路径
     */
    public static void setMapCustomFile(Context context, String PATH) {
        FileOutputStream out = null;
        InputStream inputStream = null;
        String moduleName = null;
        try {
            inputStream = context.getAssets()
                    .open("customConfigFile/" + PATH);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            moduleName = context.getFilesDir().getAbsolutePath();
            File f = new File(moduleName + "/" + PATH);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            out = new FileOutputStream(f);
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MapView.setCustomMapStylePath(moduleName + "/" + PATH);

    }

}
