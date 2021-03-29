package net.propromp.economypro.api

import net.propromp.economypro.api.event.BankDepositEvent
import net.propromp.economypro.api.event.BankWithdrawEvent
import org.bukkit.Bukkit

interface BankAccount {
    var balance:Double
    val name:String
    fun deposit(amount:Double){
        val event = BankDepositEvent(this,amount)
        Bukkit.getPluginManager().callEvent(event)
        if(event.isCancelled)
            balance+=amount
    }
    fun withdraw(amount:Double){
        val event = BankWithdrawEvent(this,amount)
        Bukkit.getPluginManager().callEvent(event)
        if(event.isCancelled)
            balance-=amount
    }
    fun has(amount:Double):Boolean
}