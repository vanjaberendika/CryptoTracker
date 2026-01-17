package com.berendika.currencytracker.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Button
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
import com.berendika.currencytracker.data.local.FavoritesDataStore
import com.berendika.currencytracker.data.repository.SupportedAssets
import com.berendika.currencytracker.ui.common.CurrencyPickerBottomSheet

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fiat
        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val tvResult = view.findViewById<TextView>(R.id.tvConvertedValue)
        val tvDefaultFiat = view.findViewById<TextView>(R.id.tvDefaultFiat)

        // Favorite
        val favoritesStore = FavoritesDataStore(requireContext())
        val btnFav = view.findViewById<Button>(R.id.btnAddToFavorites)
        val tvFromCurrency = view.findViewById<TextView>(R.id.tvFromCurrency)

        val cryptoSymbols = SupportedAssets.cryptos.map { it.symbol }
        if (tvFromCurrency.text.isNullOrBlank()) {
            tvFromCurrency.text = cryptoSymbols.first()
        }

        tvFromCurrency.setOnClickListener {
            CurrencyPickerBottomSheet(
                title = "Select crypto",
                items = cryptoSymbols
            ) { selected ->
                tvFromCurrency.text = selected
            }.show(parentFragmentManager, "crypto_picker")
        }

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

        // Update button text based on current favorites + selected symbol
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoritesStore.favorites.collectLatest { favs ->
                    val symbol = tvFromCurrency.text.toString()
                    btnFav.text = if (favs.contains(symbol)) "Remove from Favorites" else "Add to Favorites"
                }
            }
        }
        // Toggle favorite on click
        btnFav.setOnClickListener {
            val symbol = tvFromCurrency.text.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                favoritesStore.toggle(symbol)
            }
        }
    }
}
