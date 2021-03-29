package net.propromp.economypro.api

import org.bukkit.OfflinePlayer
import org.bukkit.World

class PlayerBankAccount(val player: OfflinePlayer) : BankAccount {
    override val name:String
        get(){
            player.name?.let{
                return it
            }
            return "offline"
        }
    override var balance: Double
        get() {
            return if (player.isOnline) {//online
                var onlinePlayer = player.player!!
                if (worldBalance.containsKey(onlinePlayer.world)) {
                    worldBalance[onlinePlayer.world]!!
                } else {
                    normalBalance
                }
            } else {
                normalBalance
            }
        }
        set(value) {
            if (player.isOnline) {//online
                var onlinePlayer = player.player!!
                if (worldBalance.containsKey(onlinePlayer.world)) {
                    worldBalance[onlinePlayer.world] = value
                } else {
                    normalBalance = value
                }
            } else {
                normalBalance = value
            }
        }
    internal val worldBalance = HashMap<World, Double>()
    internal var normalBalance = 0.0

    override fun has(amount: Double): Boolean {
        return balance >= amount
    }

    fun deposit(amount: Double, world: World) {
        worldBalance[world]?.let {
            worldBalance[world] = worldBalance[world]!!.plus(amount)
            return
        }
        worldBalance[world] = amount
    }

    fun withdraw(amount: Double, world: World) {
        worldBalance[world]?.let {
            worldBalance[world] = worldBalance[world]!!.minus(amount)
            return
        }
    }

    fun getBalance(world: World): Double {
        worldBalance[world]?.let {
            return it
        }
        return 0.0
    }

    fun has(amount: Double, world: World): Boolean {
        return getBalance(world) >= amount
    }
}