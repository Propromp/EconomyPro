package net.propromp.economypro.enterprise

import com.github.sanctum.economy.construct.EconomyAction
import com.github.sanctum.economy.construct.account.Wallet
import com.github.sanctum.economy.construct.entity.EconomyEntity
import net.propromp.economypro.Main
import java.math.BigDecimal

class EnterpriseWallet(name:String): Wallet(EconomyEntity { name }){
    var account = Main.economy.getAccount(name)
    /**
     * Attempt to deposit an amount
     * @param amount [BigDecimal] amount
     */
    override fun deposit(amount: BigDecimal): EconomyAction {
        if(exists()) {
            account?.deposit(amount.toDouble())
            return EconomyAction(amount,holder,true,"ok")
        }
        return EconomyAction(BigDecimal(0),holder,false,"failure")
    }

    /**
     * Attempt to deposit an amount in the world 'world'
     * @param amount [BigDecimal] amount
     * @param world Name of world
     */
    override fun deposit(amount: BigDecimal?, world: String?): EconomyAction {
        return EconomyAction(BigDecimal(0),holder,false,"not implemented")
    }

    /**
     * Attempt to withdraw an amount
     * @param amount [BigDecimal] amount
     */
    override fun withdraw(amount: BigDecimal): EconomyAction {
        if(exists()) {
            account?.withdraw(amount.toDouble())
            return EconomyAction(amount,holder,true,"ok")
        }
        return EconomyAction(BigDecimal(0),holder,false,"failure")
    }

    /**
     * Attempt to withdraw an amount in the world 'world'
     * @param amount [BigDecimal] amount
     * @param world Name of world
     */
    override fun withdraw(amount: BigDecimal?, world: String?): EconomyAction {
        return EconomyAction(BigDecimal(0),holder,false,"not implemented")
    }

    /**
     * Set this Balance in a general context. [.exists] might or
     * might not be referenced in an implementation; it is recommended
     * but not required.
     * @param amount New amount
     */
    override fun setBalance(amount: BigDecimal) {
        account?.balance=amount.toDouble()
    }

    /**
     * Set this Balance in the specific World 'world' context.
     * [.exists] might or might not be referenced in an
     * implementation; it is recommended but not required.
     * @param amount New amount
     * @param world Name of world
     */
    override fun setBalance(amount: BigDecimal?, world: String?) {}

    /**
     * Check if this Balance exists in a general context. Realistically,
     * this will return True for most Wallet implementations and thus
     * will be of most use when working with Account subtypes.
     * @return true if exists, false otherwise
     */
    override fun exists(): Boolean {
        return account!=null
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
        return BigDecimal(account?.balance ?: 0.0)
    }

    /**
     * Get the value of this Balance object in the specific
     * World 'world' context.
     * @param world Name of world
     * @return value as a [BigDecimal] if present or null
     */
    override fun getBalance(world: String?): BigDecimal {
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
    override fun has(amount: BigDecimal?, world: String?): Boolean {
        return false
    }
}