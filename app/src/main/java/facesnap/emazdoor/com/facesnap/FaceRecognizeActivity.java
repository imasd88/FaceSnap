package facesnap.emazdoor.com.facesnap;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.qihancloud.opensdk.base.BindBaseActivity;
import com.qihancloud.opensdk.beans.FuncConstant;
import com.qihancloud.opensdk.function.unit.MediaManager;

import java.io.File;

/**
 * Created by Ahmed on 11/22/2017.
 */

public class FaceRecognizeActivity extends BindBaseActivity {
    MediaManager mediaManager;
    @Override
    protected void onMainServiceConnected() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ImageView imageView = new ImageView(this);
        setContentView(imageView);

        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);


//        /**
//         * 人脸识别
//         */
//       mediaManager.setMediaListener(new FaceRecognizeListener() {
//            @Override
//            public void recognizeResult(FaceRecognizeBean faceRecognizeBean) {
//                Bitmap bitmap = mediaManager.getVideoImage();
//                if(bitmap != null){
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        });
    }

    private static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
