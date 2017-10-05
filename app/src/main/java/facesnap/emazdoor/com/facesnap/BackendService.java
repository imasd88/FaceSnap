package facesnap.emazdoor.com.facesnap;

/**
 * Created by Ahmed on 10/5/2017.
 */

import javax.inject.*;

public class BackendService {
    @Inject
    public User user;

    private String serverUrl;

    @Inject
    public BackendService(@Named("serverUrl") String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public boolean callServer() {
        if (user != null && serverUrl != null && serverUrl.length() > 0) {
            System.out.println("User: " + user + " ServerUrl: " + serverUrl);
            return true;
        }
        return false;
    }
}
