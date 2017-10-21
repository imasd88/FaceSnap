package facesnap.emazdoor.com.facesnap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.brickred.socialauth.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


import static android.R.id.message;
import static android.content.ContentValues.TAG;

/**
 * Created by Ahmed on 10/10/2017.
 */

public class SocialActivity extends Activity {

    private Button postIt;
    Context context;
    private Button btnAuthorizeTwitter;
    private SocialAuthAdapter socialAuthAdapter;
    //    public static final String PATH = Utils.getPath();
//    private final int SELECT_IMAGE_FROM_GALLERY = 0, CAPTURE_IMAGE = 1;
    private ProgressBar progressBar;
    Bitmap capturedImage;
    ImageView _image;
    String tweetMsg = "#Exaptec #Robotics #GartnerSYM #Gartner_Events  #SanBot #Telepresence #RandD " +
            "#ExaptecCSLAM " +
            "#RoboticCloudSolutions " +
            "#ExaptecRaaS " +
            "#SensorFusion ";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        context = this;

        _image = (ImageView) findViewById(R.id.capturedImage);
        Intent bundle = getIntent();
        final Uri myUri = Uri.parse(bundle.getStringExtra("image"));
//        Bitmap bitmap = (Bitmap) bundle.getParcelableExtra("image");
        try {
            capturedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), myUri);
            _image.setImageBitmap(capturedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        postIt = (Button) findViewById(R.id.postButton);

        postIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSocialAdapter();
            }
        });

    }

    private void initSocialAdapter() {
        // Utils.isOnline method check the internet connection
        if (Utils.isOnline(getApplicationContext())) {
            // Initialize the socialAuthAdapter with ResponseListener
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            socialAuthAdapter = new SocialAuthAdapter(new ResponseListener(
                    ((BitmapDrawable) _image.getDrawable()).getBitmap(),
                    tweetMsg));
            // Add Twitter to set as provider to post on twitter
            socialAuthAdapter.addProvider(SocialAuthAdapter.Provider.TWITTER, R.drawable.twitter);
            // this line is for Authorize start
            socialAuthAdapter.authorize(SocialActivity.this, SocialAuthAdapter.Provider.TWITTER);
        } else {
            // showing message when internet connection is not available
            Toast.makeText(getApplicationContext(),
                    "Check your internet connection..", Toast.LENGTH_LONG)
                    .show();
        }
    }

    // this ResponseListener class is for getting responce of post uploading
    private class ResponseListener implements DialogListener {
        Bitmap bitmap;
        String message;

        public ResponseListener(Bitmap bitmap, String message) {
            this.bitmap = bitmap;
            this.message = message;
        }

        @Override
        public void onComplete(final Bundle values) {
            // this method is call when successfull authorization is done
            try {
                socialAuthAdapter.uploadImageAsync(message, "The AppGuruz.png",
                        bitmap, 100, new UploadImageListener());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(SocialAuthError error) {
            // this method is call when error is occured in authorization
//            if (progressBar != null && progressBar.isActivated())
            progressBar.setVisibility(View.GONE);
            Log.d("ShareTwitter", "Authentication Error: " + error.getMessage());
        }

        @Override
        public void onCancel() {
            // this method is call when user cancel Authentication
//            if (progressBar != null && progressBar.isActivated())
            progressBar.setVisibility(View.GONE);
            Log.d("ShareTwitter", "Authentication Cancelled");
        }

        @Override
        public void onBack() {
            // this method is call when user backpressed from dialog
//            if (progressBar != null && progressBar.isShown())
            progressBar.setVisibility(View.GONE);
            Log.d("ShareTwitter", "Dialog Closed by pressing Back Key");
        }
    }

    private final class UploadImageListener implements
            SocialAuthListener<Integer> {

        @Override
        public void onError(SocialAuthError e) {
        }

        @Override
        public void onExecute(String arg0, Integer arg1) {
            Integer status = arg1;
            try {
                if (status.intValue() == 200 || status.intValue() == 201
                        || status.intValue() == 204) {
//                    if (progressBar != null && progressBar.isActivated())
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SocialActivity.this, "Image Uploaded",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
//                    if (progressBar != null && progressBar.isActivated())
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SocialActivity.this, "Image not Uploaded",
                            Toast.LENGTH_SHORT).show();
                }

            } catch (NullPointerException e) {
//                if (progressBar != null && progressBar.isActivated())
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SocialActivity.this, "Image not Uploaded",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}

