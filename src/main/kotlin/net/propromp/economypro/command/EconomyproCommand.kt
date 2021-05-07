package net.propromp.economypro.command

import net.propromp.economypro.Main
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.ChatColor.DARK_GRAY as dgray
import org.bukkit.ChatColor.GOLD as gold
import org.bukkit.ChatColor.RED as red
import org.bukkit.ChatColor.WHITE as white

class EconomyproCommand {
    fun run(sender: CommandSender){
        sender.sendMessage(
            "$dgray----------[$red EconomyProHELP $dgray]----------\n" +
                    "$gold/money$dgray»$white ${Main.lang.get(sender, "command.help.money")}\n" +
                    "$gold/bank$dgray»$white ${Main.lang.get(sender, "command.help.bank")}\n" +
                    "$gold/balance$dgray»$white ${Main.lang.get(sender, "command.help.balance")}\n" +
                    "$gold/pay <player> <amount>$dgray»$white ${Main.lang.get(sender, "command.help.pay")}\n" +
                    "$gold/economypro$dgray»$white ${Main.lang.get(sender, "command.help.economypro")}\n" +
                    "$gold/balancetop$dgray»$white ${Main.lang.get(sender, "command.help.balancetop")}\n" +
                    "$dgray---------------------------------------------"
        )
    }
    fun getVault(sender: CommandSender){
        if (Main.instance.hook.isVaultHooked) {//hooked
            sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.true"))
        } else {//not hooked
            sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.false"))
        }
    }
    fun hookVault(sender: CommandSender){
        Main.instance.hook.hookVault()
        sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.hook"))
    }
    fun unhookVault(sender: CommandSender){
        Main.instance.hook.unhookVault()
        sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.unhook"))
    }
    fun getEnterprise(sender: CommandSender){
        if (Main.instance.hook.isEnterpriseHooked) {//hooked
            sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.true"))
        } else {//not hooked
            sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.false"))
        }
    }
    fun hookEnterprise(sender: CommandSender){
        Main.instance.hook.hookEnterprise()
        sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.hook"))
    }
    fun unhookEnterprise(sender: CommandSender){
        Main.instance.hook.unhookEnterprise()
        sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.unhook"))
    }
}