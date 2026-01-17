package com.berendika.currencytracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.berendika.currencytracker.data.local.SettingsDataStore
import com.berendika.currencytracker.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsStore: SettingsDataStore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsStore = SettingsDataStore(requireContext())

        // Load saved value and reflect in UI
        viewLifecycleOwner.lifecycleScope.launch {
            settingsStore.defaultFiat.collectLatest { fiat ->
                when (fiat) {
                    "USD" -> binding.rbUSD.isChecked = true
                    "EUR" -> binding.rbEUR.isChecked = true
                    "BAM" -> binding.rbBAM.isChecked = true
                }
            }
        }

        // Save whenever user changes
        binding.rgFiat.setOnCheckedChangeListener { _, checkedId ->
            val selected = when (checkedId) {
                binding.rbUSD.id -> "USD"
                binding.rbEUR.id -> "EUR"
                binding.rbBAM.id -> "BAM"
                else -> "USD"
            }

            viewLifecycleOwner.lifecycleScope.launch {
                settingsStore.setDefaultFiat(selected)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}