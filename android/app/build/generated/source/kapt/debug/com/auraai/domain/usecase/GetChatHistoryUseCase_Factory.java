package com.auraai.domain.usecase;

import com.auraai.domain.repository.ChatRepository;
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
public final class GetChatHistoryUseCase_Factory implements Factory<GetChatHistoryUseCase> {
  private final Provider<ChatRepository> repositoryProvider;

  public GetChatHistoryUseCase_Factory(Provider<ChatRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetChatHistoryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetChatHistoryUseCase_Factory create(Provider<ChatRepository> repositoryProvider) {
    return new GetChatHistoryUseCase_Factory(repositoryProvider);
  }

  public static GetChatHistoryUseCase newInstance(ChatRepository repository) {
    return new GetChatHistoryUseCase(repository);
  }
}
