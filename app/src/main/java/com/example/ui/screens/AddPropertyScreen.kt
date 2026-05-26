package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import android.net.Uri
import coil.compose.AsyncImage
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.model.*
import com.example.ui.theme.*
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPropertyScreen(navController: NavController, viewModel: AppViewModel) {
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10)
    ) { uris ->
        if (uris.isNotEmpty()) {
            selectedImageUris = uris
        }
    }

    // 1. Transaction Type
    var transactionType by remember { mutableStateOf(TransactionType.FOR_SALE) }
    
    // 2. Property Type
    var propertyType by remember { mutableStateOf(PropertyType.APARTMENT) }
    var expandedType by remember { mutableStateOf(false) }

    // 3. Location
    var wilaya by remember { mutableStateOf("") }
    var baladiya by remember { mutableStateOf("") }
    var neighborhood by remember { mutableStateOf("") }
    var detailedAddress by remember { mutableStateOf("") }
    
    // 4. Details
    var area by remember { mutableStateOf("") }
    var rooms by remember { mutableStateOf("") }
    var bathrooms by remember { mutableStateOf("") }
    var numberOfFloors by remember { mutableStateOf("") }
    var floorNumber by remember { mutableStateOf("") }
    var propertyCondition by remember { mutableStateOf("") }
    var yearBuilt by remember { mutableStateOf("") }

    // 5. Price
    var price by remember { mutableStateOf("") }
    var isNegotiable by remember { mutableStateOf(false) }
    var paymentMethod by remember { mutableStateOf(PaymentMethod.CASH) }
    var expandedPayment by remember { mutableStateOf(false) }

    // 6. Description
    var description by remember { mutableStateOf("") }

    // 8. Extra Features
    var hasParking by remember { mutableStateOf(false) }
    var hasElevator by remember { mutableStateOf(false) }
    var hasBalcony by remember { mutableStateOf(false) }
    var hasGarden by remember { mutableStateOf(false) }
    var hasPool by remember { mutableStateOf(false) }
    var hasHeating by remember { mutableStateOf(false) }
    var hasSecurityCameras by remember { mutableStateOf(false) }
    var isFurnished by remember { mutableStateOf(false) }
    var hasElectricity380V by remember { mutableStateOf(false) }
    var hasTwoFacades by remember { mutableStateOf(false) }

    // 9. Contact Info
    var ownerName by remember { mutableStateOf("") }
    var ownerPhone by remember { mutableStateOf("") }
    var ownerWhatsApp by remember { mutableStateOf("") }
    var ownerEmail by remember { mutableStateOf("") }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = PremiumGold,
        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
        focusedTextColor = TextWhite,
        unfocusedTextColor = TextWhite,
        focusedLabelColor = PremiumGold,
        cursorColor = PremiumGold
    )
    
    val isLooking = transactionType == TransactionType.LOOKING_TO_BUY || transactionType == TransactionType.LOOKING_TO_RENT

    Scaffold(
        containerColor = DeepBlack,
        topBar = {
            TopAppBar(
                title = { Text("إضافة عقار جديد", color = TextWhite, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "رجوع", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DeepBlack)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // Section 1: Transaction Type
            SectionTitle("1. نوع المعاملة")
            @OptIn(ExperimentalLayoutApi::class)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransactionType.values().forEach { type ->
                    ChoiceChip(
                        text = type.labelArabic,
                        selected = transactionType == type,
                        onClick = { transactionType = type }
                    )
                }
            }

            // Section 2: Property Type
            SectionTitle("2. نوع العقار")
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = propertyType.labelArabic, onValueChange = {}, readOnly = true,
                    label = { Text("اختر نوع العقار") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    shape = RoundedCornerShape(12.dp)
                )
                // Transparent overlay to catch clicks
                Surface(
                    modifier = Modifier.matchParentSize(),
                    color = Color.Transparent,
                    onClick = { expandedType = true }
                ) {}
                
                DropdownMenu(
                    expanded = expandedType,
                    onDismissRequest = { expandedType = false },
                    modifier = Modifier.background(DarkGray)
                ) {
                    PropertyType.values().forEach { t ->
                        DropdownMenuItem(
                            text = { Text(t.labelArabic, color = TextWhite) },
                            onClick = { propertyType = t; expandedType = false }
                        )
                    }
                }
            }

            // Section 3: Location
            SectionTitle("3. العنوان والموقع")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = wilaya, onValueChange = { wilaya = it }, label = { Text("الولاية") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = baladiya, onValueChange = { baladiya = it }, label = { Text("البلدية") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
            }
            if (!isLooking) {
                OutlinedTextField(value = neighborhood, onValueChange = { neighborhood = it }, label = { Text("الحي") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = detailedAddress, onValueChange = { detailedAddress = it }, label = { Text("العنوان التفصيلي") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
            }

            // Section 4: Details
            SectionTitle(if (isLooking) "4. المواصفات المطلوبة" else "4. معلومات العقار")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = area, onValueChange = { area = it }, label = { Text(if (isLooking) "المساحة المطلوبة (أكثر من)" else "المساحة m²") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = rooms, onValueChange = { rooms = it }, label = { Text("عدد الغرف") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = bathrooms, onValueChange = { bathrooms = it }, label = { Text("عدد الحمامات") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                if (!isLooking) {
                    OutlinedTextField(value = floorNumber, onValueChange = { floorNumber = it }, label = { Text("الطابق") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                }
            }
            if (!isLooking) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = propertyCondition, onValueChange = { propertyCondition = it }, label = { Text("حالة العقار (مثل: جديد، يحتاج ترميم)") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = yearBuilt, onValueChange = { yearBuilt = it }, label = { Text("سنة البناء") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                }
            }

            // Section 5: Price
            SectionTitle(if (isLooking) "5. الميزانية وطريقة الدفع" else "5. السعر")
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text(if (isLooking) "أقصى ميزانية (د.ج)" else "السعر (د.ج)") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                if (!isLooking) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = isNegotiable, onCheckedChange = { isNegotiable = it }, colors = CheckboxDefaults.colors(checkedColor = PremiumGold))
                        Text("قابل للتفاوض", color = TextWhite, fontSize = 12.sp)
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = paymentMethod.labelArabic, onValueChange = {}, readOnly = true,
                    label = { Text("طريقة الدفع") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors, shape = RoundedCornerShape(12.dp)
                )
                Surface(
                    modifier = Modifier.matchParentSize(),
                    color = Color.Transparent,
                    onClick = { expandedPayment = true }
                ) {}
                DropdownMenu(
                    expanded = expandedPayment, onDismissRequest = { expandedPayment = false },
                    modifier = Modifier.background(DarkGray)
                ) {
                    PaymentMethod.values().forEach { p ->
                        DropdownMenuItem(
                            text = { Text(p.labelArabic, color = TextWhite) }, 
                            onClick = { paymentMethod = p; expandedPayment = false }
                        )
                    }
                }
            }

            // Section 6: Description
            SectionTitle(if (isLooking) "6. تفاصيل إضافية للطلب" else "6. الوصف")
            OutlinedTextField(
                value = description, onValueChange = { description = it },
                label = { Text(if (isLooking) "اذكر أي مواصفات أخرى تبحث عنها" else "وصف كامل للعقار") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                colors = textFieldColors,
                shape = RoundedCornerShape(12.dp),
                maxLines = 5
            )

            // Section 7: Media (Only if not looking)
            AnimatedVisibility(visible = !isLooking) {
                Column {
                    SectionTitle("7. الصور والفيديو")
                    Button(
                        onClick = {
                            multiplePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp).padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGray),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Images", tint = PremiumGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("إضافة صور / فيديو", color = TextWhite)
                    }

                    if (selectedImageUris.isNotEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            items(selectedImageUris) { uri ->
                                AsyncImage(
                                    model = uri,
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }

            // Section 8: Features
            SectionTitle(if (isLooking) "8. مميزات ضرورية" else "8. المميزات الإضافية")
            Box(modifier = Modifier.fillMaxWidth().background(DarkGray, RoundedCornerShape(12.dp)).padding(8.dp)) {
                        Column {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { 
                                FeatureCheckbox("موقف سيارات", hasParking) { hasParking = it }
                                FeatureCheckbox("مصعد", hasElevator) { hasElevator = it }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { 
                                FeatureCheckbox("شرفة", hasBalcony) { hasBalcony = it }
                                FeatureCheckbox("حديقة", hasGarden) { hasGarden = it }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { 
                                FeatureCheckbox("مسبح", hasPool) { hasPool = it }
                                FeatureCheckbox("تدفئة", hasHeating) { hasHeating = it }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { 
                                FeatureCheckbox("كاميرات", hasSecurityCameras) { hasSecurityCameras = it }
                                FeatureCheckbox("مفروش", isFurnished) { isFurnished = it }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { 
                                FeatureCheckbox("كهرباء 380V", hasElectricity380V) { hasElectricity380V = it }
                                FeatureCheckbox("واجهتين", hasTwoFacades) { hasTwoFacades = it }
                            }
                        }
                    }
            
            // Section 9: Contact Info
            SectionTitle("9. معلومات التواصل")
            OutlinedTextField(value = ownerName, onValueChange = { ownerName = it }, label = { Text("الاسم الكامل") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = ownerPhone, onValueChange = { ownerPhone = it }, label = { Text("رقم الهاتف") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
                OutlinedTextField(value = ownerWhatsApp, onValueChange = { ownerWhatsApp = it }, label = { Text("واتساب") }, modifier = Modifier.weight(1f), colors = textFieldColors, shape = RoundedCornerShape(12.dp))
            }
            OutlinedTextField(value = ownerEmail, onValueChange = { ownerEmail = it }, label = { Text("البريد الإلكتروني") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors, shape = RoundedCornerShape(12.dp))

            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    viewModel.addProperty(
                        Property(
                            id = UUID.randomUUID().toString(),
                            transactionType = transactionType,
                            propertyType = propertyType,
                            wilaya = wilaya,
                            baladiya = baladiya,
                            neighborhood = neighborhood,
                            detailedAddress = detailedAddress,
                            area = area.toDoubleOrNull() ?: 0.0,
                            rooms = rooms.toIntOrNull() ?: 0,
                            bathrooms = bathrooms.toIntOrNull() ?: 0,
                            numberOfFloors = numberOfFloors.toIntOrNull() ?: 0,
                            floorNumber = floorNumber.toIntOrNull() ?: 0,
                            propertyCondition = propertyCondition,
                            yearBuilt = yearBuilt.toIntOrNull() ?: 0,
                            price = price.toDoubleOrNull() ?: 0.0,
                            isNegotiable = isNegotiable,
                            paymentMethod = paymentMethod,
                            description = description,
                            images = if (selectedImageUris.isNotEmpty()) selectedImageUris.map { it.toString() } else listOf("https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80"),
                            videoUrl = "",
                            hasParking = hasParking,
                            hasElevator = hasElevator,
                            hasBalcony = hasBalcony,
                            hasGarden = hasGarden,
                            hasPool = hasPool,
                            hasHeating = hasHeating,
                            hasSecurityCameras = hasSecurityCameras,
                            isFurnished = isFurnished,
                            hasElectricity380V = hasElectricity380V,
                            hasTwoFacades = hasTwoFacades,
                            ownerName = ownerName,
                            ownerPhone = ownerPhone,
                            ownerWhatsApp = ownerWhatsApp,
                            ownerEmail = ownerEmail
                        )
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PremiumGold, contentColor = DeepBlack),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Check, null)
                Spacer(Modifier.width(8.dp))
                Text(if (isLooking) "نشر طلب البحث" else "نشر العقار", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = PremiumGold,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun ChoiceChip(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(20.dp),
        color = if (selected) PremiumGold else DeepBlack,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (selected) PremiumGold else Color.White.copy(alpha = 0.2f))
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = text,
                color = if (selected) DeepBlack else TextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun RowScope.FeatureCheckbox(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = PremiumGold)
        )
        Text(label, color = TextWhite, fontSize = 12.sp)
    }
}
