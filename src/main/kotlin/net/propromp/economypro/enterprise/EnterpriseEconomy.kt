package net.propromp.economypro.enterprise

import com.github.sanctum.economy.construct.EconomyAction
import com.github.sanctum.economy.construct.EconomyPriority
import com.github.sanctum.economy.construct.account.Account
import com.github.sanctum.economy.construct.account.Wallet
import com.github.sanctum.economy.construct.account.permissive.AccountType
import com.github.sanctum.economy.construct.currency.normal.EconomyCurrency
import com.github.sanctum.economy.construct.implement.AdvancedEconomy
import net.propromp.economypro.api.NormalBankAccount
import net.propromp.economypro.api.PlayerBankAccount
import net.propromp.economypro.api.ProEconomy
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.plugin.Plugin
import java.math.BigDecimal
import java.util.*

class EnterpriseEconomy(val javaPlugin: Plugin, val economy: ProEconomy) : AdvancedEconomy {
    val currency = EnterpriceCurrency(economy.plural, economy.singular)
    override fun getPlugin(): Plugin {
        return javaPlugin
    }

    override fun getVersion(): String {
        return plugin.description.version
    }

    override fun getCurrency(): EconomyCurrency {
        return currency
    }

    override fun getCurrency(world: String?): EconomyCurrency {
        return currency
    }

    override fun getPriority(): EconomyPriority {
        return EconomyPriority.HIGH
    }

    override fun format(amount: BigDecimal): String {
        return amount.toString() + currency.plural
    }

    override fun format(amount: BigDecimal, locale: Locale): String {
        return amount.toString() + currency.plural
    }

    override fun getMaxWalletSize(): BigDecimal {
        return BigDecimal(Double.MAX_VALUE)
    }

    override fun isMultiWorld(): Boolean {
        return true
    }

    override fun isMultiCurrency(): Boolean {
        return false
    }

    override fun hasMultiAccountSupport(): Boolean {
        return true
    }

    override fun hasWalletSizeLimit(): Boolean {
        return true
    }

    override fun getAccount(name: String): Account? {
        return economy.getAccount(name)?.let { EnterpriseAccount.get(it) }
    }

    override fun getAccount(name: String, type: AccountType): Account? {
        return getAccount(name)
    }

    override fun getAccount(accountId: String?, name: String): Account? {
        return getAccount(name)
    }

    override fun getAccount(player: OfflinePlayer, type: AccountType?): Account? {
        return getAccount(player)
    }

    override fun getAccount(player: OfflinePlayer): Account? {
        return economy.getDefaultAccount(player)?.let { EnterprisePlayerAccount.get(it) }
    }

    override fun getAccount(accountId: String?, player: OfflinePlayer): Account? {
        return getAccount(player)
    }

    override fun getAccount(uuid: UUID): Account? {
        return getAccount(Bukkit.getOfflinePlayer(uuid))
    }

    override fun getAccount(uuid: UUID, type: AccountType?): Account? {
        return getAccount(uuid)
    }

    override fun getAccount(accountId: String?, uuid: UUID): Account? {
        return getAccount(uuid)
    }

    override fun getWallet(name: String): Wallet? {
        return getAccount(name)?.let { EnterpriseWallet(it) }
    }

    override fun getWallet(player: OfflinePlayer): Wallet? {
        return getAccount(player)?.let { EnterpriseWallet(it) }
    }

    override fun getWallet(uuid: UUID): Wallet? {
        return getWallet(Bukkit.getOfflinePlayer(uuid))
    }

    override fun createAccount(type: AccountType?, name: String): EconomyAction {
        return EconomyAction(null,false,"Not implemented")
    }

    override fun createAccount(type: AccountType?, name: String, accountId: String?): EconomyAction {
        return createAccount(type, name)
    }

    override fun createAccount(type: AccountType?, name: String, startingAmount: BigDecimal): EconomyAction {
        var res = createAccount(type, name)
        if (res.isSuccess)
            economy.getAccount(name)!!.balance = startingAmount.toDouble()
        return res
    }

    override fun createAccount(type: AccountType?, name: String, accountId: String?, world: String?): EconomyAction {
        return createAccount(type, name)
    }

    override fun createAccount(
        type: AccountType?,
        name: String,
        accountId: String?,
        world: String?,
        startingAmount: BigDecimal,
    ): EconomyAction {
        return createAccount(type, name, startingAmount)
    }

    override fun createAccount(type: AccountType?, player: OfflinePlayer): EconomyAction {
        economy.createDefaultAccount(player)?.let {
            return EconomyAction(PlayerEconomyEntity(player), true, "Created ${player.name}'s default account")
        }
        return EconomyAction(PlayerEconomyEntity(player), false, "The account already exists")
    }

    override fun createAccount(type: AccountType?, player: OfflinePlayer, accountId: String): EconomyAction {
        economy.createAccount(accountId, player)?.let {
            return EconomyAction(PlayerEconomyEntity(player), true, "Created account")
        }
        return EconomyAction(PlayerEconomyEntity(player), false, "The account already exists")
    }

    override fun createAccount(type: AccountType?, player: OfflinePlayer, startingAmount: BigDecimal): EconomyAction {
        val res = createAccount(type, player)
        if (res.isSuccess) {
            economy.createDefaultAccount(player)!!.balance = startingAmount.toDouble()
        }
        return res
    }

    override fun createAccount(
        type: AccountType?,
        player: OfflinePlayer,
        accountId: String,
        world: String?,
    ): EconomyAction {
        return createAccount(type, player, accountId)
    }

    override fun createAccount(
        type: AccountType?,
        player: OfflinePlayer,
        accountId: String,
        world: String?,
        startingAmount: BigDecimal,
    ): EconomyAction {
        val res = createAccount(type, player, accountId)
        if (res.isSuccess) {
            economy.getAccount(accountId)!!.balance = startingAmount.toDouble()
        }
        return res
    }

    override fun createAccount(type: AccountType?, uuid: UUID): EconomyAction {
        return createAccount(type, Bukkit.getOfflinePlayer(uuid))
    }

    override fun createAccount(type: AccountType?, uuid: UUID, accountId: String): EconomyAction {
        return createAccount(type, Bukkit.getOfflinePlayer(uuid), accountId)
    }

    override fun createAccount(type: AccountType?, uuid: UUID, startingAmount: BigDecimal): EconomyAction {
        return createAccount(type, Bukkit.getOfflinePlayer(uuid), startingAmount)
    }

    override fun createAccount(type: AccountType?, uuid: UUID, accountId: String, world: String?): EconomyAction {
        return createAccount(type, Bukkit.getOfflinePlayer(uuid), accountId, world)
    }

    override fun createAccount(
        type: AccountType?,
        uuid: UUID,
        accountId: String,
        world: String?,
        startingAmount: BigDecimal,
    ): EconomyAction {
        return createAccount(type, Bukkit.getOfflinePlayer(uuid), accountId, world, startingAmount)
    }

    override fun deleteWalletAccount(wallet: Wallet): EconomyAction {
        return if (wallet is EnterpriseWallet) {
            if (wallet.account is PlayerBankAccount) {
                EconomyAction(wallet.holder, false, "You cannot delete player's account")
            } else if (wallet.account is NormalBankAccount) {
                economy.deleteAccount(wallet.account.name)
                EconomyAction(wallet.holder, true, "ok")
            } else {
                EconomyAction(null, false, "failure")
            }
        } else {
            EconomyAction(null, false, "failure")
        }
    }

    override fun deleteWalletAccount(wallet: Wallet, world: String?): EconomyAction {
        return deleteWalletAccount(wallet)
    }

    override fun deleteAccount(accountID: String): EconomyAction {
        return if (economy.hasAccount(accountID)) {
            economy.deleteAccount(accountID)
            EconomyAction(null, true, "ok")
        } else {
            EconomyAction(null, false, "The account doesn't exist")
        }
    }

    override fun deleteAccount(accountID: String, world: String?): EconomyAction {
        return deleteAccount(accountID)
    }

    override fun deleteAccount(account: Account?): EconomyAction {
        return if (account is EnterprisePlayerAccount) {
            EconomyAction(account.holder, false, "You cannot delete player's account")
        } else if (account is EnterpriseAccount) {
            economy.deleteAccount(account.account.name)
            EconomyAction(account.holder, true, "ok")
        } else {
            EconomyAction(null, false, "failure")
        }
    }

    override fun deleteAccount(account: Account, world: String?): EconomyAction {
        return deleteAccount(account)
    }

    override fun getAccounts(): MutableList<Account> {
        val res = mutableListOf<Account>()
        economy.accounts.forEach {
            if (it is NormalBankAccount) {
                res.add(EnterpriseAccount.get(it))
            } else if (it is PlayerBankAccount) {
                res.add(EnterprisePlayerAccount.get(it))
            }
        }
        return res
    }

    override fun getAccountList(): MutableList<String> {
        val res = mutableListOf<String>()
        accounts.forEach {
            res.add(it.id)
        }
        return res
    }
}