package net.propromp.economypro.command

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
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

class BankCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(args.isEmpty()){
            sender.sendMessage("${dred}Not enough arguments")
            return true
        }
        if (sender.hasPermission("economypro.bank")) {
            when (args[0]) {
                "help" -> {
                    sender.sendMessage(
                        "$dgray----------[$red EconomyProHELP$dgray -$white /bank $dgray]----------\n" +
                                "$gold/bank help$dgray»$white shows help\n" +
                                "$gold/bank select [bankName]$dgray»$white select a bank\n" +
                                "$gold/bank info [bankName]$dgray»$white shows the information of the bank\n" +
                                "$gold/bank delete [bankName]$dgray»$white delete the bank\n" +
                                "$gold/bank create$dgray»$white create the bank\n" +
                                "$dgray---------------------------------------------"
                    )
                    return true
                }
                "select" -> {
                    if (args.size < 2) {
                        sender.sendMessage("${dred}Not enough arguments")
                        return true
                    }
                    if (sender is Player) {
                        if (args[1] == "default") {
                            Main.economy.selectAccount(sender, Main.economy.getDefaultAccount(sender)!!)
                            sender.sendMessage("${aqua}selected default as the default bank")
                            return true
                        }
                        return if (Main.economy.hasAccount(args[1])) {
                            if (Main.economy.getAccount(args[1])!!.isMemberOrOwner(sender)) {
                                Main.economy.selectedAccounts[sender] = Main.economy.getAccount(args[1])!!
                                sender.sendMessage("${aqua}selected ${args[1]} as the default bank")
                                true
                            } else {
                                sender.sendMessage("${dred}You have to be a member or a owner of the bank to perform this command")
                                true
                            }
                        } else {
                            sender.sendMessage("${dred}The bank does not exist.")
                            true
                        }
                    } else {
                        sender.sendMessage("${dred}This command can only be executed from player.")
                        return true
                    }
                }
                "info" -> {
                    if (args.size < 2) {
                        sender.sendMessage("${dred}Not enough arguments")
                        return true
                    }
                    if (args[1] == "default" && sender is Player) {
                        Main.economy.getDefaultAccount(sender)?.let {
                            sender.sendMessage(
                                "$dgray[$red BankInfo$dgray - $white${args[1]} $dgray]\n" +
                                        "   ${gold}name$dgray»$white ${it.name}\n" +
                                        "   ${gold}owner$dgray»$white ${it.player.name}\n" +
                                        "   ${gold}balance$dgray»$white ${it.balance}\n"
                            )
                        }
                        return true
                    }
                    return if (Main.economy.hasAccount(args[1])) {
                        Main.economy.getAccount(args[1])?.let {
                            sender.sendMessage(
                                "$dgray[$red BankInfo$dgray - $white${args[1]} $dgray]\n" +
                                        "   ${gold}name$dgray»$white ${it.name}\n" +
                                        "   ${gold}owner$dgray»$white ${it.owner.name}\n" +
                                        "   ${gold}members$dgray»$white ${it.members}\n" +
                                        "   ${gold}balance$dgray»$white ${it.balance}\n"
                            )
                        }
                        true
                    } else {
                        sender.sendMessage("${dred}The bank does not exist.")
                        true
                    }
                }
                "delete" -> {
                    if (args.size < 2) {
                        sender.sendMessage("${dred}Not enough arguments")
                        return true
                    }
                    if (args[1] == "default") {
                        sender.sendMessage("${dred}You can not delete default bank account")
                        return true
                    }
                    if (sender is Player) {
                        if (Main.economy.hasAccount(args[1])) {
                            if (Main.economy.getAccount(args[1])!!.isMemberOrOwner(sender)) {
                                if (args.size > 2) {
                                    if (args[2] == "confirm") {
                                        Main.economy.deleteAccount(args[1])
                                        sender.sendMessage("${aqua}deleted the bank successfully")
                                        return true
                                    }
                                } else {
                                    sender.sendMessage("Do you really want to delete ${args[1]} ?")
                                    sender.sendMessage("${dred}By pressing [yes] The money in ${args[1]} will disappear")
                                    val yes = TextComponent("   $dred[yes]")
                                    yes.clickEvent =
                                        ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bank delete ${args[1]} confirm")
                                    sender.sendMessage(yes)
                                }
                                return true
                            } else {
                                sender.sendMessage("${dred}You have to be a owner of the bank to perform this command")
                                return true
                            }
                        } else {
                            sender.sendMessage("${dred}The bank does not exist.")
                            return true
                        }
                    } else {
                        sender.sendMessage("${dred}This command can only be executed from player.")
                        return true
                    }
                }
                "create" -> {
                    if (args.size < 2) {
                        sender.sendMessage("${dred}Not enough arguments")
                        return true
                    }
                    if (args[1] == "default") {
                        sender.sendMessage("${dred}You can not create bank account with this name")
                        return true
                    }
                    return if (sender is Player) {
                        if (!Main.economy.hasAccount(args[1])) {
                            Main.economy.createAccount(args[1], sender)
                            sender.sendMessage("${aqua}created a bank account. You can select the account with /bank select ${args[1]} ")
                            true
                        } else {
                            sender.sendMessage("${dred}The bank already exists.")
                            true
                        }
                    } else {
                        sender.sendMessage("${dred}This command can only be executed from player.")
                        true
                    }
                }
                "invite" -> {
                    if (args.size < 3) {
                        sender.sendMessage("${dred}Not enough arguments")
                        return true
                    }
                    if (sender is Player) {
                        Main.economy.getAccount(args[1])?.let { account ->
                            Bukkit.getPlayer(args[2])?.let { target ->
                                account.addInvited(target)
                                sender.sendMessage("Invited ${target} to ${account.name}")
                                target.sendMessage("You received an invitation from bank account ${account.name}")
                                var text = TextComponent("   ")
                                var join = TextComponent("[join]")
                                join.clickEvent =
                                    ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bank join ${account.name}")
                                text.addExtra(join)
                                target.sendMessage(text)
                                return true
                            }
                            sender.sendMessage("${dred}The player doesn't exist")
                            return true
                        }
                        sender.sendMessage("${dred}The bank account doesn't exist")
                        return true
                    } else {
                        sender.sendMessage("${dred}This command can only be executed from player.")
                        return true
                    }
                }
                "leave" -> {
                    if (args.size < 2) {
                        sender.sendMessage("${dred}Not enough arguments")
                        return true
                    }
                    if (sender is Player) {
                        Main.economy.getAccount(args[1])?.let { account ->
                            if (account.isMember(sender)) {
                                account.members.remove(sender)
                                sender.sendMessage("${aqua}You have left from bank account A")
                            } else if (account.isOwner(sender)) {
                                sender.sendMessage("${dred}You have to delete the bank before you leave the bank")
                            } else {
                                sender.sendMessage("${dred}You have to be a member or a owner of the bank to perform this command")
                            }
                            return true
                        }
                        sender.sendMessage("${dred}The bank account doesn't exist")
                        return true
                    } else {
                        sender.sendMessage("${dred}This command can only be executed from player.")
                        return true
                    }
                }
                "join" -> {
                    if (sender is Player) {
                        Main.economy.getAccount(args[1])?.let { account ->
                            if (account.isInvited(sender)){
                                account.members.add(sender)
                                sender.sendMessage("${aqua}You have successfully joined the bank account!")
                            } else {
                                sender.sendMessage("${dred}You are not invited from the bank account")
                            }
                            return true
                        }
                    } else {
                        sender.sendMessage("${dred}This command can only be executed from player.")
                        return true
                    }
                }
                else -> return false
            }

        } else {
            sender.sendMessage("Unknown command. Type \"/help\" for help.")
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
        if (sender is Player) {
            return when (args.size) {
                1 -> mutableListOf("help", "select", "info", "delete", "create","invite","leave","join").filter { it.startsWith(args[0]) }
                    .toMutableList()
                2 -> {
                    var res = mutableListOf<String>()
                    when (args[0]) {
                        "select" -> {
                            Main.economy.accounts.filterIsInstance<NormalBankAccount>()
                                .filter { it.isMemberOrOwner(sender) }.forEach { res.add(it.name) }
                            res.add("default")
                        }
                        "info" -> {
                            Main.economy.accounts.filterIsInstance<NormalBankAccount>().forEach { res.add(it.name) }
                            res.add("default")
                        }
                        "delete","invite" -> Main.economy.accounts.filterIsInstance<NormalBankAccount>()
                            .filter { it.isOwner(sender) }.forEach { res.add(it.name) }
                        "leave" -> Main.economy.accounts.filterIsInstance<NormalBankAccount>()
                            .filter { it.isMember(sender) }.forEach { res.add(it.name) }
                        "join" -> Main.economy.accounts.filterIsInstance<NormalBankAccount>()
                            .filter { it.isInvited(sender) }.forEach { res.add(it.name) }
                    }
                    return res.filter { it.startsWith(args[1]) }.toMutableList()
                }
                3 -> {
                    var res = mutableListOf<String>()
                    Bukkit.getOnlinePlayers().forEach { res.add(it.name) }
                    res.filter { it.startsWith(args[2]) }.toMutableList()
                }
                else -> mutableListOf()
            }
        }
        return mutableListOf()
    }
}