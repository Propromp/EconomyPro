package net.propromp.economypro.command

import net.propromp.economypro.Main
import net.propromp.economypro.api.ProEconomy
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import kotlin.math.roundToInt

class MoneyCommand{
    fun run(sender: CommandSender): Int {
        if(sender.hasPermission("economypro.money")) {
            if (sender is OfflinePlayer) {
                sender.sendMessage(
                    "Balance of ${Main.economy.getSelectedAccount(sender).name}:" + ChatColor.GOLD + Main.economy.getSelectedAccount(sender).balance
                )
                return Main.economy.getSelectedAccount(sender).balance.roundToInt()
            } else {
                sender.sendMessage("${ChatColor.DARK_RED}This command can only be executed from the player ")
                return 0
            }
        } else {
            sender.sendMessage("Unknown command. Type\"/help\" for help.")
            return 0
        }
    }
}