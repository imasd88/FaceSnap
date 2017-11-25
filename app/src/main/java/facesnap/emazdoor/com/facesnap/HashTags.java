package facesnap.emazdoor.com.facesnap;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmed on 11/25/2017.
 */

public class HashTags extends Dialog {

    @BindView(R.id.titleTv)
    TextView title_textView;
    @BindView(R.id.hashTagsET)
    EditText hashTags_editText;
    @BindView(R.id.applyBtn)
    Button apply_button;
    @BindView(R.id.cancelBtn)
    Button cancel_button;

    Context context = getContext();

    public HashTags(@NonNull Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(HashTags.this);
    }

    @OnClick(R.id.cancelBtn)
    public void cancelDialog() {
        this.dismiss();
    }

    @OnClick(R.id.applyBtn)
    public void applyHashTags() {
        if (hashTags_editText.length() < 1) {

        }
    }
}
