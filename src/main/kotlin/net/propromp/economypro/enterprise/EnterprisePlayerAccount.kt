package net.propromp.economypro.enterprise

import com.github.sanctum.economy.construct.EconomyAction
import com.github.sanctum.economy.construct.account.Account
import com.github.sanctum.economy.construct.account.permissive.AccountType
import com.github.sanctum.economy.construct.entity.EconomyEntity
import net.propromp.economypro.api.PlayerBankAccount
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.math.BigDecimal
import java.util.*

class EnterprisePlayerAccount(
    accountType: AccountType?,
    holder: EconomyEntity, vararg members: EconomyEntity,
) : Account(accountType, holder, *members) {
    lateinit var account: PlayerBankAccount

    companion object {
        fun get(bankAccount: PlayerBankAccount): EnterprisePlayerAccount {
            val accountType = AccountType.BANK_ACCOUNT
            val holder = PlayerEconomyEntity(bankAccount.player)
            val members = mutableListOf<EconomyEntity>()
            val eAccount = EnterprisePlayerAccount(accountType, holder, *members.toTypedArray())
            eAccount.account = bankAccount
            return eAccount
        }
    }

    /**
     * Attempt to deposit an amount
     * @param amount [BigDecimal] amount
     */
    override fun deposit(amount: BigDecimal): EconomyAction {
        account.deposit(amount.toDouble())
        return EconomyAction(amount, holder, true, "OK")
    }

    /**
     * Attempt to deposit an amount in the world 'world'
     * @param amount [BigDecimal] amount
     * @param world Name of world
     */
    override fun deposit(amount: BigDecimal, world: String): EconomyAction {
        return EconomyAction(amount, holder, false, "Not implemented")
    }

    /**
     * Attempt to withdraw an amount
     * @param amount [BigDecimal] amount
     */
    override fun withdraw(amount: BigDecimal): EconomyAction {
        account.withdraw(amount.toDouble())
        return EconomyAction(amount, holder, true, "OK")
    }

    /**
     * Attempt to withdraw an amount in the world 'world'
     * @param amount [BigDecimal] amount
     * @param world Name of world
     */
    override fun withdraw(amount: BigDecimal?, world: String?): EconomyAction {
        return EconomyAction(amount, holder, false, "Not implemented")
    }

    /**
     * Set this Balance in a general context. [.exists] might or
     * might not be referenced in an implementation; it is recommended
     * but not required.
     * @param amount New amount
     */
    override fun setBalance(amount: BigDecimal) {
        account.balance = amount.toDouble()
    }

    /**
     * Set this Balance in the specific World 'world' context.
     * [.exists] might or might not be referenced in an
     * implementation; it is recommended but not required.
     * @param amount New amount
     * @param world Name of world
     */
    override fun setBalance(amount: BigDecimal, world: String) {}

    /**
     * Check if this Balance exists in a general context. Realistically,
     * this will return True for most Wallet implementations and thus
     * will be of most use when working with Account subtypes.
     * @return true if exists, false otherwise
     */
    override fun exists(): Boolean {
        return true
    }

    /**
     * Check if this Balance exists in the specific World 'world' context.
     * Realistically, this will return True for most Wallet implementations
     * and thus will be of most use when working with Account subtypes.
     * @param world Name of world
     * @return true if exists, false otherwise
     */
    override fun exists(world: String?): Boolean {
        return false
    }

    /**
     * Get the value of this Balance object in a general context.
     * @return value as a [BigDecimal] if present or null
     */
    override fun getBalance(): BigDecimal {
        return BigDecimal(account.balance)
    }

    /**
     * Get the value of this Balance object in the specific
     * World 'world' context.
     * @param world Name of world
     * @return value as a [BigDecimal] if present or null
     */
    override fun getBalance(world: String?): BigDecimal? {
        return null
    }

    /**
     * Test if this Balance is greater than or equal to an amount
     * in a general context.
     * @param amount amount to test
     * @return true if balance >= to amount, false otherwise
     */
    override fun has(amount: BigDecimal): Boolean {
        return account.has(amount.toDouble())
    }

    /**
     * Test if this Balance is greater than or equal to an amount
     * in the context of world 'world'.
     * @param amount amount to test
     * @return true if balance >= to amount, false otherwise
     */
    override fun has(amount: BigDecimal?, world: String?): Boolean {
        return false
    }

    override fun isOwner(name: String): EconomyAction {
        return EconomyAction(holder, account.player == Bukkit.getOfflinePlayer(name), "deprecated")
    }

    override fun isOwner(name: String?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isOwner(player: OfflinePlayer): EconomyAction {
        return EconomyAction(holder, account.player == player, "ok")
    }

    override fun isOwner(player: OfflinePlayer?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isOwner(uuid: UUID): EconomyAction {
        return EconomyAction(holder, account.player == Bukkit.getOfflinePlayer(uuid), "ok")
    }

    override fun isOwner(uuid: UUID?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isJointOwner(name: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isJointOwner(name: String?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isJointOwner(player: OfflinePlayer?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isJointOwner(player: OfflinePlayer?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isJointOwner(uuid: UUID?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isJointOwner(uuid: UUID?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isMember(name: String): EconomyAction {
        return isOwner(name)
    }

    override fun isMember(name: String?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isMember(player: OfflinePlayer): EconomyAction {
        return isOwner(player)
    }

    override fun isMember(player: OfflinePlayer?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun isMember(uuid: UUID): EconomyAction {
        return isOwner(uuid)
    }

    override fun isMember(uuid: UUID?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun addMember(name: String): EconomyAction {
        Bukkit.getOfflinePlayer(name).let {
            return addMember(it)
        }
    }

    override fun addMember(name: String?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun addMember(player: OfflinePlayer): EconomyAction {
        return EconomyAction(holder, false, "You cannot add member to player account")
    }

    override fun addMember(player: OfflinePlayer?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun addMember(uuid: UUID): EconomyAction {
        Bukkit.getOfflinePlayer(uuid).let {
            return addMember(it)
        }
    }

    override fun addMember(uuid: UUID?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }

    override fun removeMember(name: String): EconomyAction {
        Bukkit.getOfflinePlayer(name).let {
            return removeMember(it)
        }
    }

    override fun removeMember(name: String?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "player doesn't exist")
    }

    override fun removeMember(player: OfflinePlayer): EconomyAction {
        return EconomyAction(holder, false, "You cannot remove member from player account")
    }

    override fun removeMember(player: OfflinePlayer?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "player doesn't exist")
    }

    override fun removeMember(uuid: UUID): EconomyAction {
        Bukkit.getOfflinePlayer(uuid).let {
            return removeMember(it)
        }
    }

    override fun removeMember(uuid: UUID?, world: String?): EconomyAction {
        return EconomyAction(holder, false, "Not implemented")
    }
}