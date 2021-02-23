package net.propromp.economypro.api.event

import net.propromp.economypro.api.BankAccount
import org.bukkit.OfflinePlayer
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class SelectBankAccountEvent(val bank:BankAccount,val player:OfflinePlayer) :Event(){
    private val HANDLERS_LIST = HandlerList()
    override fun getHandlers(): HandlerList {
        return HANDLERS_LIST
    }
    fun getHandlerList(): HandlerList {
        return HANDLERS_LIST
    }
}