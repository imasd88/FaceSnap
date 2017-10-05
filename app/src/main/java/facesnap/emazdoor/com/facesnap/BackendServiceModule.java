package facesnap.emazdoor.com.facesnap;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed on 10/5/2017.
 */

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class BackendServiceModule {
    @Provides
    @Singleton
    BackendService provideBackendService(@Named("serverUrl") String serverUrl) {
        return new BackendService(serverUrl);
    }

    @Provides
    @Named("serverUrl")
    String provideServerUrl() {
        return "http://www.vogella.com";
    }

    @Provides
    @Named("anotherUrl")
    String provideAnotherUrl() {
        return "http://www.google.com";
    }

}

