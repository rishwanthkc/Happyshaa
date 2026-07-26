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
public final class DeleteContactUseCase_Factory implements Factory<DeleteContactUseCase> {
  private final Provider<ContactsRepository> repoProvider;

  public DeleteContactUseCase_Factory(Provider<ContactsRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public DeleteContactUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static DeleteContactUseCase_Factory create(Provider<ContactsRepository> repoProvider) {
    return new DeleteContactUseCase_Factory(repoProvider);
  }

  public static DeleteContactUseCase newInstance(ContactsRepository repo) {
    return new DeleteContactUseCase(repo);
  }
}
