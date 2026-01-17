package com.berendika.currencytracker.data.model

enum class AssetType { CRYPTO, FIAT }

data class Asset(
    val symbol: String,
    val name: String,
    val type: AssetType
)