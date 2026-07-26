package com.auraai.domain.repository

import com.auraai.domain.model.Contact

interface ContactsRepository {
    suspend fun getContacts(token: String): Result<List<Contact>>
    suspend fun createContact(token: String, name: String, phone: String, email: String?, relationshipLabel: String, isFavorite: Boolean, isEmergency: Boolean): Result<Contact>
    suspend fun updateContact(token: String, contactId: String, name: String?, phone: String?, email: String?, relationshipLabel: String?, isFavorite: Boolean?, isEmergency: Boolean?): Result<Unit>
    suspend fun deleteContact(token: String, contactId: String): Result<Unit>
    suspend fun toggleFavoriteContact(token: String, contactId: String): Result<Boolean>
}
