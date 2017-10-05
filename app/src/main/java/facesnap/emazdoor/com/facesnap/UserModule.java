package facesnap.emazdoor.com.facesnap;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ahmed on 10/5/2017.
 */

@Module
public class UserModule {

    @Singleton
    @Provides
    User providesUser() {
        return new User("Lars", "Vogel");
    }
}
