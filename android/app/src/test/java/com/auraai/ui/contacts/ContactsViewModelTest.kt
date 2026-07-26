package com.auraai.ui.contacts

import com.auraai.domain.model.Contact
import com.auraai.domain.usecase.GetContactsUseCase
import com.auraai.domain.usecase.CreateContactUseCase
import com.auraai.domain.usecase.UpdateContactUseCase
import com.auraai.domain.usecase.DeleteContactUseCase
import com.auraai.domain.usecase.ToggleFavoriteContactUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ContactsViewModelTest {

    @Mock
    private lateinit var getContactsUseCase: GetContactsUseCase
    @Mock
    private lateinit var createContactUseCase: CreateContactUseCase
    @Mock
    private lateinit var updateContactUseCase: UpdateContactUseCase
    @Mock
    private lateinit var deleteContactUseCase: DeleteContactUseCase
    @Mock
    private lateinit var toggleFavoriteContactUseCase: ToggleFavoriteContactUseCase
    @Mock
    private lateinit var getTokenUseCase: GetCurrentUserTokenUseCase

    private lateinit var viewModel: ContactsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = ContactsViewModel(
            getContactsUseCase,
            createContactUseCase,
            updateContactUseCase,
            deleteContactUseCase,
            toggleFavoriteContactUseCase,
            getTokenUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadContacts_success_updatesContactsState() = runTest {
        val token = "mock_token"
        val mockList = listOf(Contact("contact1", "uid", "Buddy", "1234", "mail", "Friend", false, true))

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(getContactsUseCase(token)).thenReturn(Result.success(mockList))

        viewModel.loadContacts()
        advanceUntilIdle()

        verify(getTokenUseCase).invoke()
        verify(getContactsUseCase).invoke(token)
        assertEquals(mockList, viewModel.contacts.value)
    }

    @Test
    fun addContact_success_triggersReload() = runTest {
        val token = "mock_token"
        val contact = Contact("contact1", "uid", "Buddy", "123", "mail", "Friend", false, true)

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(createContactUseCase(token, "Buddy", "123", "mail", "Friend", false, true))
            .thenReturn(Result.success(contact))
        `when`(getContactsUseCase(token)).thenReturn(Result.success(listOf(contact)))

        viewModel.addContact("Buddy", "123", "mail", "Friend", false, true)
        advanceUntilIdle()

        verify(createContactUseCase).invoke(token, "Buddy", "123", "mail", "Friend", false, true)
    }
}
