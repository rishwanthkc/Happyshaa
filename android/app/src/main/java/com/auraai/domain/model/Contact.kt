package com.auraai.domain.model

data class Contact(
    val contactId: String,
    val uid: String,
    val name: String,
    val phone: String,
    val email: String?,
    val relationshipLabel: String,
    val isFavorite: Boolean,
    val isEmergency: Boolean
)
