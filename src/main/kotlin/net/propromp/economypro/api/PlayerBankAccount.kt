package net.propromp.economypro.api

import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.entity.Player

class PlayerBankAccount(val player:OfflinePlayer) : BankAccount{
    override var name = "default"
    override var balance: Double
        get() {
            return if(player is Player){//online
                if(worldBalance.containsKey(player.world)) {
                    worldBalance[player.world]!!
                } else {
                    balance
                }
            } else {
                balance
            }
        }
        set(value) {
            if(player is Player){//online
                if(worldBalance.containsKey(player.world)) {
                    worldBalance[player.world] = value
                } else {
                    balance=value
                }
            } else {
                balance=value
            }
        }
    internal val worldBalance = HashMap<World,Double>()

    override fun deposit(amount: Double) {
        balance+=amount
    }

    override fun withdraw(amount: Double) {
        balance-=amount
    }

    override fun has(amount: Double):Boolean {
        return balance>=amount
    }
}