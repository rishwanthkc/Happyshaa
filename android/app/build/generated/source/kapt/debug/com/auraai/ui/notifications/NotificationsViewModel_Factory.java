package com.auraai.ui.notifications;

import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.GetNotificationHistoryUseCase;
import com.auraai.domain.usecase.SaveFcmTokenUseCase;
import com.auraai.domain.usecase.SendTestNotificationUseCase;
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
public final class NotificationsViewModel_Factory implements Factory<NotificationsViewModel> {
  private final Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider;

  private final Provider<GetNotificationHistoryUseCase> getNotificationHistoryUseCaseProvider;

  private final Provider<SendTestNotificationUseCase> sendTestNotificationUseCaseProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  public NotificationsViewModel_Factory(Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider,
      Provider<GetNotificationHistoryUseCase> getNotificationHistoryUseCaseProvider,
      Provider<SendTestNotificationUseCase> sendTestNotificationUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    this.saveFcmTokenUseCaseProvider = saveFcmTokenUseCaseProvider;
    this.getNotificationHistoryUseCaseProvider = getNotificationHistoryUseCaseProvider;
    this.sendTestNotificationUseCaseProvider = sendTestNotificationUseCaseProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
  }

  @Override
  public NotificationsViewModel get() {
    return newInstance(saveFcmTokenUseCaseProvider.get(), getNotificationHistoryUseCaseProvider.get(), sendTestNotificationUseCaseProvider.get(), getTokenUseCaseProvider.get());
  }

  public static NotificationsViewModel_Factory create(
      Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider,
      Provider<GetNotificationHistoryUseCase> getNotificationHistoryUseCaseProvider,
      Provider<SendTestNotificationUseCase> sendTestNotificationUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    return new NotificationsViewModel_Factory(saveFcmTokenUseCaseProvider, getNotificationHistoryUseCaseProvider, sendTestNotificationUseCaseProvider, getTokenUseCaseProvider);
  }

  public static NotificationsViewModel newInstance(SaveFcmTokenUseCase saveFcmTokenUseCase,
      GetNotificationHistoryUseCase getNotificationHistoryUseCase,
      SendTestNotificationUseCase sendTestNotificationUseCase,
      GetCurrentUserTokenUseCase getTokenUseCase) {
    return new NotificationsViewModel(saveFcmTokenUseCase, getNotificationHistoryUseCase, sendTestNotificationUseCase, getTokenUseCase);
  }
}
