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
public final class GetChatResponseStreamUseCase_Factory implements Factory<GetChatResponseStreamUseCase> {
  private final Provider<ChatRepository> repositoryProvider;

  public GetChatResponseStreamUseCase_Factory(Provider<ChatRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetChatResponseStreamUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetChatResponseStreamUseCase_Factory create(
      Provider<ChatRepository> repositoryProvider) {
    return new GetChatResponseStreamUseCase_Factory(repositoryProvider);
  }

  public static GetChatResponseStreamUseCase newInstance(ChatRepository repository) {
    return new GetChatResponseStreamUseCase(repository);
  }
}
