package facesnap.emazdoor.com.facesnap;

import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BackendService_Factory implements Factory<BackendService> {
  private final MembersInjector<BackendService> backendServiceMembersInjector;

  private final Provider<String> serverUrlProvider;

  public BackendService_Factory(
      MembersInjector<BackendService> backendServiceMembersInjector,
      Provider<String> serverUrlProvider) {
    assert backendServiceMembersInjector != null;
    this.backendServiceMembersInjector = backendServiceMembersInjector;
    assert serverUrlProvider != null;
    this.serverUrlProvider = serverUrlProvider;
  }

  @Override
  public BackendService get() {
    return MembersInjectors.injectMembers(
        backendServiceMembersInjector, new BackendService(serverUrlProvider.get()));
  }

  public static Factory<BackendService> create(
      MembersInjector<BackendService> backendServiceMembersInjector,
      Provider<String> serverUrlProvider) {
    return new BackendService_Factory(backendServiceMembersInjector, serverUrlProvider);
  }
}
