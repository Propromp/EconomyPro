package net.propromp.economypro.enterprise

import com.github.sanctum.economy.construct.EconomyAction
import com.github.sanctum.economy.construct.account.PlayerWallet
import net.propromp.economypro.Main
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.math.BigDecimal

class EnterprisePlayerWallet(player: OfflinePlayer) : PlayerWallet(player) {
    var account = Main.economy.getDefaultAccount(player)

    /**
     * Attempt to deposit an amount
     * @param amount [BigDecimal] amount
     */
    override fun deposit(amount: BigDecimal): EconomyAction {
        account?.deposit(amount.toDouble())
        return EconomyAction(amount, holder, true, "ok")
    }

    /**
     * Attempt to deposit an amount in the world 'world'
     * @param amount [BigDecimal] amount
     * @param world Name of world
     */
    override fun deposit(amount: BigDecimal, world: String): EconomyAction {
        Bukkit.getWorld(world)?.let {
            account?.deposit(amount.toDouble(), it)
            return EconomyAction(amount, holder, true, "ok")
        }
        return EconomyAction(BigDecimal(0), holder, false, "No such a world")
    }

    /**
     * Attempt to withdraw an amount
     * @param amount [BigDecimal] amount
     */
    override fun withdraw(amount: BigDecimal): EconomyAction {
        account?.withdraw(amount.toDouble())
        return EconomyAction(amount, holder, true, "ok")
    }

    /**
     * Attempt to withdraw an amount in the world 'world'
     * @param amount [BigDecimal] amount
     * @param world Name of world
     */
    override fun withdraw(amount: BigDecimal, world: String): EconomyAction {
        Bukkit.getWorld(world)?.let {
            account?.withdraw(amount.toDouble(), it)
            return EconomyAction(amount, holder, true, "ok")
        }
        return EconomyAction(BigDecimal(0), holder, false, "No such a world")
    }

    /**
     * Set this Balance in a general context. [.exists] might or
     * might not be referenced in an implementation; it is recommended
     * but not required.
     * @param amount New amount
     */
    override fun setBalance(amount: BigDecimal) {
        account?.balance = amount.toDouble()
    }

    /**
     * Set this Balance in the specific World 'world' context.
     * [.exists] might or might not be referenced in an
     * implementation; it is recommended but not required.
     * @param amount New amount
     * @param world Name of world
     */
    override fun setBalance(amount: BigDecimal, world: String) {
        Bukkit.getWorld(world)?.let {
            account?.worldBalance?.set(it, amount.toDouble())
        }
    }

    /**
     * Check if this Balance exists in a general context. Realistically,
     * this will return True for most Wallet implementations and thus
     * will be of most use when working with Account subtypes.
     * @return true if exists, false otherwise
     */
    override fun exists(): Boolean {
        return account != null
    }

    /**
     * Check if this Balance exists in the specific World 'world' context.
     * Realistically, this will return True for most Wallet implementations
     * and thus will be of most use when working with Account subtypes.
     * @param world Name of world
     * @return true if exists, false otherwise
     */
    override fun exists(world: String): Boolean {
        return account?.worldBalance!!.containsKey(Bukkit.getWorld(world))
    }

    /**
     * Get the value of this Balance object in a general context.
     * @return value as a [BigDecimal] if present or null
     */
    override fun getBalance(): BigDecimal? {
        return account?.balance?.let { BigDecimal(it) }
    }

    /**
     * Get the value of this Balance object in the specific
     * World 'world' context.
     * @param world Name of world
     * @return value as a [BigDecimal] if present or null
     */
    override fun getBalance(world: String): BigDecimal? {
        Bukkit.getWorld(world)?.let {
            return account?.getBalance(it)?.let { it1 -> BigDecimal(it1) }
        }
        return BigDecimal(0)
    }

    /**
     * Test if this Balance is greater than or equal to an amount
     * in a general context.
     * @param amount amount to test
     * @return true if balance >= to amount, false otherwise
     */
    override fun has(amount: BigDecimal): Boolean {
        return account?.has(amount.toDouble()) ?: false
    }

    /**
     * Test if this Balance is greater than or equal to an amount
     * in the context of world 'world'.
     * @param amount amount to test
     * @return true if balance >= to amount, false otherwise
     */
    override fun has(amount: BigDecimal, world: String): Boolean {
        Bukkit.getWorld(world)?.let {
            return account?.has(amount.toDouble(), it) ?: false
        }
        return false
    }
}