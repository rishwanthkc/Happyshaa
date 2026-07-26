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
public final class GetNotificationHistoryUseCase_Factory implements Factory<GetNotificationHistoryUseCase> {
  private final Provider<NotificationsRepository> repoProvider;

  public GetNotificationHistoryUseCase_Factory(Provider<NotificationsRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetNotificationHistoryUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetNotificationHistoryUseCase_Factory create(
      Provider<NotificationsRepository> repoProvider) {
    return new GetNotificationHistoryUseCase_Factory(repoProvider);
  }

  public static GetNotificationHistoryUseCase newInstance(NotificationsRepository repo) {
    return new GetNotificationHistoryUseCase(repo);
  }
}
