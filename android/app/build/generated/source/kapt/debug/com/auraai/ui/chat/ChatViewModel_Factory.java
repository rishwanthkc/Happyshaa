package com.auraai.ui.chat;

import com.auraai.domain.usecase.GetChatHistoryUseCase;
import com.auraai.domain.usecase.GetChatResponseStreamUseCase;
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<GetChatHistoryUseCase> getHistoryUseCaseProvider;

  private final Provider<GetChatResponseStreamUseCase> getStreamUseCaseProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  public ChatViewModel_Factory(Provider<GetChatHistoryUseCase> getHistoryUseCaseProvider,
      Provider<GetChatResponseStreamUseCase> getStreamUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    this.getHistoryUseCaseProvider = getHistoryUseCaseProvider;
    this.getStreamUseCaseProvider = getStreamUseCaseProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(getHistoryUseCaseProvider.get(), getStreamUseCaseProvider.get(), getTokenUseCaseProvider.get());
  }

  public static ChatViewModel_Factory create(
      Provider<GetChatHistoryUseCase> getHistoryUseCaseProvider,
      Provider<GetChatResponseStreamUseCase> getStreamUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    return new ChatViewModel_Factory(getHistoryUseCaseProvider, getStreamUseCaseProvider, getTokenUseCaseProvider);
  }

  public static ChatViewModel newInstance(GetChatHistoryUseCase getHistoryUseCase,
      GetChatResponseStreamUseCase getStreamUseCase, GetCurrentUserTokenUseCase getTokenUseCase) {
    return new ChatViewModel(getHistoryUseCase, getStreamUseCase, getTokenUseCase);
  }
}
