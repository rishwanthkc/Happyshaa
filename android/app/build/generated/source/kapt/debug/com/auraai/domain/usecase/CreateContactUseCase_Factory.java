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
public final class CreateContactUseCase_Factory implements Factory<CreateContactUseCase> {
  private final Provider<ContactsRepository> repoProvider;

  public CreateContactUseCase_Factory(Provider<ContactsRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public CreateContactUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static CreateContactUseCase_Factory create(Provider<ContactsRepository> repoProvider) {
    return new CreateContactUseCase_Factory(repoProvider);
  }

  public static CreateContactUseCase newInstance(ContactsRepository repo) {
    return new CreateContactUseCase(repo);
  }
}
