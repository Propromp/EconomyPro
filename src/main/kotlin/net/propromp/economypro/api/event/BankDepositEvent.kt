package net.propromp.economypro.api.event

import net.propromp.economypro.api.BankAccount
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class BankDepositEvent(val bank: BankAccount, val amount: Double) :Event(),Cancellable{
    private var cancelled:Boolean = false
    private val HANDLERS_LIST = HandlerList()
    override fun setCancelled(cancel: Boolean) {
        cancelled=cancel
    }
    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS_LIST
    }

    fun getHandlerList(): HandlerList {
        return HANDLERS_LIST
    }
}