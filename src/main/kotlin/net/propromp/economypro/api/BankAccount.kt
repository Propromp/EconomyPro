package net.propromp.economypro.api

import org.bukkit.OfflinePlayer

open interface BankAccount {
    val balance:Double
    var name:String
    fun deposit(amount:Double)
    fun withdraw(amount:Double)
    fun has(amount:Double):Boolean
}