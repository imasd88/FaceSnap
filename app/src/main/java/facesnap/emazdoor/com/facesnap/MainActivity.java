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
        Button capture = (Button) findViewById(R.id.btnCapture);
        image = (ImageView) findViewById(R.id.imageView);

        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Here images will be saved in the dir as
                //time in millisecs.
                File dir = new File(Environment.getExternalStorageDirectory() + "/DCIM/FaceSnap");
                String file = System.currentTimeMillis() + ".jpg";
                File filename = new File(Environment.getExternalStorageDirectory() + "/DCIM/FaceSnap", file);
                boolean flag = false;
                try {
                    if (!dir.exists())
                        dir.mkdirs();
                    flag = filename.createNewFile();
                } catch (IOException e) {
                    System.out.println(flag);
                }

                outputFileUri = Uri.fromFile(filename);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
//            try {
//
//                Bitmap capturedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), outputFileUri);
//                image.setImageBitmap(capturedImage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            Intent intent = new Intent(getApplicationContext(), SocialActivity.class);
            intent.putExtra("image", outputFileUri.toString());
            startActivity(intent);

            Log.d("CameraDemo", "Pic saved");
        }
    }
}
