package facesnap.emazdoor.com.facesnap;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UserModule_ProvidesUserFactory implements Factory<User> {
  private final UserModule module;

  public UserModule_ProvidesUserFactory(UserModule module) {
    assert module != null;
    this.module = module;
  }

  @Override
  public User get() {
    return Preconditions.checkNotNull(
        module.providesUser(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<User> create(UserModule module) {
    return new UserModule_ProvidesUserFactory(module);
  }

  /** Proxies {@link UserModule#providesUser()}. */
  public static User proxyProvidesUser(UserModule instance) {
    return instance.providesUser();
  }
}
