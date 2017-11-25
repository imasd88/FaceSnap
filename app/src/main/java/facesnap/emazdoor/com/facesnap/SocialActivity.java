package facesnap.emazdoor.com.facesnap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import static android.R.id.switch_widget;
import static android.content.ContentValues.TAG;

/**
 * Created by Ahmed on 10/10/2017.
 */

public class SocialActivity extends Activity implements View.OnClickListener {

    Context context;
    private SocialAuthAdapter socialAuthAdapter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Bitmap capturedImage;
    @BindView(R.id.capturedImage)
    ImageView _image;
    @BindView(R.id.constrainLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.messageTweet)
    EditText mEditText;
    @BindView(R.id.wordCount)
    TextView tvWordCount;
    @BindView(R.id.postButton)
    Button postIt;
    @BindView(R.id.cancelButton)
    Button cancelButton;
    String tweetMsg;
    String longHashTags;
    String shortHashTags;


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
        //retrieve the image uri link from bundle
        final Uri myUri = Uri.parse(bundle.getStringExtra("image"));
        //read preferences here for longHashTags and shortHashTags
        longHashTags = Utils.getPreferances(this, "LongHashTags");
        shortHashTags = Utils.getPreferances(this, "ShortHashTags");

        try {
            capturedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), myUri);
            _image.setImageBitmap(capturedImage);
            _image.setVisibility(View.GONE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //set the captured image as background
        constraintLayout.setBackground(new BitmapDrawable(getResources(), capturedImage));
        //default: set predefined Hash Tags to mEditText
        mEditText.setHint(longHashTags);

        //set the word count
        //tvWordCount.setText(String.valueOf(wordCount));
        //disable word limit on default
        tvWordCount.setVisibility(View.GONE);
        //assign text observer to mEditText here to limit message to word count
        mEditText.addTextChangedListener(mTextEditorWatcher);

        postIt.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        int wordCount = 140;

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //enable word limit
            tvWordCount.setVisibility(View.VISIBLE);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            System.out.println("start " + start + " before " + before);
            wordCount = wordCount - count;


            //show remaining words for message
            tvWordCount.setText(String.valueOf(wordCount));
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
            return message + " " + shortHashTags;
        } else {
            return longHashTags;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postButton:
                initSocialAdapter();
                break;
            case R.id.cancelButton:
                finish();
                break;
            default:
                break;
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

