package com.auraai.ui.contacts;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0007J\f\u0010\u0016\u001a\u00060\u0014j\u0002`\u0015H\u0007J\b\u0010\u0017\u001a\u00020\u0014H\u0007J\b\u0010\u0018\u001a\u00020\u0014H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00020\u000e8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\u00108\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/auraai/ui/contacts/ContactsViewModelTest;", "", "()V", "createContactUseCase", "Lcom/auraai/domain/usecase/CreateContactUseCase;", "deleteContactUseCase", "Lcom/auraai/domain/usecase/DeleteContactUseCase;", "getContactsUseCase", "Lcom/auraai/domain/usecase/GetContactsUseCase;", "getTokenUseCase", "Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "toggleFavoriteContactUseCase", "Lcom/auraai/domain/usecase/ToggleFavoriteContactUseCase;", "updateContactUseCase", "Lcom/auraai/domain/usecase/UpdateContactUseCase;", "viewModel", "Lcom/auraai/ui/contacts/ContactsViewModel;", "addContact_success_triggersReload", "", "Lkotlinx/coroutines/test/TestResult;", "loadContacts_success_updatesContactsState", "setUp", "tearDown", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class ContactsViewModelTest {
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetContactsUseCase getContactsUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.CreateContactUseCase createContactUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.UpdateContactUseCase updateContactUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.DeleteContactUseCase deleteContactUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.ToggleFavoriteContactUseCase toggleFavoriteContactUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase;
    private com.auraai.ui.contacts.ContactsViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    
    public ContactsViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
    
    @org.junit.Test()
    public final void loadContacts_success_updatesContactsState() {
    }
    
    @org.junit.Test()
    public final void addContact_success_triggersReload() {
    }
}