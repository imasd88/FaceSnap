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
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;
import android.widget.TextView;
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


import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.message;
import static android.content.ContentValues.TAG;

/**
 * Created by Ahmed on 10/10/2017.
 */

public class SocialActivity extends Activity {


    @BindView(R.id.postButton)
    Button postIt;
    Context context;
    private SocialAuthAdapter socialAuthAdapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Bitmap capturedImage;
    @BindView(R.id.capturedImage)
    ImageView _image;
    String tweetMsg = "#Exaptec #Robotics #RethinkEverything #Innodev" +
            " #Sanbot #Telepresence #" +
            "RandD #ExaptecCSLAM " +
            "#RoboticCloudSolutions #ExaptecRaaS #SensorFusion";
    String hashTags = "#Exaptec #RethinkEverything #Innodev #Sanbot";
    @BindView(R.id.constrainLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.messageTweet)
    EditText mEditText;
    @BindView(R.id.wordCount)
    TextView tvWordCount;
    private static int wordCount = 96;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        context = this;
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        Intent bundle = getIntent();
        final Uri myUri = Uri.parse(bundle.getStringExtra("image"));
        try {
            capturedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), myUri);
            _image.setImageBitmap(capturedImage);
            _image.setVisibility(View.GONE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        constraintLayout.setBackground(new BitmapDrawable(getResources(), capturedImage));
        tvWordCount.setText(String.valueOf(wordCount));

        mEditText.addTextChangedListener(mTextEditorWatcher);
        postIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSocialAdapter();
            }
        });
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvWordCount.setText(String.valueOf(wordCount--));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private void initSocialAdapter() {
        // Utils.isOnline method check the internet connection
        if (Utils.isOnline(getApplicationContext())) {
            // Initialize the socialAuthAdapter with ResponseListener
            progressBar.setVisibility(View.VISIBLE);
            socialAuthAdapter = new SocialAuthAdapter(new ResponseListener(
                    ((BitmapDrawable) _image.getDrawable()).getBitmap(),
                    isCustomMessage(mEditText.getText().toString())));
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

    private String isCustomMessage(String message) {

        if (message.length() > 0) {
            return message + " " + hashTags;
        } else {
            return tweetMsg;
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

