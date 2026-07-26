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
public final class SaveFcmTokenUseCase_Factory implements Factory<SaveFcmTokenUseCase> {
  private final Provider<NotificationsRepository> repoProvider;

  public SaveFcmTokenUseCase_Factory(Provider<NotificationsRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public SaveFcmTokenUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static SaveFcmTokenUseCase_Factory create(Provider<NotificationsRepository> repoProvider) {
    return new SaveFcmTokenUseCase_Factory(repoProvider);
  }

  public static SaveFcmTokenUseCase newInstance(NotificationsRepository repo) {
    return new SaveFcmTokenUseCase(repo);
  }
}
