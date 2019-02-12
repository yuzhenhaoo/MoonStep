package priv.zxy.moonstep.DAO;

import android.graphics.Bitmap;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.util.ImageCacheUtil.NetCacheUtil;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/12
 * 描述:向服务器请求图片
 **/
public class PullImagesDAO {

    private static PullImagesDAO instance = null;

    private static final String IMAGE_TAG = "Image";

    public static PullImagesDAO getInstance() {
        if (instance == null) {
            instance = new PullImagesDAO();
        }
        return instance;
    }

    public void getImages(CallBack callBack, String imageUrl) {
        new Thread(() -> {
            if(NetCacheUtil.getInstance().downLoadBitmap(imageUrl) == null){
                callBack.onSuccess(NetCacheUtil.getInstance().downLoadBitmap(imageUrl));
            }
            else {
                callBack.onFail(ErrorCodeEnum.NET_NOT_RESPONSE);
            }
        }).start();
    }

    public interface CallBack {

        void onSuccess(Bitmap bitmap);

        void onFail(ErrorCodeEnum errorCodeEnum);
    }

}
