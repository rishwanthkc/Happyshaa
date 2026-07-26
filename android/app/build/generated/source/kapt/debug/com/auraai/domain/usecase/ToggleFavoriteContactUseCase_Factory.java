package com.auraai.domain.usecase;

import com.auraai.domain.repository.ContactsRepository;
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
public final class ToggleFavoriteContactUseCase_Factory implements Factory<ToggleFavoriteContactUseCase> {
  private final Provider<ContactsRepository> repoProvider;

  public ToggleFavoriteContactUseCase_Factory(Provider<ContactsRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public ToggleFavoriteContactUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static ToggleFavoriteContactUseCase_Factory create(
      Provider<ContactsRepository> repoProvider) {
    return new ToggleFavoriteContactUseCase_Factory(repoProvider);
  }

  public static ToggleFavoriteContactUseCase newInstance(ContactsRepository repo) {
    return new ToggleFavoriteContactUseCase(repo);
  }
}
