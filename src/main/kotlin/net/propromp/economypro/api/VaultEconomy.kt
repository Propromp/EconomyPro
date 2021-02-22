package net.propromp.economypro.api

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer

class VaultEconomy(private val economy: ProEconomy) : Economy {
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
        return "EconomyPro"
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
        return economy.plural
    }

    /**
     * Returns the name of the currency in singular form.
     * If the economy being used does not support currency names then an empty string will be returned.
     *
     * @return name of the currency (singular)
     */
    override fun currencyNameSingular(): String {
        return economy.singular
    }

    override fun hasAccount(playerName: String?): Boolean {
        return null!!
    }

    /**
     * Checks if this player has an account on the server yet
     * This will always return true if the player has joined the server at least once
     * as all major economy plugins auto-generate a player account when the player joins the server
     *
     * @param player to check
     * @return if the player has an account
     */
    override fun hasAccount(player: OfflinePlayer): Boolean {
        return economy.hasDefaultAccount(player)
    }

    override fun hasAccount(playerName: String?, worldName: String?): Boolean {
        return null!!
    }

    /**
     * Checks if this player has an account on the server yet on the given world
     * This will always return true if the player has joined the server at least once
     * as all major economy plugins auto-generate a player account when the player joins the server
     *
     * @param player to check in the world
     * @param worldName world-specific account
     * @return if the player has an account
     */
    override fun hasAccount(player: OfflinePlayer, worldName: String): Boolean {
        return if (hasAccount(player)) {
            economy.getDefaultAccount(player)!!.worldBalance.containsKey(Bukkit.getWorld(worldName))
        } else {
            false
        }
    }

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
        return economy.getSelectedAccount(player).balance
    }

    override fun getBalance(playerName: String?, world: String?): Double {
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
        return economy.getDefaultAccount(player)!!.worldBalance[Bukkit.getWorld(world)] ?: 0.0
    }

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
        return economy.getSelectedAccount(player).has(amount)
    }

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
        economy.getDefaultAccount(player)?.let { it ->
            it.worldBalance[Bukkit.getWorld(worldName)]?.let {
                return it >= amount
            }
        }
        return false
    }

    override fun withdrawPlayer(playerName: String?, amount: Double): EconomyResponse {
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
        economy.getSelectedAccount(player).withdraw(amount)
        return EconomyResponse(
            amount,
            economy.getSelectedAccount(player).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "OK"
        )
    }

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
        economy.getDefaultAccount(player)?.let {
            it.worldBalance[Bukkit.getWorld(worldName)]?.run {
                this.minus(amount)
                return EconomyResponse(amount, this, EconomyResponse.ResponseType.SUCCESS, "OK")
            }
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "No default account found.")
    }

    override fun depositPlayer(playerName: String?, amount: Double): EconomyResponse {
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
        economy.getSelectedAccount(player).deposit(amount)
        return EconomyResponse(
            amount,
            economy.getSelectedAccount(player).balance,
            EconomyResponse.ResponseType.SUCCESS,
            "OK"
        )
    }

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
        economy.getDefaultAccount(player)?.let {
            it.worldBalance[Bukkit.getWorld(worldName)]?.run {
                this.plus(amount)
                return EconomyResponse(amount, this, EconomyResponse.ResponseType.SUCCESS, "OK")
            }
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "No default account found.")
    }

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
        economy.createAccount(name, player)?.let {
            return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "OK")
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The bank already exists")
    }

    /**
     * Deletes a bank account with the specified name.
     * @param name of the back to delete
     * @return if the operation completed successfully
     */
    override fun deleteBank(name: String): EconomyResponse {
        return if (economy.deleteAccount(name)) {
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "OK")
        } else {
            EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "Failed to delete the bank")
        }
    }

    /**
     * Returns the amount the bank has
     * @param name of the account
     * @return EconomyResponse Object
     */
    override fun bankBalance(name: String): EconomyResponse {
        economy.getAccount(name)?.let {
            return EconomyResponse(it.balance, it.balance, EconomyResponse.ResponseType.SUCCESS, "OK")
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The bank doesn't exist")
    }

    /**
     * Returns true or false whether the bank has the amount specified - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to check for
     * @return EconomyResponse Object
     */
    override fun bankHas(name: String, amount: Double): EconomyResponse {
        economy.getAccount(name)?.let {
            return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, it.has(amount).toString())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The bank doesn't exist")
    }

    /**
     * Withdraw an amount from a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to withdraw
     * @return EconomyResponse Object
     */
    override fun bankWithdraw(name: String, amount: Double): EconomyResponse {
        economy.getAccount(name)?.let {
            it.withdraw(amount)
            return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "OK")
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The bank doesn't exist")
    }

    /**
     * Deposit an amount into a bank account - DO NOT USE NEGATIVE AMOUNTS
     *
     * @param name of the account
     * @param amount to deposit
     * @return EconomyResponse Object
     */
    override fun bankDeposit(name: String, amount: Double): EconomyResponse {
        economy.getAccount(name)?.let {
            it.deposit(amount)
            return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "OK")
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The bank doesn't exist")
    }

    override fun isBankOwner(name: String, playerName: String): EconomyResponse {
        return null!!
    }

    /**
     * Check if a player is the owner of a bank account
     *
     * @param name of the account
     * @param player to check for ownership
     * @return EconomyResponse Object
     */
    override fun isBankOwner(name: String, player: OfflinePlayer): EconomyResponse {
        economy.getAccount(name)?.let {
            return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, (it.owner == player).toString())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The bank doesn't exist")
    }

    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        return null!!
    }

    /**
     * Check if the player is a member of the bank account
     *
     * @param name of the account
     * @param player to check membership
     * @return EconomyResponse Object
     */
    override fun isBankMember(name: String, player: OfflinePlayer): EconomyResponse {
        economy.getAccount(name)?.let {
            return EconomyResponse(
                0.0,
                0.0,
                EconomyResponse.ResponseType.SUCCESS,
                it.members.contains(player).toString()
            )
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The bank doesn't exist")
    }

    /**
     * Gets the list of banks
     * @return the List of Banks
     */
    override fun getBanks(): MutableList<String> {
        val res = mutableListOf<String>()
        economy.accounts.filterIsInstance<NormalBankAccount>().forEach { res.add(it.name) }
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
        economy.createDefaultAccount(player)?.let {
            return true
        }
        return false
    }

    override fun createPlayerAccount(playerName: String, worldName: String): Boolean {
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
        economy.getDefaultAccount(player)?.let {
            Bukkit.getWorld(worldName)?.run {
                it.worldBalance[this] = 0.0
                return true
            }
        }
        economy.createDefaultAccount(player)?.let {
            Bukkit.getWorld(worldName)?.run {
                it.worldBalance[this] = 0.0
                return true
            }
        }
        return false
    }
}