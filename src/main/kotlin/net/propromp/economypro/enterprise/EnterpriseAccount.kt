package net.propromp.economypro.enterprise

import com.github.sanctum.economy.construct.EconomyAction
import com.github.sanctum.economy.construct.account.Account
import com.github.sanctum.economy.construct.account.permissive.AccountType
import com.github.sanctum.economy.construct.entity.EconomyEntity
import net.propromp.economypro.Main
import net.propromp.economypro.api.NormalBankAccount
import net.propromp.economypro.api.PlayerBankAccount
import org.bukkit.OfflinePlayer
import java.math.BigDecimal
import java.util.*

class EnterpriseAccount(name: String,owner:OfflinePlayer) :Account(null,null,null){
    var account = Main.economy.getAccount(name)
    /**
     * Attempt to deposit an amount
     * @param amount [BigDecimal] amount
     */
    override fun deposit(amount: BigDecimal?): EconomyAction {
        TODO("Not yet implemented")
    }

    /**
     * Attempt to deposit an amount in the world 'world'
     * @param amount [BigDecimal] amount
     * @param world Name of world
     */
    override fun deposit(amount: BigDecimal?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    /**
     * Attempt to withdraw an amount
     * @param amount [BigDecimal] amount
     */
    override fun withdraw(amount: BigDecimal?): EconomyAction {
        TODO("Not yet implemented")
    }

    /**
     * Attempt to withdraw an amount in the world 'world'
     * @param amount [BigDecimal] amount
     * @param world Name of world
     */
    override fun withdraw(amount: BigDecimal?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    /**
     * Set this Balance in a general context. [.exists] might or
     * might not be referenced in an implementation; it is recommended
     * but not required.
     * @param amount New amount
     */
    override fun setBalance(amount: BigDecimal?) {
        TODO("Not yet implemented")
    }

    /**
     * Set this Balance in the specific World 'world' context.
     * [.exists] might or might not be referenced in an
     * implementation; it is recommended but not required.
     * @param amount New amount
     * @param world Name of world
     */
    override fun setBalance(amount: BigDecimal?, world: String?) {
        TODO("Not yet implemented")
    }

    /**
     * Check if this Balance exists in a general context. Realistically,
     * this will return True for most Wallet implementations and thus
     * will be of most use when working with Account subtypes.
     * @return true if exists, false otherwise
     */
    override fun exists(): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Check if this Balance exists in the specific World 'world' context.
     * Realistically, this will return True for most Wallet implementations
     * and thus will be of most use when working with Account subtypes.
     * @param world Name of world
     * @return true if exists, false otherwise
     */
    override fun exists(world: String?): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Get the value of this Balance object in a general context.
     * @return value as a [BigDecimal] if present or null
     */
    override fun getBalance(): BigDecimal? {
        TODO("Not yet implemented")
    }

    /**
     * Get the value of this Balance object in the specific
     * World 'world' context.
     * @param world Name of world
     * @return value as a [BigDecimal] if present or null
     */
    override fun getBalance(world: String?): BigDecimal? {
        TODO("Not yet implemented")
    }

    /**
     * Test if this Balance is greater than or equal to an amount
     * in a general context.
     * @param amount amount to test
     * @return true if balance >= to amount, false otherwise
     */
    override fun has(amount: BigDecimal?): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Test if this Balance is greater than or equal to an amount
     * in the context of world 'world'.
     * @param amount amount to test
     * @return true if balance >= to amount, false otherwise
     */
    override fun has(amount: BigDecimal?, world: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isOwner(name: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isOwner(name: String?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isOwner(player: OfflinePlayer?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isOwner(player: OfflinePlayer?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isOwner(uuid: UUID?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isOwner(uuid: UUID?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isJointOwner(name: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isJointOwner(name: String?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isJointOwner(player: OfflinePlayer?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isJointOwner(player: OfflinePlayer?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isJointOwner(uuid: UUID?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isJointOwner(uuid: UUID?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isMember(name: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isMember(name: String?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isMember(player: OfflinePlayer?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isMember(player: OfflinePlayer?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isMember(uuid: UUID?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun isMember(uuid: UUID?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun addMember(name: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun addMember(name: String?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun addMember(player: OfflinePlayer?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun addMember(player: OfflinePlayer?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun addMember(uuid: UUID?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun addMember(uuid: UUID?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun removeMember(name: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun removeMember(name: String?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun removeMember(player: OfflinePlayer?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun removeMember(player: OfflinePlayer?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun removeMember(uuid: UUID?): EconomyAction {
        TODO("Not yet implemented")
    }

    override fun removeMember(uuid: UUID?, world: String?): EconomyAction {
        TODO("Not yet implemented")
    }
}