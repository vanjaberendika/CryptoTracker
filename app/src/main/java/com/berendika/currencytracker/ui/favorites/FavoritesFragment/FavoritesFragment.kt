package com.berendika.currencytracker.ui.favorites

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.berendika.currencytracker.R
import com.berendika.currencytracker.data.local.FavoritesDataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lv = view.findViewById<ListView>(R.id.lvFavorites)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mutableListOf<String>())
        lv.adapter = adapter

        val favoritesStore = FavoritesDataStore(requireContext())

        // Show favorites
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoritesStore.favorites.collectLatest { favs ->
                    adapter.clear()
                    adapter.addAll(favs.sorted())
                    adapter.notifyDataSetChanged()
                }
            }
        }

        // Long press to remove
        lv.setOnItemLongClickListener { _, _, position, _ ->
            val symbol = adapter.getItem(position) ?: return@setOnItemLongClickListener true
            viewLifecycleOwner.lifecycleScope.launch {
                favoritesStore.remove(symbol)
            }
            true
        }
    }
}