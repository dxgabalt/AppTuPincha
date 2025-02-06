package com.dxgabalt.tupincha.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxgabalt.tupincha.data.SupabaseService
import com.dxgabalt.tupincha.model.FaqItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SoporteViewModel() : ViewModel() {
    private val supabaseService: SupabaseService = SupabaseService
    private val _faqs = MutableStateFlow<List<FaqItem>>(emptyList())
    val faqs: StateFlow<List<FaqItem>> = _faqs

    fun cargarFaqs() {
        viewModelScope.launch {
            val faqsObtenidas = supabaseService.obtenerFaqs()
            _faqs.value = faqsObtenidas
        }
    }
}
