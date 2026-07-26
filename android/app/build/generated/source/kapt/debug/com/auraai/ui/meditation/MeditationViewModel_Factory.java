package com.auraai.ui.meditation;

import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.MeditationUseCases;
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
public final class MeditationViewModel_Factory implements Factory<MeditationViewModel> {
  private final Provider<MeditationUseCases> useCasesProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  public MeditationViewModel_Factory(Provider<MeditationUseCases> useCasesProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    this.useCasesProvider = useCasesProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
  }

  @Override
  public MeditationViewModel get() {
    return newInstance(useCasesProvider.get(), getTokenUseCaseProvider.get());
  }

  public static MeditationViewModel_Factory create(Provider<MeditationUseCases> useCasesProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    return new MeditationViewModel_Factory(useCasesProvider, getTokenUseCaseProvider);
  }

  public static MeditationViewModel newInstance(MeditationUseCases useCases,
      GetCurrentUserTokenUseCase getTokenUseCase) {
    return new MeditationViewModel(useCases, getTokenUseCase);
  }
}
