package facesnap.emazdoor.com.facesnap;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;


import java.io.File;
import java.util.logging.Handler;

/**
 * Created by Ahmed on 11/26/2017.
 */

public class SlideEffect extends Application implements UiUpdate {


    int count = 0;
    final android.os.Handler handler = new android.os.Handler();

    @Override
    public void retrieveImages() {
        File directory = new File(AppConstant.IMAGE_LOCATION);
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                myImages.add(file.toString());
            }
        }
    }

    @Override
    public void mHandler(final ConstraintLayout constraintLayout) {
        this.retrieveImages();
        for (int i = 0; i < myImages.size(); i++) {
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    changeBackground(constraintLayout, myImages.get(count));
                }
            }, 5000);
        }

    }

    @Override
    public void changeBackground(ConstraintLayout constraintLayout, String path) {
        count++;
        Drawable drawable = Drawable.createFromPath(path);
        constraintLayout.setBackground(drawable);
    }


}

