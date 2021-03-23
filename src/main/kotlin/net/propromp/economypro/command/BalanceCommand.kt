package net.propromp.economypro.command

import net.propromp.economypro.Main
import net.propromp.economypro.api.NormalBankAccount
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.ChatColor.AQUA as aqua
import org.bukkit.ChatColor.DARK_GRAY as dgray
import org.bukkit.ChatColor.DARK_RED as dred
import org.bukkit.ChatColor.GOLD as gold
import org.bukkit.ChatColor.RED as red
import org.bukkit.ChatColor.WHITE as white

class BalanceCommand:CommandExecutor,TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender.hasPermission("economypro.balance.get")){
            if(sender is Player) {
                if (args.isEmpty()) {
                    sender.sendMessage("${dred}${Main.lang.get(sender,"command.not_enough_arguments")}")
                    return true
                }
                Bukkit.getPlayer(args[0])?.let {
                    sender.sendMessage(
                        Main.lang.get(sender,"command.balance.get").replace("%val1%",
                            Main.economy.getDefaultAccount(it)!!.name) + gold + Main.economy.getSelectedAccount(
                            it
                        ).balance
                    )
                }
                Main.economy.getAccount(args[0])?.let {
                    sender.sendMessage(Main.lang.get(sender,"command.balance.get").replace("%val1%",it.name) + gold + it.balance)
                }
            }
        }
        if(sender is Player) {
            if (sender.hasPermission("economypro.balance.set")) {
                when (args[0]) {
                    "help" -> {
                        sender.sendMessage(
                            "$dgray----------[$red EconomyProHELP$dgray -$white /bank $dgray]----------\n" +
                                    "$gold/balance help$dgray»$white ${Main.lang.get(sender, "command.balance.help.help")}\n" +
                                    "$gold/balance set [player/bank] [amount]$dgray»$white ${Main.lang.get(sender, "command.balance.help.set")}\n" +
                                    "$gold/balance [player/bank]$dgray»$white  ${Main.lang.get(sender, "command.balance.help.get")}\n" +
                                    "$gold/balance deposit [player/bank] [amount]$dgray»$white ${Main.lang.get(sender, "command.balance.help.deposit")}\n" +
                                    "$gold/balance withdraw [player/bank] [amount]$dgray»$white ${Main.lang.get(sender, "command.balance.help.withdraw")}\n" +
                                    "$dgray---------------------------------------------"
                        )
                        return true
                    }
                    "set" -> {
                        Bukkit.getPlayer(args[1])?.let {
                            Main.economy.getDefaultAccount(it)!!.let {
                                args[2].toDoubleOrNull()?.run {
                                    it.balance = this
                                    sender.sendMessage("${aqua}${Main.lang.get(sender, "command.balance.set.1").replace("%val1%",args[1]).replace("%val2%",args[2])}")
                                    return true
                                }
                            }
                        }
                        Main.economy.getAccount(args[1])?.let {
                            args[2].toDoubleOrNull()?.run {
                                it.balance = this
                                sender.sendMessage("${aqua}${Main.lang.get(sender, "command.balance.set.2").replace("%val1%",args[1]).replace("%val2%",args[2])}")
                                return true
                            }
                        }
                    }
                    "deposit" -> {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Main.economy.getDefaultAccount(Bukkit.getPlayer(args[1])!!)?.let {
                                args[2].toDoubleOrNull()?.run {
                                    it.balance += this
                                    sender.sendMessage("${aqua}${Main.lang.get(sender, "command.balance.deposit.1").replace("%val1%",args[1]).replace("%val2%",args[2])}")
                                    return true
                                }
                            }
                        } else {
                            Main.economy.getAccount(args[1])?.let {
                                args[2].toDoubleOrNull()?.run {
                                    it.balance += this
                                    sender.sendMessage("$aqua${Main.lang.get(sender, "command.balance.deposit.2").replace("%val1%",args[1]).replace("%val2%",args[2])}")
                                    return true
                                }
                            }
                        }
                    }
                    "withdraw" -> {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Main.economy.getDefaultAccount(Bukkit.getPlayer(args[1])!!)?.let {
                                args[2].toDoubleOrNull()?.run {
                                    it.balance -= this
                                    sender.sendMessage("${aqua}${Main.lang.get(sender, "command.balance.withdraw.1").replace("%val1%",args[1]).replace("%val2%",args[2])}")
                                    return true
                                }
                            }
                        } else {
                            Main.economy.getAccount(args[1])?.let {
                                args[2].toDoubleOrNull()?.run {
                                    it.balance -= this
                                    sender.sendMessage("${aqua}${Main.lang.get(sender, "command.balance.withdraw.2").replace("%val1%",args[1]).replace("%val2%",args[2])}")
                                    return true
                                }
                            }
                        }
                    }
                }
            }
        }  else {
            sender.sendMessage("${dred}This command can only be executed from player.")
            return true
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if(sender is Player) {
            return when (args.size) {
                1 -> {
                    var res = mutableListOf<String>()
                    if(sender.hasPermission("economypro.balance.get")){
                        Bukkit.getOnlinePlayers().forEach{res.add(it.name)}
                        Main.economy.accounts.filterIsInstance<NormalBankAccount>().forEach {
                            res.add(it.name)
                        }
                    }
                    if(sender.hasPermission("economypro.balance.set")){
                        res.addAll(listOf("help", "set","deposit","withdraw"))
                    }
                    return res.filter { it.startsWith(args[0],true) }.toMutableList()
                }
                2 -> {
                    return when(args[0]){
                        "set","deposit","withdraw"->{
                            var res = mutableListOf<String>()
                            Main.economy.accounts.forEach{
                                res.add(it.name)
                            }
                            return res.filter{it.startsWith(args[1],true)}.toMutableList()
                        }
                        else->{
                            mutableListOf()
                        }
                    }
                }
                3->{
                    mutableListOf("[amount]")
                }
                else-> mutableListOf()
            }
        }
        return mutableListOf()
    }
}