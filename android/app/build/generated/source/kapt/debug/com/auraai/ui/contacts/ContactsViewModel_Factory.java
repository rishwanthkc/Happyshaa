package com.auraai.ui.contacts;

import com.auraai.domain.usecase.CreateContactUseCase;
import com.auraai.domain.usecase.DeleteContactUseCase;
import com.auraai.domain.usecase.GetContactsUseCase;
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.ToggleFavoriteContactUseCase;
import com.auraai.domain.usecase.UpdateContactUseCase;
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
public final class ContactsViewModel_Factory implements Factory<ContactsViewModel> {
  private final Provider<GetContactsUseCase> getContactsUseCaseProvider;

  private final Provider<CreateContactUseCase> createContactUseCaseProvider;

  private final Provider<UpdateContactUseCase> updateContactUseCaseProvider;

  private final Provider<DeleteContactUseCase> deleteContactUseCaseProvider;

  private final Provider<ToggleFavoriteContactUseCase> toggleFavoriteContactUseCaseProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  public ContactsViewModel_Factory(Provider<GetContactsUseCase> getContactsUseCaseProvider,
      Provider<CreateContactUseCase> createContactUseCaseProvider,
      Provider<UpdateContactUseCase> updateContactUseCaseProvider,
      Provider<DeleteContactUseCase> deleteContactUseCaseProvider,
      Provider<ToggleFavoriteContactUseCase> toggleFavoriteContactUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    this.getContactsUseCaseProvider = getContactsUseCaseProvider;
    this.createContactUseCaseProvider = createContactUseCaseProvider;
    this.updateContactUseCaseProvider = updateContactUseCaseProvider;
    this.deleteContactUseCaseProvider = deleteContactUseCaseProvider;
    this.toggleFavoriteContactUseCaseProvider = toggleFavoriteContactUseCaseProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
  }

  @Override
  public ContactsViewModel get() {
    return newInstance(getContactsUseCaseProvider.get(), createContactUseCaseProvider.get(), updateContactUseCaseProvider.get(), deleteContactUseCaseProvider.get(), toggleFavoriteContactUseCaseProvider.get(), getTokenUseCaseProvider.get());
  }

  public static ContactsViewModel_Factory create(
      Provider<GetContactsUseCase> getContactsUseCaseProvider,
      Provider<CreateContactUseCase> createContactUseCaseProvider,
      Provider<UpdateContactUseCase> updateContactUseCaseProvider,
      Provider<DeleteContactUseCase> deleteContactUseCaseProvider,
      Provider<ToggleFavoriteContactUseCase> toggleFavoriteContactUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    return new ContactsViewModel_Factory(getContactsUseCaseProvider, createContactUseCaseProvider, updateContactUseCaseProvider, deleteContactUseCaseProvider, toggleFavoriteContactUseCaseProvider, getTokenUseCaseProvider);
  }

  public static ContactsViewModel newInstance(GetContactsUseCase getContactsUseCase,
      CreateContactUseCase createContactUseCase, UpdateContactUseCase updateContactUseCase,
      DeleteContactUseCase deleteContactUseCase,
      ToggleFavoriteContactUseCase toggleFavoriteContactUseCase,
      GetCurrentUserTokenUseCase getTokenUseCase) {
    return new ContactsViewModel(getContactsUseCase, createContactUseCase, updateContactUseCase, deleteContactUseCase, toggleFavoriteContactUseCase, getTokenUseCase);
  }
}
