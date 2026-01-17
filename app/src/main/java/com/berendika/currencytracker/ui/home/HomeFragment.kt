package com.berendika.currencytracker.ui.home

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.berendika.currencytracker.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.berendika.currencytracker.data.local.SettingsDataStore

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val tvResult = view.findViewById<TextView>(R.id.tvConvertedValue)
        val tvDefaultFiat = view.findViewById<TextView>(R.id.tvDefaultFiat)

        val settingsStore = SettingsDataStore(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsStore.defaultFiat.collectLatest { fiat ->
                    tvDefaultFiat.text = "Default FIAT: $fiat"
                }
            }
        }

        etAmount.addTextChangedListener {
            val amount = it?.toString()?.toDoubleOrNull() ?: 0.0
            viewModel.onAmountChanged(amount)
        }
    }
}
