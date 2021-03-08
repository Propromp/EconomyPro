package net.propromp.economypro.enterprise

import com.github.sanctum.economy.construct.currency.normal.EconomyCurrency
import java.util.*
import kotlin.math.sin

class EnterpriceCurrency(val plural:String,val singular:String):EconomyCurrency {
    override fun majorPlural(): String {
        return plural
    }

    override fun majorSingular(): String {
        return singular
    }

    override fun minorPlural(): String {
        return plural
    }

    override fun minorSingular(): String {
        return singular
    }

    override fun getLocale(): Locale {
        return Locale.ENGLISH
    }

    override fun getWorld(): String {
        return "world"
    }
}