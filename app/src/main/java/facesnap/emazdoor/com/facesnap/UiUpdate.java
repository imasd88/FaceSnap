package facesnap.emazdoor.com.facesnap;

import android.support.constraint.ConstraintLayout;

import java.util.ArrayList;

/**
 * Created by Ahmed on 11/26/2017.
 */

public interface UiUpdate {

    ArrayList<String> myImages = new ArrayList<>();

    void retrieveImages();

    void mHandler(final ConstraintLayout constraintLayout);

    void changeBackground(ConstraintLayout constraintLayout, String path);

}
