package facesnap.emazdoor.com.facesnap;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    int TAKE_PHOTO_CODE = 0;
    @BindView(R.id.profile_image)
    ImageView logoImage;
    @BindView(R.id.captureButton)
    Button capture;
    @BindView(R.id.settingsButton)
    ImageButton settingsButton;
    ConstraintLayout constraintLayout;
    Uri outputFileUri;
    SlideEffect slideEffect;


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

        constraintLayout = (ConstraintLayout) findViewById(R.id.background);
        slideEffect = new SlideEffect();
        slideEffect.mHandler(constraintLayout);

        ((AppCompatTextView) findViewById(R.id.textView)).setTypeface(getFont(this, FONT_MUSEO_700));
        ((AppCompatTextView) findViewById(R.id.textView2)).setTypeface(getFont(this, FONT_MUSEO_700));
        ((AppCompatTextView) findViewById(R.id.textView3)).setTypeface(getFont(this, FONT_MUSEO_700));


        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                //Here images will be saved in the dir as
//                //time in millisecs.
                File dir = new File(AppConstant.IMAGE_LOCATION);
                String file = System.currentTimeMillis() + ".jpg";
                File filename = new File(dir, file);
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


    @OnClick(R.id.settingsButton)
    public void setSettingsButton() {
        startActivity(new Intent(MainActivity.this, Settings.class));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
