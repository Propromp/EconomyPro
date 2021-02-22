package net.propromp.economypro.api

import org.bukkit.OfflinePlayer

class ProEconomy(val plural: String, val singular: String) {
    internal var selectedAccounts = HashMap<OfflinePlayer, BankAccount>()
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
                if (it.player == player) {
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
                accounts.add(it)
                selectedAccounts[player] = it
                return it
            }
        }
    }

    /**
     * get a bank from string
     * @param name name of the bank
     * @return bank account
     */
    fun getAccount(name: String): NormalBankAccount? {
        accounts.forEach {
            if (it is NormalBankAccount) {
                if (it.name == name) {
                    return it
                }
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
                accounts.add(it)
                return it
            }
        }
    }

    /**
     * delete the bank
     * @param the name of the bank
     * @return Was it deleted successfully?
     */
    fun deleteAccount(name:String):Boolean{
        getAccount(name)?.let{
            accounts.remove(it)
            return true
        }
        return false
    }

    fun selectAccount(player: OfflinePlayer,bankAccount: BankAccount){
        selectedAccounts[player]=bankAccount
    }
    fun getSelectedAccount(player: OfflinePlayer):BankAccount{
        return selectedAccounts[player]!!
    }
}