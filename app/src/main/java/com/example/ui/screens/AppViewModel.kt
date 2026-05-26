package com.example.ui.screens

import androidx.lifecycle.ViewModel
import com.example.data.PropertyRepository
import com.example.model.*
import kotlinx.coroutines.flow.*

class AppViewModel : ViewModel() {
    val repository = PropertyRepository()
    
    val properties = repository.properties
    val clients = repository.clients

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedStatus = MutableStateFlow<PropertyStatus?>(null)
    val selectedStatus = _selectedStatus.asStateFlow()

    val filteredProperties = combine(properties, _searchQuery, _selectedStatus) { list, query, status ->
        list.filter {
            (status == null || it.status == status) &&
            (query.isEmpty() || it.title.contains(query, ignoreCase = true) || it.location.contains(query, ignoreCase = true))
        }
    }
    
    fun onSearch(query: String) { _searchQuery.value = query }
    fun onFilterStatus(status: PropertyStatus?) { _selectedStatus.value = status }
    fun addProperty(p: Property) = repository.addProperty(p)
}
