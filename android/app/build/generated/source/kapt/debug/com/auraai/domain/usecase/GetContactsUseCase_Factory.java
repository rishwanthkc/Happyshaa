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
public final class GetContactsUseCase_Factory implements Factory<GetContactsUseCase> {
  private final Provider<ContactsRepository> repoProvider;

  public GetContactsUseCase_Factory(Provider<ContactsRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetContactsUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetContactsUseCase_Factory create(Provider<ContactsRepository> repoProvider) {
    return new GetContactsUseCase_Factory(repoProvider);
  }

  public static GetContactsUseCase newInstance(ContactsRepository repo) {
    return new GetContactsUseCase(repo);
  }
}
