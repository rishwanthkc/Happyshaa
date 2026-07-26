package com.auraai.domain.usecase

import com.auraai.domain.model.Contact
import com.auraai.domain.repository.ContactsRepository
import javax.inject.Inject

data class ContactsUseCases(
    val getContacts: GetContactsUseCase,
    val createContact: CreateContactUseCase,
    val updateContact: UpdateContactUseCase,
    val deleteContact: DeleteContactUseCase,
    val toggleFavoriteContact: ToggleFavoriteContactUseCase
)

class GetContactsUseCase @Inject constructor(private val repo: ContactsRepository) {
    suspend operator fun invoke(token: String): Result<List<Contact>> = repo.getContacts(token)
}

class CreateContactUseCase @Inject constructor(private val repo: ContactsRepository) {
    suspend operator fun invoke(
        token: String,
        name: String,
        phone: String,
        email: String?,
        relationshipLabel: String,
        isFavorite: Boolean,
        isEmergency: Boolean
    ): Result<Contact> = repo.createContact(token, name, phone, email, relationshipLabel, isFavorite, isEmergency)
}

class UpdateContactUseCase @Inject constructor(private val repo: ContactsRepository) {
    suspend operator fun invoke(
        token: String,
        contactId: String,
        name: String?,
        phone: String?,
        email: String?,
        relationshipLabel: String?,
        isFavorite: Boolean?,
        isEmergency: Boolean?
    ): Result<Unit> = repo.updateContact(token, contactId, name, phone, email, relationshipLabel, isFavorite, isEmergency)
}

class DeleteContactUseCase @Inject constructor(private val repo: ContactsRepository) {
    suspend operator fun invoke(token: String, contactId: String): Result<Unit> = repo.deleteContact(token, contactId)
}

class ToggleFavoriteContactUseCase @Inject constructor(private val repo: ContactsRepository) {
    suspend operator fun invoke(token: String, contactId: String): Result<Boolean> = repo.toggleFavoriteContact(token, contactId)
}
