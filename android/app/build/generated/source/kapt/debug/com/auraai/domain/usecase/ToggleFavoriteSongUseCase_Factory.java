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
public final class ToggleFavoriteSongUseCase_Factory implements Factory<ToggleFavoriteSongUseCase> {
  private final Provider<MusicRepository> repoProvider;

  public ToggleFavoriteSongUseCase_Factory(Provider<MusicRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public ToggleFavoriteSongUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static ToggleFavoriteSongUseCase_Factory create(Provider<MusicRepository> repoProvider) {
    return new ToggleFavoriteSongUseCase_Factory(repoProvider);
  }

  public static ToggleFavoriteSongUseCase newInstance(MusicRepository repo) {
    return new ToggleFavoriteSongUseCase(repo);
  }
}
