package facesnap.emazdoor.com.facesnap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import com.qihancloud.opensdk.base.BindBaseActivity;
//import com.qihancloud.opensdk.beans.FuncConstant;
//import com.qihancloud.opensdk.function.unit.MediaManager;
//import com.qihancloud.opensdk.function.unit.interfaces.media.MediaListener;
//import com.qihancloud.opensdk.function.unit.interfaces.media.MediaStreamListener;

//public class MainActivity extends BindBaseActivity {
//
//    MediaManager mediaManager;
//
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);
//
//
//    }
//
//    @Override
//    protected void onMainServiceConnected() {
//
//    }
//}

public class MainActivity extends Activity {

    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    ImageView image;
    Uri outputFileUri;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // Here, we are making a folder named picFolder to store
        // pics taken by the camera using this application.
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newdir = new File(dir);
        newdir.mkdirs();

        Button capture = (Button) findViewById(R.id.btnCapture);
        image = (ImageView) findViewById(R.id.imageView);

        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Here, the counter will be incremented each time, and the
                // picture taken by camera will be stored as 1.jpg,2.jpg
                // and likewise.
                count++;
                String file = dir + count + ".jpg";
                File newfile = new File(file);
                boolean flag = false;
                try {
                    flag = newfile.createNewFile();
                } catch (IOException e) {
                    System.out.println(flag);
                }

                outputFileUri = Uri.fromFile(newfile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(mphoto);
            Log.d("CameraDemo", "Pic saved");
        }
    }
}
