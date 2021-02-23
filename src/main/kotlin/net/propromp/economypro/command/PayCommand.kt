package net.propromp.economypro.command

import net.propromp.economypro.Main
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class PayCommand :CommandExecutor,TabCompleter{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.size < 2){
            sender.sendMessage("${ChatColor.DARK_RED}Not enough arguments")
            return true
        }
        if(sender is Player) {
            Bukkit.getPlayer(args[0])?.let { target ->
                args[1].toDoubleOrNull()?.let { amount ->
                    var targetAccount = Main.economy.getSelectedAccount(target)
                    var senderAccount = Main.economy.getSelectedAccount(sender)
                    if (senderAccount.has(amount)) {
                        senderAccount.withdraw(amount)
                        targetAccount.deposit(amount)
                        sender.sendMessage("${ChatColor.AQUA}${Main.lang.get(sender,"command.pay.paid").replace("%val1%",Main.economy.plural).replace("%val2%",target.name)}")
                        target.sendMessage("${ChatColor.AQUA}${Main.lang.get(sender,"command.pay.received").replace("%val1%",Main.economy.plural).replace("%val2%",target.name)}")
                        sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,1.0f)
                        target.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f,1.0f)
                        return true
                    } else {
                        sender.sendMessage("${ChatColor.DARK_RED}${Main.lang.get(sender,"command.pay.not_enough_balance")}")
                        return true
                    }
                }
            }
        } else {
            sender.sendMessage("${ChatColor.DARK_RED}This command can only be executed from player.")
            return true
        }
        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        return when(args.size){
            1->{
                var res = mutableListOf<String>()
                Bukkit.getOnlinePlayers().forEach {
                    res.add(it.name)
                }
                res.filter { it.startsWith(args[0]) }.toMutableList()
            }
            else->{
                mutableListOf()
            }
        }
    }
}