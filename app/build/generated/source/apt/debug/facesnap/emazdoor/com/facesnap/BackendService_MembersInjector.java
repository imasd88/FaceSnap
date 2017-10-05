package facesnap.emazdoor.com.facesnap;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BackendService_MembersInjector implements MembersInjector<BackendService> {
  private final Provider<User> userProvider;

  public BackendService_MembersInjector(Provider<User> userProvider) {
    assert userProvider != null;
    this.userProvider = userProvider;
  }

  public static MembersInjector<BackendService> create(Provider<User> userProvider) {
    return new BackendService_MembersInjector(userProvider);
  }

  @Override
  public void injectMembers(BackendService instance) {
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    instance.user = userProvider.get();
  }
}
