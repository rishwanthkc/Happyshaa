package com.auraai.data.repository

import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.ContactCreateRequest
import com.auraai.data.remote.api.ContactUpdateRequest
import com.auraai.domain.model.Contact
import com.auraai.domain.repository.ContactsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService
) : ContactsRepository {

    private val offlineContacts = mutableListOf<Contact>()

    override suspend fun getContacts(token: String): Result<List<Contact>> {
        return try {
            val response = apiService.getContacts(token)
            val mapped = response.map {
                Contact(
                    contactId = it.contact_id,
                    uid = it.uid,
                    name = it.name,
                    phone = it.phone,
                    email = it.email,
                    relationshipLabel = it.relationship_label,
                    isFavorite = it.is_favorite,
                    isEmergency = it.is_emergency
                )
            }
            offlineContacts.clear()
            offlineContacts.addAll(mapped)
            Result.success(mapped)
        } catch (e: Exception) {
            Result.success(offlineContacts)
        }
    }

    override suspend fun createContact(
        token: String,
        name: String,
        phone: String,
        email: String?,
        relationshipLabel: String,
        isFavorite: Boolean,
        isEmergency: Boolean
    ): Result<Contact> {
        return try {
            val request = ContactCreateRequest(name, phone, email, relationshipLabel, isFavorite, isEmergency)
            val res = apiService.createContact(token, request)
            val newContact = Contact(
                contactId = res.contact_id,
                uid = res.uid,
                name = res.name,
                phone = res.phone,
                email = res.email,
                relationshipLabel = res.relationship_label,
                isFavorite = res.is_favorite,
                isEmergency = res.is_emergency
            )
            offlineContacts.add(newContact)
            Result.success(newContact)
        } catch (e: Exception) {
            val mockId = "offline_${System.currentTimeMillis()}"
            val newContact = Contact(mockId, "offline_user", name, phone, email, relationshipLabel, isFavorite, isEmergency)
            offlineContacts.add(newContact)
            Result.success(newContact)
        }
    }

    override suspend fun updateContact(
        token: String,
        contactId: String,
        name: String?,
        phone: String?,
        email: String?,
        relationshipLabel: String?,
        isFavorite: Boolean?,
        isEmergency: Boolean?
    ): Result<Unit> {
        return try {
            val request = ContactUpdateRequest(name, phone, email, relationshipLabel, isFavorite, isEmergency)
            apiService.updateContact(token, contactId, request)
            
            // Sync local state
            val idx = offlineContacts.indexOfFirst { it.contactId == contactId }
            if (idx != -1) {
                val old = offlineContacts[idx]
                offlineContacts[idx] = old.copy(
                    name = name ?: old.name,
                    phone = phone ?: old.phone,
                    email = email ?: old.email,
                    relationshipLabel = relationshipLabel ?: old.relationshipLabel,
                    isFavorite = isFavorite ?: old.isFavorite,
                    isEmergency = isEmergency ?: old.isEmergency
                )
            }
            Result.success(Unit)
        } catch (e: Exception) {
            val idx = offlineContacts.indexOfFirst { it.contactId == contactId }
            if (idx != -1) {
                val old = offlineContacts[idx]
                offlineContacts[idx] = old.copy(
                    name = name ?: old.name,
                    phone = phone ?: old.phone,
                    email = email ?: old.email,
                    relationshipLabel = relationshipLabel ?: old.relationshipLabel,
                    isFavorite = isFavorite ?: old.isFavorite,
                    isEmergency = isEmergency ?: old.isEmergency
                )
            }
            Result.success(Unit)
        }
    }

    override suspend fun deleteContact(token: String, contactId: String): Result<Unit> {
        return try {
            apiService.deleteContact(token, contactId)
            offlineContacts.removeAll { it.contactId == contactId }
            Result.success(Unit)
        } catch (e: Exception) {
            offlineContacts.removeAll { it.contactId == contactId }
            Result.success(Unit)
        }
    }

    override suspend fun toggleFavoriteContact(token: String, contactId: String): Result<Boolean> {
        return try {
            apiService.toggleFavoriteContact(token, contactId)
            val idx = offlineContacts.indexOfFirst { it.contactId == contactId }
            var isFav = false
            if (idx != -1) {
                isFav = !offlineContacts[idx].isFavorite
                offlineContacts[idx] = offlineContacts[idx].copy(isFavorite = isFav)
            }
            Result.success(isFav)
        } catch (e: Exception) {
            val idx = offlineContacts.indexOfFirst { it.contactId == contactId }
            var isFav = false
            if (idx != -1) {
                isFav = !offlineContacts[idx].isFavorite
                offlineContacts[idx] = offlineContacts[idx].copy(isFavorite = isFav)
            }
            Result.success(isFav)
        }
    }
}
