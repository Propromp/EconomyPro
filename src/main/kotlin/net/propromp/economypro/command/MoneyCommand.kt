package net.propromp.economypro.command

import net.propromp.economypro.Main
import net.propromp.economypro.api.ProEconomy
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class MoneyCommand :CommandExecutor{
    /**
     * Executes the given command, returning its success.
     * <br></br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("economypro.money")) {
            if (sender is OfflinePlayer) {
                sender.sendMessage(
                    "Balance of ${Main.economy.getSelectedAccount(sender).name}:" + ChatColor.GOLD + Main.economy.getSelectedAccount(sender).balance
                )
            } else {
                sender.sendMessage("${ChatColor.DARK_RED}This command can only be executed from the player ")
            }
        } else {
            sender.sendMessage("Unknown command. Type\"/help\" for help.")
        }
        return true
    }
}