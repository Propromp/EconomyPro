package net.propromp.economypro.command

import net.propromp.economypro.Main
import net.propromp.economypro.api.BankAccount
import org.bukkit.command.CommandSender
import kotlin.math.roundToInt
import org.bukkit.ChatColor.AQUA as aqua
import org.bukkit.ChatColor.DARK_GRAY as dgray
import org.bukkit.ChatColor.GOLD as gold
import org.bukkit.ChatColor.RED as red
import org.bukkit.ChatColor.WHITE as white

class BalanceCommand {
    fun get(sender: CommandSender, account: BankAccount): Int {
        sender.sendMessage(
            Main.lang.get(sender, "command.balance.get").replace("%val1%", account.name) + gold + account.balance
        )
        return account.balance.roundToInt()
    }

    fun help(sender: CommandSender) {
        sender.sendMessage(
            "$dgray----------[$red EconomyProHELP$dgray -$white /bank $dgray]----------\n" +
                    "$gold/balance help$dgray»$white ${Main.lang.get(sender, "command.balance.help.help")}\n" +
                    "$gold/balance set [player/bank] [amount]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.balance.help.set"
                        )
                    }\n" +
                    "$gold/balance [player/bank]$dgray»$white  ${Main.lang.get(sender, "command.balance.help.get")}\n" +
                    "$gold/balance deposit [player/bank] [amount]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.balance.help.deposit"
                        )
                    }\n" +
                    "$gold/balance withdraw [player/bank] [amount]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.balance.help.withdraw"
                        )
                    }\n" +
                    "$dgray---------------------------------------------"
        )
        return
    }

    fun set(sender: CommandSender, account: BankAccount, amount: Double) {
        account.balance = amount
        sender.sendMessage(
            "${aqua}${
                Main.lang.get(sender, "command.balance.set.1").replace("%val1%", account.name)
                    .replace("%val2%", amount.toString())
            }"
        )
        return
    }

    fun deposit(sender: CommandSender, account: BankAccount, amount: Double) {
        set(sender, account, get(sender, account) + amount)
        return
    }

    fun withdraw(sender: CommandSender, account: BankAccount, amount: Double) {
        set(sender, account, get(sender, account) - amount)
        return
    }
}