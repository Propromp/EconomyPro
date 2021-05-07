package net.propromp.economypro.command

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import net.propromp.economypro.Main
import net.propromp.economypro.api.BankAccount
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.ChatColor.DARK_GRAY as dgray
import org.bukkit.ChatColor.WHITE as white
import org.bukkit.ChatColor.RED as red

class BalancetopCommand {
    fun run(sender: CommandSender): Boolean {
        var sorted = ArrayList(Main.economy.accounts)
        sorted.sortByDescending{ it.balance }
        var i = 0
        sender.sendMessage("$dgray-----[$red${Main.lang.get(sender,"command.balancetop.title")}$dgray]-----")
        sorted.forEach{
            if(i < 10){
                var color=when(i){
                    0->ChatColor.GOLD
                    else->ChatColor.WHITE
                }
                var text = TextComponent(Main.lang.get(sender,"command.balancetop.val").replace("[val1]","${color}${i+1}").replace("[val2]",it.name).replace("[val3]",it.balance.toString()+Main.economy.plural))
                text.clickEvent= ClickEvent(ClickEvent.Action.RUN_COMMAND,"/bank info ${it.name}")
                sender.sendMessage(text)
            } else {
                return@forEach
            }
            i++
        }
        sender.sendMessage("$dgray-------------------------")
        return true
    }
}