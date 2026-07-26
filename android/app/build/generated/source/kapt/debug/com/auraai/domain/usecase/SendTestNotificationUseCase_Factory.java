package com.auraai.domain.usecase;

import com.auraai.domain.repository.NotificationsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class SendTestNotificationUseCase_Factory implements Factory<SendTestNotificationUseCase> {
  private final Provider<NotificationsRepository> repoProvider;

  public SendTestNotificationUseCase_Factory(Provider<NotificationsRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public SendTestNotificationUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static SendTestNotificationUseCase_Factory create(
      Provider<NotificationsRepository> repoProvider) {
    return new SendTestNotificationUseCase_Factory(repoProvider);
  }

  public static SendTestNotificationUseCase newInstance(NotificationsRepository repo) {
    return new SendTestNotificationUseCase(repo);
  }
}
