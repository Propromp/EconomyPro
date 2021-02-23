package net.propromp.economypro.enterprise

import com.github.sanctum.economy.construct.EconomyAction
import com.github.sanctum.economy.construct.EconomyPriority
import com.github.sanctum.economy.construct.account.Account
import com.github.sanctum.economy.construct.account.Wallet
import com.github.sanctum.economy.construct.account.permissive.AccountType
import com.github.sanctum.economy.construct.currency.normal.EconomyCurrency
import com.github.sanctum.economy.construct.implement.AdvancedEconomy
import org.bukkit.OfflinePlayer
import org.bukkit.plugin.Plugin
import java.math.BigDecimal
import java.util.*

class EnterpriseEconomy :AdvancedEconomy{
    override fun getPlugin(): Plugin {
        TODO("Not yet implemented")
    }

    override fun getVersion(): String {
        TODO("Not yet implemented")
    }

    override fun getCurrency(): EconomyCurrency {
        TODO("Not yet implemented")
    }

    override fun getCurrency(world: String?): EconomyCurrency {
        TODO("Not yet implemented")
    }

    override fun getPriority(): EconomyPriority {
        TODO("Not yet implemented")
    }

    override fun format(amount: BigDecimal?): String {
        TODO("Not yet implemented")
    }

    override fun format(amount: BigDecimal?, locale: Locale?): String {
        TODO("Not yet implemented")
    }

    override fun getMaxWalletSize(): BigDecimal {
        TODO("Not yet implemented")
    }

    override fun isMultiWorld(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isMultiCurrency(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasMultiAccountSupport(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasWalletSizeLimit(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAccount(name: String?): Account {
        TODO("Not yet implemented")
    }

    override fun getAccount(name: String?, type: AccountType?): Account {
        TODO("Not yet implemented")
    }

    override fun getAccount(accountId: String?, name: String?): Account {
        TODO("Not yet implemented")
    }

    override fun getAccount(player: OfflinePlayer?, type: AccountType?): Account {
        TODO("Not yet implemented")
    }

    override fun getAccount(player: OfflinePlayer?): Account {
        TODO("Not yet implemented")
    }

    override fun getAccount(accountId: String?, player: OfflinePlayer?): Account {
        TODO("Not yet implemented")
    }

    override fun getAccount(uuid: UUID?): Account {
        TODO("Not yet implemented")
    }

    override fun getAccount(uuid: UUID?, type: AccountType?): Account {
        TODO("Not yet implemented")
    }

    override fun getAccount(accountId: String?, uuid: UUID?): Account {
        TODO("Not yet implemented")
    }

    override fun getWallet(name: String?): Wallet {
        TODO("Not yet implemented")
    }

    override fun getWallet(player: OfflinePlayer?): Wallet {
        TODO("Not yet implemented")
    }

    override fun getWallet(uuid: UUID?): Wallet {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, name: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, name: String?, accountId: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, name: String?, startingAmount: BigDecimal?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, name: String?, accountId: String?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(
        type: AccountType?,
        name: String?,
        accountId: String?,
        world: String?,
        startingAmount: BigDecimal?
    ): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, player: OfflinePlayer?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, player: OfflinePlayer?, accountId: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, player: OfflinePlayer?, startingAmount: BigDecimal?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(
        type: AccountType?,
        player: OfflinePlayer?,
        accountId: String?,
        world: String?
    ): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(
        type: AccountType?,
        player: OfflinePlayer?,
        accountId: String?,
        world: String?,
        startingAmount: BigDecimal?
    ): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, uuid: UUID?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, uuid: UUID?, accountId: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, uuid: UUID?, startingAmount: BigDecimal?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(type: AccountType?, uuid: UUID?, accountId: String?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun createAccount(
        type: AccountType?,
        uuid: UUID?,
        accountId: String?,
        world: String?,
        startingAmount: BigDecimal?
    ): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun deleteWalletAccount(wallet: Wallet?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun deleteWalletAccount(wallet: Wallet?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun deleteAccount(accountID: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun deleteAccount(accountID: String?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun deleteAccount(account: Account?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun deleteAccount(account: Account?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun getAccounts(): MutableList<Account> {
        TODO("Not yet implemented")
    }

    override fun getAccountList(): MutableList<String> {
        TODO("Not yet implemented")
    }
}