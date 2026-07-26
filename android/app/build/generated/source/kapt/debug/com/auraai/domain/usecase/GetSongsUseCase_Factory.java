package com.auraai.domain.usecase;

import com.auraai.domain.repository.MusicRepository;
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
public final class GetSongsUseCase_Factory implements Factory<GetSongsUseCase> {
  private final Provider<MusicRepository> repoProvider;

  public GetSongsUseCase_Factory(Provider<MusicRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetSongsUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetSongsUseCase_Factory create(Provider<MusicRepository> repoProvider) {
    return new GetSongsUseCase_Factory(repoProvider);
  }

  public static GetSongsUseCase newInstance(MusicRepository repo) {
    return new GetSongsUseCase(repo);
  }
}
