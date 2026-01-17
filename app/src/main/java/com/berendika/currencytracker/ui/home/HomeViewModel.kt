package com.berendika.currencytracker.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _convertedValue = MutableStateFlow(0.0)
    val convertedValue: StateFlow<Double> = _convertedValue

    // Fake rate for now (BTC → USD)
    private val fakeRate = 42000.0

    fun onAmountChanged(amount: Double) {
        _convertedValue.value = amount * fakeRate
    }
}