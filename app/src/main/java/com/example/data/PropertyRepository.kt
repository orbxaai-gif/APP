package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PropertyRepository {
    private val _properties = MutableStateFlow<List<Property>>(emptyList())
    val properties: StateFlow<List<Property>> = _properties.asStateFlow()

    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients.asStateFlow()

    init {
        _properties.value = listOf(
            Property(
                id = "1",
                transactionType = TransactionType.FOR_SALE,
                propertyType = PropertyType.VILLA,
                wilaya = "Oran",
                baladiya = "Ain El Turk",
                neighborhood = "Cap Falcon",
                detailedAddress = "Rue de la Plage, Villa 14",
                area = 450.0,
                rooms = 5,
                bathrooms = 4,
                numberOfFloors = 2,
                floorNumber = 0,
                propertyCondition = "ممتازة",
                yearBuilt = 2020,
                price = 25000000.0,
                isNegotiable = true,
                paymentMethod = PaymentMethod.CASH,
                description = "Experience luxury with this beautiful villa by the sea.",
                images = listOf("https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80"),
                videoUrl = "",
                hasParking = true,
                hasElevator = false,
                hasBalcony = true,
                hasGarden = true,
                hasPool = true,
                hasHeating = true,
                hasSecurityCameras = true,
                isFurnished = true,
                hasElectricity380V = true,
                hasTwoFacades = true,
                ownerName = "Alexander",
                ownerPhone = "0555001122",
                ownerWhatsApp = "0555001122",
                ownerEmail = "alex@example.com"
            ),
            Property(
                id = "2",
                transactionType = TransactionType.FOR_RENT,
                propertyType = PropertyType.APARTMENT,
                wilaya = "Oran",
                baladiya = "Oran",
                neighborhood = "Akid Lotfi",
                detailedAddress = "Résidence Hasnaoui, Bâtiment A",
                area = 120.0,
                rooms = 3,
                bathrooms = 2,
                numberOfFloors = 10,
                floorNumber = 5,
                propertyCondition = "جديدة",
                yearBuilt = 2022,
                price = 120000.0,
                isNegotiable = false,
                paymentMethod = PaymentMethod.MONTHLY,
                description = "Modern apartment in the heart of Akid Lotfi.",
                images = listOf("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80"),
                videoUrl = "",
                hasParking = true,
                hasElevator = true,
                hasBalcony = true,
                hasGarden = false,
                hasPool = false,
                hasHeating = true,
                hasSecurityCameras = true,
                isFurnished = true,
                hasElectricity380V = false,
                hasTwoFacades = false,
                ownerName = "Sarah",
                ownerPhone = "0777001122",
                ownerWhatsApp = "0777001122",
                ownerEmail = "sarah@example.com"
            )
        )

        _clients.value = listOf(
            Client("1", "Alexander Pierce", "+1 415 555 0192", "Luxe Oceanfront Villa", ClientStatus.ACTIVE, "https://i.pravatar.cc/150?u=1"),
            Client("2", "Sophia Martinez", "+1 305 555 0184", "Downtown Penthouse", ClientStatus.POTENTIAL, "https://i.pravatar.cc/150?u=2"),
            Client("3", "TechNova Corp", "+1 512 555 0100", "Modern Tech Office", ClientStatus.CLOSED, "https://i.pravatar.cc/150?u=3")
        )
    }

    fun addProperty(property: Property) {
        _properties.update { it + property }
    }
}
