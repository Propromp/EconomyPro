package net.propromp.economypro.api

import net.propromp.economypro.api.event.CreateBankAccountEvent
import net.propromp.economypro.api.event.DeleteBankAccountEvent
import net.propromp.economypro.api.event.SelectBankAccountEvent
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ProEconomy(val plural: String, val singular: String) {
    internal var selectedAccounts = HashMap<UUID, BankAccount>()
    internal var accounts = ArrayList<BankAccount>()

    /**
     * get the default bank of the player
     * You have to check if the bank exists by using hasDefaultAccount
     * @param player
     * @return bankAccount of the player
     */
    fun getDefaultAccount(player: OfflinePlayer): PlayerBankAccount? {
        accounts.forEach {
            if (it is PlayerBankAccount) {
                if (it.uuid == player.uniqueId) {
                    return it
                }
            }
        }
        return null
    }

    /**
     * check if the player has a default bank
     * @param player
     * @return boolean
     */
    fun hasDefaultAccount(player: OfflinePlayer): Boolean {
        return getDefaultAccount(player) != null
    }

    /**
     * create the default bank of the player
     * @param player
     * @return the bank account made
     */
    fun createDefaultAccount(player: OfflinePlayer): PlayerBankAccount? {
        if (hasDefaultAccount(player)) {
            return null
        } else {
            PlayerBankAccount(player).let {
                Bukkit.getPluginManager().callEvent(CreateBankAccountEvent(it))
                accounts.add(it)
                selectedAccounts[player.uniqueId] = it
                return it
            }
        }
    }

    /**
     * get a bank from string
     * @param name name of the bank
     * @return bank account
     */
    fun getNormalAccount(name: String): NormalBankAccount? {
        accounts.forEach {
            if (it is NormalBankAccount) {
                if (it.name.equals(name,true)) {
                    return it
                }
            }
        }
        return null
    }/**
     * get a bank from string
     * @param name name of the bank
     * @return bank account
     */
    fun getAccount(name: String): BankAccount? {
        accounts.forEach {
            if (it.name.equals(name,true)) {
                return it
            }
        }
        return null
    }

    /**
     * check if the bank with the name exists.
     * @param name the name of the bank
     * @return if the bank exists
     */
    fun hasAccount(name: String): Boolean {
        return getAccount(name) != null
    }

    /**
     * create the bank with the name
     * @param name name of the bank
     * @param owner owner of the bank
     * @return bankAccount made
     */
    fun createAccount(name: String, owner: OfflinePlayer): NormalBankAccount? {
        if (hasAccount(name)) {
            return null
        } else {
            NormalBankAccount(name, owner).let {
                Bukkit.getPluginManager().callEvent(CreateBankAccountEvent(it))
                accounts.add(it)
                return it
            }
        }
    }

    /**
     * delete the bank
     * @param name the name of the bank
     * @return Was it deleted successfully?
     */
    fun deleteAccount(name:String):Boolean{
        getAccount(name)?.let{
            Bukkit.getPluginManager().callEvent(DeleteBankAccountEvent(it))
            accounts.remove(it)
            return true
        }
        return false
    }
    fun deleteAccount(account: BankAccount){
        Bukkit.getPluginManager().callEvent(DeleteBankAccountEvent(account))
        accounts.remove(account)
    }

    fun selectAccount(player: OfflinePlayer,bankAccount: BankAccount){
        Bukkit.getPluginManager().callEvent(SelectBankAccountEvent(bankAccount,player))
        selectedAccounts[player.uniqueId]=bankAccount
    }
    fun getSelectedAccount(player: OfflinePlayer):BankAccount{
        return selectedAccounts[player.uniqueId]!!
    }
}