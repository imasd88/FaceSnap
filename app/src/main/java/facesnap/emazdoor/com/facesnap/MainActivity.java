package facesnap.emazdoor.com.facesnap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    int TAKE_PHOTO_CODE = 0;
    ImageView logoImage;
    Button capture;
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
        capture = (Button) findViewById(R.id.captureButton);
        logoImage = (ImageView) findViewById(R.id.profile_image);
        capture.setVisibility(View.GONE);

        Animation animateLogo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_logo_up);
        animateLogo.setFillAfter(true);
        animateLogo.setAnimationListener(this);
        logoImage.startAnimation(animateLogo);

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

            Bitmap bitmap = BitmapFactory.decodeFile(Utils.getImagePath(outputFileUri, MainActivity.this));

            outputFileUri = Utils.getImageUri(MainActivity.this, bitmap);
            Intent intent = new Intent(getApplicationContext(), SocialActivity.class);
            intent.putExtra("image", outputFileUri.toString());
            startActivity(intent);

            Log.d("CameraDemo", "Pic saved");
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        capture.setAlpha(0f);
        capture.setVisibility(View.VISIBLE);

        int mediumAnimateTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        capture.animate().alpha(1f).setDuration(mediumAnimateTime).setListener(null);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
