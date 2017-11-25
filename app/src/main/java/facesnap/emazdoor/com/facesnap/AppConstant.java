package facesnap.emazdoor.com.facesnap;

import android.os.Environment;

public class AppConstant {

    public static final String SHARED_PREF_NAME = "demo_twitter_oauth";
    public static final String SHARED_PREF_KEY_SECRET = "demo_oauth_token_secret";
    public static final String SHARED_PREF_KEY_TOKEN = "demo_oauth_token";

    public static final String TWITTER_CALLBACK_URL = "https://www.exaptec.com.au";//"x-demo-twitter://demotwitterlogin";

    public static final String IEXTRA_AUTH_URL = "auth_url";
    public static final String IEXTRA_OAUTH_VERIFIER = "oauth_verifier";
    public static final String IEXTRA_OAUTH_TOKEN = "oauth_token";


    public static final String IMAGE_LOCATION = Environment.getExternalStorageDirectory() + "/DCIM/PixElf";

}