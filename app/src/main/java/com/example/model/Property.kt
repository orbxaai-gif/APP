package com.example.model

data class Property(
    val id: String,
    // 1. Transaction Type
    val transactionType: TransactionType,
    // 2. Property Type
    val propertyType: PropertyType,
    
    // 3. Address and Location
    val wilaya: String,
    val baladiya: String,
    val neighborhood: String,
    val detailedAddress: String,
    
    // 4. Property Details
    val area: Double, // in m²
    val rooms: Int,
    val bathrooms: Int,
    val numberOfFloors: Int,
    val floorNumber: Int,
    val propertyCondition: String,
    val yearBuilt: Int,
    
    // 5. Price
    val price: Double,
    val isNegotiable: Boolean,
    val paymentMethod: PaymentMethod,
    
    // 6. Description
    val description: String,
    
    // 7. Media
    val images: List<String>,
    val videoUrl: String,
    
    // 8. Additional Features
    val hasParking: Boolean,
    val hasElevator: Boolean,
    val hasBalcony: Boolean,
    val hasGarden: Boolean,
    val hasPool: Boolean,
    val hasHeating: Boolean,
    val hasSecurityCameras: Boolean,
    val isFurnished: Boolean,
    val hasElectricity380V: Boolean,
    val hasTwoFacades: Boolean,
    
    // 9. Contact Info
    val ownerName: String,
    val ownerPhone: String,
    val ownerWhatsApp: String,
    val ownerEmail: String,
    
    // Legacy fields mapped for compilation if any
    val title: String = "$propertyType in $baladiya",
    val location: String = "$wilaya, $baladiya",
    val status: PropertyStatus = if (transactionType == TransactionType.FOR_SALE) PropertyStatus.FOR_SALE else PropertyStatus.FOR_RENT
)

enum class TransactionType(val labelArabic: String) {
    FOR_SALE("بيع"),
    FOR_RENT("كراء"),
    VACATION_RENT("كراء للعطل"),
    LOOKING_TO_BUY("بحث عن شراء"),
    LOOKING_TO_RENT("بحث عن كراء")
}

enum class PropertyType(val labelArabic: String) { 
    APARTMENT("شقة"), 
    VILLA("فيلا"), 
    SHOP("محل"), 
    OFFICE("مكتب"), 
    LAND("أرض"), 
    AGRICULTURAL_LAND("أرض فلاحية"), 
    STUDIO("ستوديو"), 
    BUILDING("عمارة") 
}

enum class PaymentMethod(val labelArabic: String) {
    CASH("نقدًا"),
    INSTALLMENTS("أقساط"),
    MONTHLY("شهري"),
    YEARLY("سنوي")
}

// Deprecated, keeping minimal for compilation of old files
enum class PropertyStatus { FOR_SALE, FOR_RENT }

fun PropertyType.getLabel(): String = name.lowercase().replaceFirstChar { it.uppercase() }
fun PropertyStatus.getLabel(): String = this.name.replace("_", " ").lowercase().split(" ").joinToString(" ") { it.replaceFirstChar(Char::uppercase) }

data class Client(
    val id: String,
    val name: String,
    val phone: String,
    val interestedIn: String,
    val status: ClientStatus,
    val avatarUrl: String
)
enum class ClientStatus { ACTIVE, POTENTIAL, CLOSED }

data class KPI(
    val label: String,
    val value: String,
    val change: String,
    val isPositive: Boolean
)

