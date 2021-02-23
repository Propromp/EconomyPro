package net.propromp.economypro.api

import org.bukkit.OfflinePlayer
import org.bukkit.World

open class NormalBankAccount(override var name: String, var owner: OfflinePlayer) :BankAccount{
    var members = mutableListOf<OfflinePlayer>()
    override var balance = 0.0
    var invitedPlayers = mutableListOf<OfflinePlayer>()
    override fun has(amount: Double): Boolean {
        return balance>=amount
    }

    fun isOwner(player: OfflinePlayer):Boolean{
        return owner==player
    }
    fun isMember(player: OfflinePlayer):Boolean{
        return members.contains(player)
    }
    fun isMemberOrOwner(player: OfflinePlayer):Boolean{
        return isOwner(player)||isMember(player)
    }

    fun addInvited(player: OfflinePlayer){
        if(!isInvited(player)){
            invitedPlayers.add(player)
        }
    }
    fun isInvited(player: OfflinePlayer):Boolean{
        return invitedPlayers.contains(player)
    }

}