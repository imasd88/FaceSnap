package facesnap.emazdoor.com.facesnap;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BackendServiceModule_ProvideBackendServiceFactory
    implements Factory<BackendService> {
  private final BackendServiceModule module;

  private final Provider<String> serverUrlProvider;

  public BackendServiceModule_ProvideBackendServiceFactory(
      BackendServiceModule module, Provider<String> serverUrlProvider) {
    assert module != null;
    this.module = module;
    assert serverUrlProvider != null;
    this.serverUrlProvider = serverUrlProvider;
  }

  @Override
  public BackendService get() {
    return Preconditions.checkNotNull(
        module.provideBackendService(serverUrlProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<BackendService> create(
      BackendServiceModule module, Provider<String> serverUrlProvider) {
    return new BackendServiceModule_ProvideBackendServiceFactory(module, serverUrlProvider);
  }

  /** Proxies {@link BackendServiceModule#provideBackendService(String)}. */
  public static BackendService proxyProvideBackendService(
      BackendServiceModule instance, String serverUrl) {
    return instance.provideBackendService(serverUrl);
  }
}
