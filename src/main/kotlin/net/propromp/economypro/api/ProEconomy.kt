package net.propromp.economypro.api

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import net.propromp.economypro.Main
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

class ProEconomy(private val plural: String, private val singular: String) : Economy {
    internal var selectedBanks = HashMap<OfflinePlayer, Bank>()
    internal var banks = ArrayList<Bank>()//owner,bank

    /**
     * Checks if economy method is enabled.
     * @return Success or Failure
     */
    override fun isEnabled(): Boolean {
        return true
    }

    /**
     * Gets name of economy method
     * @return Name of Economy Method
     */
    override fun getName(): String {
        return "ProEconomy"
    }

    /**
     * Returns true if the given implementation supports banks.
     * @return true if the implementation supports banks
     */
    override fun hasBankSupport(): Boolean {
        return true
    }

    /**
     * Some economy plugins round off after a certain number of digits.
     * This function returns the number of digits the plugin keeps
     * or -1 if no rounding occurs.
     * @return number of digits after the decimal point kept
     */
    override fun fractionalDigits(): Int {
        return -1
    }

    /**
     * Format amount into a human readable String This provides translation into
     * economy specific formatting to improve consistency between plugins.
     *
     * @param amount to format
     * @return Human readable string describing amount
     */
    override fun format(amount: Double): String {
        return amount.toString()
    }

    /**
     * Returns the name of the currency in plural form.
     * If the economy being used does not support currency names then an empty string will be returned.
     *
     * @return name of the currency (plural)
     */
    override fun currencyNamePlural(): String {
        return plural
    }

    /**
     * Returns the name of the currency in singular form.
     * If the economy being used does not support currency names then an empty string will be returned.
     *
     * @return name of the currency (singular)
     */
    override fun currencyNameSingular(): String {
        return singular
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun hasAccount(playerName: String): Boolean {
        return null!!
    }

    /**
     * Checks if this player has an account selected on the server yet
     * This will always return true if the player has joined the server at least once
     * as all major economy plugins auto-generate a player account when the player joins the server
     *
     * @param player to check
     * @return if the player has an account
     */
    override fun hasAccount(player: OfflinePlayer): Boolean {
        selectedBanks[player]?.let {
            return true
        }
        banks.forEach {
            if (it.world == null && it.name == "default") {
                selectedBanks[player] = it
                return true
            }
        }
        return false
    }

    /**
     * get the bank linked to the player
     * If the banks doesn't exists,it will return null.
     *
     * @param player
     * @return player's account
     */
    fun getAccount(player: OfflinePlayer): Bank {
        return selectedBanks[player]!!
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun hasAccount(playerName: String?, worldName: String?): Boolean {
        return null!!
    }

    /**
     * Checks if this player has an account on the server yet on the given world
     * This will always return true if the player has joined the server at least once
     * as all major economy plugins auto-generate a player account when the player joins the server
     *
     * @param player to check in the world
     * @param worldName world-name
     * @return if the player has an account
     */
    override fun hasAccount(player: OfflinePlayer, worldName: String): Boolean {
        banks.filterNotNull().forEach {
            if (it.world == Bukkit.getWorld(worldName) && it.name == "default") {
                return true
            }
        }
        return false
    }

    /**
     * get the bank linked to the world
     * If the banks doesn't exists,it will return null.
     *
     * @param player
     * @param worldName world-name
     * @return player's account
     */
    fun getAccount(player: OfflinePlayer, worldName: String): Bank? {
        banks.filterNotNull().forEach {
            if (it.owner == player && it.world == Bukkit.getWorld(worldName) && it.name == "default") {
                return it
            }
        }
        return null
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun getBalance(playerName: String?): Double {
        return null!!
    }

    /**
     * Gets balance of a player
     *
     * @param player of the player
     * @return Amount currently held in players account
     */
    override fun getBalance(player: OfflinePlayer): Double {
        if (hasAccount(player)) {
            return getAccount(player)!!.balance
        }
        return 0.0
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun getBalance(playerName: String, world: String): Double {
        return null!!
    }

    /**
     * Gets balance of a player on the specified world.
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     * @param player to check
     * @param world name of the world
     * @return Amount currently held in players account
     */
    override fun getBalance(player: OfflinePlayer, world: String): Double {
        if (hasAccount(player, world)) {
            return getAccount(player, world)!!.balance
        }
        return 0.0
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun has(playerName: String?, amount: Double): Boolean {
        return null!!
    }

    /**
     * Checks if the player account has the amount - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to check
     * @param amount to check for
     * @return True if **player** has **amount**, False else wise
     */
    override fun has(player: OfflinePlayer, amount: Double): Boolean {
        if (hasAccount(player)) {
            return getAccount(player)!!.balance >= amount
        }
        return false
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun has(playerName: String?, worldName: String?, amount: Double): Boolean {
        return null!!
    }

    /**
     * Checks if the player account has the amount in a given world - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     *
     * @param player to check
     * @param worldName to check with
     * @param amount to check for
     * @return True if **player** has **amount**, False else wise
     */
    override fun has(player: OfflinePlayer, worldName: String, amount: Double): Boolean {
        if (hasAccount(player, worldName)) {
            return getAccount(player, worldName)!!.balance >= amount
        }
        return false
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun withdrawPlayer(playerName: String, amount: Double): EconomyResponse {
        return null!!
    }

    /**
     * Withdraw an amount from a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to withdraw from
     * @param amount Amount to withdraw
     * @return Detailed response of transaction
     */
    override fun withdrawPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        return if (hasAccount(player)) {
            getAccount(player)!!.balance -= amount
            EconomyResponse(amount, getAccount(player)!!.balance, EconomyResponse.ResponseType.SUCCESS, "OK")
        } else {
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account does not exist")
        }
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun withdrawPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return null!!
    }

    /**
     * Withdraw an amount from a player on a given world - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     * @param player to withdraw from
     * @param worldName - name of the world
     * @param amount Amount to withdraw
     * @return Detailed response of transaction
     */
    override fun withdrawPlayer(player: OfflinePlayer, worldName: String, amount: Double): EconomyResponse {
        return if (hasAccount(player, worldName)) {
            getAccount(player, worldName)!!.balance -= amount
            EconomyResponse(amount, getAccount(player, worldName)!!.balance, EconomyResponse.ResponseType.SUCCESS, "OK")
        } else {
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account does not exist")
        }
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun depositPlayer(playerName: String, amount: Double): EconomyResponse {
        return null!!
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param player to deposit to
     * @param amount Amount to deposit
     * @return Detailed response of transaction
     */
    override fun depositPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        return if (hasAccount(player)) {
            getAccount(player)!!.balance += amount
            EconomyResponse(amount, getAccount(player)!!.balance, EconomyResponse.ResponseType.SUCCESS, "OK")
        } else {
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account does not exist")
        }
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun depositPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return null!!
    }

    /**
     * Deposit an amount to a player - DO NOT USE NEGATIVE AMOUNTS
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this the global balance will be returned.
     *
     * @param player to deposit to
     * @param worldName name of the world
     * @param amount Amount to deposit
     * @return Detailed response of transaction
     */
    override fun depositPlayer(player: OfflinePlayer, worldName: String, amount: Double): EconomyResponse {
        return if (hasAccount(player, worldName)) {
            getAccount(player, worldName)!!.balance += amount
            EconomyResponse(amount, getAccount(player, worldName)!!.balance, EconomyResponse.ResponseType.SUCCESS, "OK")
        } else {
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account does not exist")
        }
    }

    /**
     * this method is deprecated
     * Do not use this method
     */
    override fun createBank(name: String?, player: String?): EconomyResponse {
        return null!!
    }

    /**
     * Creates a bank account with the specified name and the player as the owner
     * @param name of account
     * @param player the account should be linked to
     * @return EconomyResponse Object
     */
    override fun createBank(name: String, player: OfflinePlayer): EconomyResponse {
        if (hasBank(name))
            return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account already exists")

        banks.add(Bank(name, player))
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "ok")
    }

    fun getBank(name: String): Bank? {
        banks.filterNotNull().forEach {
            if (it.name == name) {
                return it
            }
        }
        return null
    }

    fun hasBank(name: String): Boolean {
        banks.filterNotNull().forEach {
            if (it.name == name) {
                return true
            }
        }
        return false
    }

    /**
     * Deletes a bank account with the specified name.
     * @param name of the back to delete
     * @return if the operation completed successfully
     */
    override fun deleteBank(name: String): EconomyResponse {
        banks.filterNotNull().forEach {
            if (it.name == name) {
                banks.remove(it)
                if (selectedBanks.containsValue(it)) {
                    selectedBanks.remove(it.owner)
                }
                return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "ok")
            }
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account does not exist")
    }

    /**
     * Returns the amount the bank has
     * @param name of the account
     * @return EconomyResponse Object
     */
    override fun bankBalance(name: String?): EconomyResponse {
        banks.filterNotNull().forEach {
            if (it.name == name) {
                return EconomyResponse(0.0, it.balance, EconomyResponse.ResponseType.SUCCESS, "ok")
            }
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account doesn't exist")
    }

    /**
     * use bankHasBoolean instead
     */
    @Deprecated("deprecated", ReplaceWith("null!!"))
    override fun bankHas(name: String, amount: Double): EconomyResponse {
        return null!!
    }

    fun bankHasBoolean(name: String, amount: Double): Boolean {
        return if (hasBank(name)) {
            getBank(name)!!.balance >= amount
        } else {
            false
        }
    }

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to withdraw
     * @return EconomyResponse Object
     */
    override fun bankWithdraw(name: String, amount: Double): EconomyResponse {
        return if (hasBank(name)) {
            getBank(name)!!.balance -= amount
            EconomyResponse(amount, getBank(name)!!.balance, EconomyResponse.ResponseType.SUCCESS, "ok")
        } else {
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account does not exist")
        }
    }

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to deposit
     * @return EconomyResponse Object
     */
    override fun bankDeposit(name: String, amount: Double): EconomyResponse {
        return if (hasBank(name)) {
            getBank(name)!!.balance += amount
            EconomyResponse(amount, getBank(name)!!.balance, EconomyResponse.ResponseType.SUCCESS, "ok")
        } else {
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "account does not exist")
        }
    }

    /**
     * use isBankOwnerBoolean instead
     */
    @Deprecated("deprecated", ReplaceWith("null!!"))
    override fun isBankOwner(name: String, playerName: String): EconomyResponse {
        return null!!
    }

    /**
     * use isBankOwnerBoolean instead
     */
    @Deprecated("deprecated", ReplaceWith("null!!"))
    override fun isBankOwner(name: String?, player: OfflinePlayer?): EconomyResponse {
        return null!!
    }

    /**
     * use isBankOwnerBoolean instead
     */
    @Deprecated("deprecated", ReplaceWith("null!!"))
    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        return null!!
    }

    /**
     * use isBankOwnerBoolean instead
     */
    @Deprecated("deprecated", ReplaceWith("null!!"))
    override fun isBankMember(name: String?, player: OfflinePlayer?): EconomyResponse {
        return null!!
    }

    fun isBankOwnerBoolean(name: String, player: OfflinePlayer): Boolean {
        return if (hasBank(name)) {
            getBank(name)!!.owner == player
        } else {
            false
        }
    }

    fun isBankMemberBoolean(name: String, player: OfflinePlayer): Boolean {
        return if (hasBank(name)) {
            getBank(name)!!.members.contains(player)
        } else {
            false
        }
    }

    /**
     * Gets the list of banks
     * @return the List of Banks
     */
    override fun getBanks(): MutableList<String> {
        var res = mutableListOf<String>()
        banks.forEach { res.add(it.name) }
        return res
    }

    override fun createPlayerAccount(playerName: String?): Boolean {
        return null!!
    }

    /**
     * Attempts to create a player account for the given player
     * @param player OfflinePlayer
     * @return if the account creation was successful
     */
    override fun createPlayerAccount(player: OfflinePlayer): Boolean {
        if (hasAccount(player)) {
            return false
        } else {
            var bank = Bank("default", player)
            banks.add(bank)
            selectedBanks[player] = bank
        }
        return false
    }

    override fun createPlayerAccount(playerName: String?, worldName: String?): Boolean {
        return null!!
    }

    /**
     * Attempts to create a player account for the given player on the specified world
     * IMPLEMENTATION SPECIFIC - if an economy plugin does not support this then false will always be returned.
     * @param player OfflinePlayer
     * @param worldName String name of the world
     * @return if the account creation was successful
     */
    override fun createPlayerAccount(player: OfflinePlayer, worldName: String): Boolean {
        return if (hasAccount(player, worldName)) {
            false
        } else {
            Bukkit.getWorld(worldName)?.let {
                var bank = Bank("default", player, Bukkit.getWorld(worldName)!!)
                banks.add(bank)
                selectedBanks[player] = bank
            }
            true
        }
    }
}