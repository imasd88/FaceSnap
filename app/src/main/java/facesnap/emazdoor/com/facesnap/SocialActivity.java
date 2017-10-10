package facesnap.emazdoor.com.facesnap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Ahmed on 10/10/2017.
 */

public class SocialActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView _image = (ImageView) findViewById(R.id.capturedImage);
        Intent bundle = getIntent();
        Uri myUri = Uri.parse(bundle.getStringExtra("image"));
        try {
            Bitmap capturedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), myUri);
            _image.setImageBitmap(capturedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
