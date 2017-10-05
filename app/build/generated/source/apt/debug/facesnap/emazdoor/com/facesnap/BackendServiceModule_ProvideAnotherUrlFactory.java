package facesnap.emazdoor.com.facesnap;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BackendServiceModule_ProvideAnotherUrlFactory implements Factory<String> {
  private final BackendServiceModule module;

  public BackendServiceModule_ProvideAnotherUrlFactory(BackendServiceModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public String get() {
    return Preconditions.checkNotNull(
        module.provideAnotherUrl(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<String> create(BackendServiceModule module) {
    return new BackendServiceModule_ProvideAnotherUrlFactory(module);
  }

  /** Proxies {@link BackendServiceModule#provideAnotherUrl()}. */
  public static String proxyProvideAnotherUrl(BackendServiceModule instance) {
    return instance.provideAnotherUrl();
  }
}
