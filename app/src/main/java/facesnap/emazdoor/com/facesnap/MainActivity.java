package facesnap.emazdoor.com.facesnap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int TAKE_PHOTO_CODE = 0;
    @BindView(R.id.profile_image)
    ImageView logoImage;
    @BindView(R.id.captureButton)
    Button capture;
    Uri outputFileUri;


    private static String FONT_MUSEO_700 = "Museo-700.otf";


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
        ButterKnife.bind(this);

        ((AppCompatTextView) findViewById(R.id.textView)).setTypeface(getFont(this, FONT_MUSEO_700));
        ((AppCompatTextView) findViewById(R.id.textView2)).setTypeface(getFont(this, FONT_MUSEO_700));
        ((AppCompatTextView) findViewById(R.id.textView3)).setTypeface(getFont(this, FONT_MUSEO_700));

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


//        int[] mbgIds = new int[]{
//                R.drawable.telepres, R.drawable.app_team, R.drawable.team, R.drawable.nicci, R.drawable.amysanbot
//        };
//
//        Random rgenerator = new Random();
//
//
//        ConstraintLayout rootView = (ConstraintLayout) findViewById(R.id.background);
//
//
//        Integer u = mbgIds[rgenerator.nextInt(mbgIds.length)];
//        Log.e("123", "IMAGE_GET" + u);
//        rootView.setBackgroundResource(u);

    }

    public static Typeface getFont(Context context, String name) {
        AssetManager am = context.getApplicationContext().getAssets();

        return Typeface.createFromAsset(am, name);
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
}
