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
public final class GetFavoriteSongsUseCase_Factory implements Factory<GetFavoriteSongsUseCase> {
  private final Provider<MusicRepository> repoProvider;

  public GetFavoriteSongsUseCase_Factory(Provider<MusicRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetFavoriteSongsUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetFavoriteSongsUseCase_Factory create(Provider<MusicRepository> repoProvider) {
    return new GetFavoriteSongsUseCase_Factory(repoProvider);
  }

  public static GetFavoriteSongsUseCase newInstance(MusicRepository repo) {
    return new GetFavoriteSongsUseCase(repo);
  }
}
