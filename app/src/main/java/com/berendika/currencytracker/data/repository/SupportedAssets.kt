package com.berendika.currencytracker.data.repository

import com.berendika.currencytracker.data.model.Asset
import com.berendika.currencytracker.data.model.AssetType

object SupportedAssets {

    val cryptos: List<Asset> = listOf(
        Asset("BTC", "Bitcoin", AssetType.CRYPTO),
        Asset("ETH", "Ethereum", AssetType.CRYPTO),
        Asset("SOL", "Solana", AssetType.CRYPTO),
        Asset("BNB", "BNB", AssetType.CRYPTO),
        Asset("XRP", "XRP", AssetType.CRYPTO),
    )

    val fiats: List<Asset> = listOf(
        Asset("USD", "US Dollar", AssetType.FIAT),
        Asset("EUR", "Euro", AssetType.FIAT),
        Asset("BAM", "Bosnia Mark", AssetType.FIAT),
    )
}