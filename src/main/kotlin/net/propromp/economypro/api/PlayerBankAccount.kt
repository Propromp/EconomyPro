package net.propromp.economypro.api

import org.bukkit.OfflinePlayer
import org.bukkit.World
import org.bukkit.entity.Player

class PlayerBankAccount(val player:OfflinePlayer) : BankAccount{
    override var name = "default"
    override var balance: Double
        get() {
            return if(player.isOnline){//online
                var onlinePlayer = player.player!!
                if(worldBalance.containsKey(onlinePlayer.world)) {
                    worldBalance[onlinePlayer.world]!!
                } else {
                    normalBalance
                }
            } else {
                normalBalance
            }
        }
        set(value) {
            if(player.isOnline){//online
                var onlinePlayer = player.player!!
                if(worldBalance.containsKey(onlinePlayer.world)) {
                    worldBalance[onlinePlayer.world] = value
                } else {
                    normalBalance=value
                }
            } else {
                normalBalance=value
            }
        }
    internal val worldBalance = HashMap<World,Double>()
    internal var normalBalance = 0.0

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