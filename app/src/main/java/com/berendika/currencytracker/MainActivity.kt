package com.berendika.currencytracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.berendika.currencytracker.databinding.ActivityMainBinding
import com.berendika.currencytracker.ui.favorites.FavoritesFragment
import com.berendika.currencytracker.ui.home.HomeFragment
import com.berendika.currencytracker.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.setOnItemSelectedListener { item ->
            if (item.itemId == binding.bottomNav.selectedItemId) return@setOnItemSelectedListener true
            when (item.itemId) {
                R.id.nav_home -> {
                    openHome()
                    true
                }
                R.id.nav_favorites -> {
                    openFavorites()
                    true
                }
                R.id.nav_settings -> {
                    openSettings()
                    true
                }
                else -> false
            }
        }
        binding.bottomNav.setOnItemReselectedListener { /* no-op to avoid reselect loops */ }

        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.nav_home
            openHome()
        }
    }

    private fun openHome() {
        val current = supportFragmentManager.findFragmentById(R.id.container)
        if (current is HomeFragment) {
            return
        }
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.container, HomeFragment(), TAG_HOME)
            .commit()
    }

    private fun openFavorites() {
        val current = supportFragmentManager.findFragmentById(R.id.container)
        if (current is FavoritesFragment) {
            return
        }
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.container, FavoritesFragment(), TAG_FAVORITES)
            .commit()
    }

    private fun openSettings() {
        val current = supportFragmentManager.findFragmentById(R.id.container)
        if (current is SettingsFragment) {
            return
        }
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.container, SettingsFragment(), TAG_SETTINGS)
            .commit()
    }

    private companion object {
        private const val TAG_HOME = "home"
        private const val TAG_FAVORITES = "favorites"
        private const val TAG_SETTINGS = "settings"
    }
}
