package net.propromp.economypro.api

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

open class NormalBankAccount(override var name: String, owner: OfflinePlayer) : BankAccount {
    val ownerUUID = owner.uniqueId
    var members = mutableListOf<UUID>()
    override var balance = 0.0
    private var invitedPlayers = mutableListOf<OfflinePlayer>()
    override fun has(amount: Double): Boolean {
        return balance >= amount
    }

    fun isOwner(player: OfflinePlayer): Boolean {
        return ownerUUID == player.uniqueId
    }

    fun isMember(player: OfflinePlayer): Boolean {
        return members.contains(player.uniqueId)
    }

    fun isMemberOrOwner(player: OfflinePlayer): Boolean {
        return isOwner(player) || isMember(player)
    }

    override fun canSelect(player: OfflinePlayer):Boolean {
        return isMemberOrOwner(player)
    }

    fun addInvited(player: OfflinePlayer) {
        if (!isInvited(player)) {
            invitedPlayers.add(player)
        }
    }

    fun isInvited(player: OfflinePlayer): Boolean {
        return invitedPlayers.contains(player)
    }

}