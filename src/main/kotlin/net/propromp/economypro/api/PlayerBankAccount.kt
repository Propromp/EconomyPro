package net.propromp.economypro.api

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.World

class PlayerBankAccount(player: OfflinePlayer) : BankAccount {
    val uuid = player.uniqueId
    override val name:String
        get(){
            Bukkit.getOfflinePlayer(uuid).name?.let{
                return it
            }
            return "offline"
        }
    override var balance: Double
        get() {
            return if (Bukkit.getOfflinePlayer(uuid).isOnline) {//online
                var onlinePlayer = Bukkit.getOfflinePlayer(uuid).player!!
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
            if (Bukkit.getOfflinePlayer(uuid).isOnline) {//online
                var onlinePlayer = Bukkit.getOfflinePlayer(uuid).player!!
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