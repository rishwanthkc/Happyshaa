package com.auraai.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.Contact
import com.auraai.domain.usecase.GetContactsUseCase
import com.auraai.domain.usecase.CreateContactUseCase
import com.auraai.domain.usecase.UpdateContactUseCase
import com.auraai.domain.usecase.DeleteContactUseCase
import com.auraai.domain.usecase.ToggleFavoriteContactUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val createContactUseCase: CreateContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val toggleFavoriteContactUseCase: ToggleFavoriteContactUseCase,
    private val getTokenUseCase: GetCurrentUserTokenUseCase
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadContacts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                getContactsUseCase(token).onSuccess {
                    _contacts.value = it
                }.onFailure {
                    _errorMessage.value = "Failed to load support contacts: ${it.localizedMessage}"
                }
            }.onFailure {
                _errorMessage.value = "Auth token lookup failed: ${it.localizedMessage}"
            }
            _isLoading.value = false
        }
    }

    fun addContact(
        name: String,
        phone: String,
        email: String?,
        relationship: String,
        isFavorite: Boolean,
        isEmergency: Boolean
    ) {
        viewModelScope.launch {
            getTokenUseCase().onSuccess { token ->
                createContactUseCase(token, name, phone, email, relationship, isFavorite, isEmergency).onSuccess {
                    loadContacts() // Refresh list
                }
            }
        }
    }

    fun editContact(
        contactId: String,
        name: String?,
        phone: String?,
        email: String?,
        relationship: String?,
        isFavorite: Boolean?,
        isEmergency: Boolean?
    ) {
        viewModelScope.launch {
            getTokenUseCase().onSuccess { token ->
                updateContactUseCase(token, contactId, name, phone, email, relationship, isFavorite, isEmergency).onSuccess {
                    loadContacts() // Refresh list
                }
            }
        }
    }

    fun removeContact(contactId: String) {
        viewModelScope.launch {
            getTokenUseCase().onSuccess { token ->
                deleteContactUseCase(token, contactId).onSuccess {
                    loadContacts() // Refresh list
                }
            }
        }
    }

    fun toggleFavorite(contactId: String) {
        viewModelScope.launch {
            getTokenUseCase().onSuccess { token ->
                toggleFavoriteContactUseCase(token, contactId).onSuccess {
                    loadContacts() // Refresh list
                }
            }
        }
    }

    private val _deviceContacts = MutableStateFlow<List<DeviceContact>>(emptyList())
    val deviceContacts: StateFlow<List<DeviceContact>> = _deviceContacts.asStateFlow()

    fun loadDeviceContacts(context: android.content.Context) {
        viewModelScope.launch {
            val list = mutableListOf<DeviceContact>()
            try {
                val resolver = context.contentResolver
                val cursor = resolver.query(
                    android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    arrayOf(
                        android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER
                    ),
                    null,
                    null,
                    null
                )
                cursor?.use {
                    val nameIdx = it.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val phoneIdx = it.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER)
                    while (it.moveToNext()) {
                        val name = it.getString(nameIdx)
                        val phone = it.getString(phoneIdx)
                        if (name != null && phone != null) {
                            list.add(DeviceContact(name, phone))
                        }
                    }
                }
            } catch (e: Exception) {
                // Safe fallback for permissions
            }
            _deviceContacts.value = list.distinctBy { it.phone }
        }
    }
}

data class DeviceContact(val name: String, val phone: String)
