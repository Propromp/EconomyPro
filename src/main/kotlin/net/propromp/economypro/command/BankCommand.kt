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
        if (args.isEmpty()) {
            return false
        }
        if (sender is Player) {
            if (sender.hasPermission("economypro.bank")) {
                when (args[0]) {
                    "help" -> {
                        sender.sendMessage(
                            "$dgray----------[$red EconomyProHELP$dgray -$white /bank $dgray]----------\n" +
                                    "$gold/bank help$dgray»$white ${
                                        Main.lang.get(sender,
                                            "command.bank.help.help")
                                    }\n" +
                                    "$gold/bank select [bankName]$dgray»$white ${
                                        Main.lang.get(sender,
                                            "command.bank.help.select")
                                    }\n" +
                                    "$gold/bank info [bankName]$dgray»$white ${
                                        Main.lang.get(sender,
                                            "command.bank.help.info")
                                    }\n" +
                                    "$gold/bank delete [bankName]$dgray»$white ${
                                        Main.lang.get(sender,
                                            "command.bank.help.delete")
                                    }\n" +
                                    "$gold/bank create$dgray»$white ${
                                        Main.lang.get(sender,
                                            "command.bank.help.create")
                                    }\n" +
                                    "$gold/bank invite [bankName] [player]$dgray»$white ${
                                        Main.lang.get(sender,
                                            "command.bank.help.invite")
                                    }\n" +
                                    "$gold/bank join [bankName]$dgray»$white ${
                                        Main.lang.get(sender,
                                            "command.bank.help.join")
                                    }\n" +
                                    "$gold/bank leave [bankName]$dgray»$white ${
                                        Main.lang.get(sender,
                                            "command.bank.help.leave")
                                    }\n" +
                                    "$dgray---------------------------------------------"
                        )
                        return true
                    }
                    "select" -> {
                        if (args.size < 2) {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.not_enough_arguments")}")
                            return true
                        }
                        if (args[1] == "default") {
                            Main.economy.selectAccount(sender, Main.economy.getDefaultAccount(sender)!!)
                            sender.sendMessage("${aqua}${Main.lang.get(sender, "command.bank.select.1")}")
                            return true
                        }
                        return if (Main.economy.hasAccount(args[1])) {
                            if (Main.economy.getAccount(args[1])!!.isMemberOrOwner(sender)) {
                                Main.economy.selectedAccounts[sender.uniqueId] = Main.economy.getAccount(args[1])!!
                                sender.sendMessage("${aqua}${
                                    Main.lang.get(sender, "command.bank.select.2").replace("%val1%", args[1])
                                }")
                                true
                            } else {
                                sender.sendMessage("${dred}${Main.lang.get(sender, "command.not_owner_or_member")}")
                                true
                            }
                        } else {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank_not_found")}")
                            true
                        }
                    }
                    "info" -> {
                        if (args.size < 2) {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.not_enough_arguments")}")
                            return true
                        }
                        if (args[1] == "default") {
                            Main.economy.getDefaultAccount(sender)?.let {
                                sender.sendMessage(
                                    "$dgray[$red ${
                                        Main.lang.get(sender,
                                            "command.bank.info.title")
                                    }$dgray - $white${args[1]} $dgray]\n" +
                                            "   ${gold}${
                                                Main.lang.get(sender,
                                                    "word.name")
                                            }$dgray»$white ${it.name}\n" +
                                            "   ${gold}${
                                                Main.lang.get(sender,
                                                    "word.owner")
                                            }$dgray»$white ${Bukkit.getOfflinePlayer(it.uuid)}\n" +
                                            "   ${gold}${
                                                Main.lang.get(sender,
                                                    "word.balance")
                                            }$dgray»$white ${it.balance}\n"
                                )
                            }
                            return true
                        }
                        return if (Main.economy.hasAccount(args[1])) {
                            Main.economy.getAccount(args[1])?.let {
                                val members = mutableListOf<String>()
                                for (member in it.members) {
                                    members.add(Bukkit.getOfflinePlayer(member).name ?: member.toString())
                                }
                                sender.sendMessage(
                                    "$dgray[$red ${
                                        Main.lang.get(sender,
                                            "command.bank.info.title")
                                    }$dgray - $white${args[1]} $dgray]\n" +
                                            "   ${gold}${
                                                Main.lang.get(sender,
                                                    "word.name")
                                            }$dgray»$white ${it.name}\n" +
                                            "   ${gold}${
                                                Main.lang.get(sender,
                                                    "word.owner")
                                            }$dgray»$white ${Bukkit.getOfflinePlayer(it.ownerUUID).name}\n" +
                                            "   ${gold}${
                                                Main.lang.get(sender,
                                                    "word.members")
                                            }$dgray»$white ${members}\n" +
                                            "   ${gold}${
                                                Main.lang.get(sender,
                                                    "word.balance")
                                            }$dgray»$white ${it.balance}\n"
                                )
                            }
                            true
                        } else {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank_not_found")}")
                            true
                        }
                    }
                    "delete" -> {
                        if (args.size < 2) {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.not_enough_arguments")}")
                            return true
                        }
                        if (args[1] == "default") {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank.delete.1")}")
                            return true
                        }
                        if (Main.economy.hasAccount(args[1])) {
                            if (Main.economy.getAccount(args[1])!!.isMemberOrOwner(sender)) {
                                if (args.size > 2) {
                                    if (args[2] == "confirm") {
                                        Main.economy.deleteAccount(args[1])
                                        sender.sendMessage("${aqua}${Main.lang.get(sender, "command.bank.delete.2")}")
                                        return true
                                    }
                                } else {
                                    sender.sendMessage(Main.lang.get(sender, "command.bank.delete.3")
                                        .replace("%val1%", args[1]))
                                    sender.sendMessage("${dred}${
                                        Main.lang.get(sender, "command.bank.delete.4").replace("%val1%", args[1])
                                    }")
                                    val yes = TextComponent("   $dred${Main.lang.get(sender, "command.bank.delete.5")}")
                                    yes.clickEvent =
                                        ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bank delete ${args[1]} confirm")
                                    sender.sendMessage(yes)
                                }
                                return true
                            } else {
                                sender.sendMessage("${dred}${Main.lang.get(sender, "command.not_owner")}")
                                return true
                            }
                        } else {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank_not_found")}")
                            return true
                        }

                    }
                    "create" -> {
                        if (args.size < 2) {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "not_enough_arguments")}")
                            return true
                        }
                        if (args[1] == "default") {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank.create.1")}")
                            return true
                        }
                        return if (!Main.economy.hasAccount(args[1])) {
                            Main.economy.createAccount(args[1], sender)
                            sender.sendMessage("${aqua}${
                                Main.lang.get(sender, "command.bank.create.2").replace("%val1%", args[1])
                            }")
                            true
                        } else {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank_already_exists")}")
                            true
                        }
                    }
                    "invite" -> {
                        if (args.size < 3) {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "not_enough_arguments")}")
                            return true
                        }
                        Main.economy.getAccount(args[1])?.let { account ->
                            Bukkit.getPlayer(args[2])?.let { target ->
                                if(account.isMember(target)){
                                    sender.sendMessage(red.toString()+Main.lang.get(sender,"command.bank.invite.already_member"))
                                    return true
                                }
                                account.addInvited(target)
                                sender.sendMessage(Main.lang.get(sender, "command.bank.invite.1")
                                    .replace("%val1%", target.name)
                                    .replace("%val2%", account.name))
                                target.sendMessage(Main.lang.get(sender, "command.bank.invite.2")
                                    .replace("%val1%", account.name))
                                val text = TextComponent("   ")
                                val join = TextComponent(Main.lang.get(sender, "command.bank.invite.3"))
                                join.clickEvent =
                                    ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bank join ${account.name}")
                                text.addExtra(join)
                                target.sendMessage(text)
                                return true
                            }
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.player_not_found")}")
                            return true
                        }
                        sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank_not_found")}")
                        return true

                    }
                    "leave" -> {
                        if (args.size < 2) {
                            sender.sendMessage("${dred}${Main.lang.get(sender, "command.not_enough_arguments")}")
                            return true
                        }
                        Main.economy.getAccount(args[1])?.let { account ->
                            if (account.isMember(sender)) {
                                account.members.remove(sender)
                                sender.sendMessage("${aqua}${
                                    Main.lang.get(sender, "command.bank.leave.1").replace("%val1%", account.name)
                                }")
                            } else if (account.isOwner(sender)) {
                                sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank.leave.2")}")
                            } else {
                                sender.sendMessage("${dred}${Main.lang.get(sender, "command.not_member")}")
                            }
                            return true
                        }
                        sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank_not_found")}")
                        return true

                    }
                    "join" -> {
                        Main.economy.getAccount(args[1])?.let { account ->
                            if (account.isInvited(sender)) {
                                account.members.add(sender.uniqueId)
                                sender.sendMessage("${aqua}${Main.lang.get(sender, "command.bank.join.1")}")
                            } else {
                                sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank.join.2")}")
                            }
                            return true
                        }
                    }
                    else -> return false
                }
            } else {
                sender.sendMessage(Main.lang.get(sender, "command.unknown_command"))
                return true
            }
        } else {
            sender.sendMessage("${dred}This command can only be executed from player")
            return true
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>,
    ): MutableList<String> {
        if (sender is Player) {
            return when (args.size) {
                1 -> mutableListOf(
                    "help",
                    "select",
                    "info",
                    "delete",
                    "create",
                    "invite",
                    "leave",
                    "join"
                ).filter { it.startsWith(args[0]) }
                    .toMutableList()
                2 -> {
                    val res = mutableListOf<String>()
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
                        "delete", "invite" -> Main.economy.accounts.filterIsInstance<NormalBankAccount>()
                            .filter { it.isOwner(sender) }.forEach { res.add(it.name) }
                        "leave" -> Main.economy.accounts.filterIsInstance<NormalBankAccount>()
                            .filter { it.isMember(sender) }.forEach { res.add(it.name) }
                        "join" -> Main.economy.accounts.filterIsInstance<NormalBankAccount>()
                            .filter { it.isInvited(sender) }.forEach { res.add(it.name) }
                    }
                    return res.filter { it.startsWith(args[1]) }.toMutableList()
                }
                3 -> {
                    val res = mutableListOf<String>()
                    Bukkit.getOnlinePlayers().forEach { res.add(it.name) }
                    res.filter { it.startsWith(args[2]) }.toMutableList()
                }
                else -> mutableListOf()
            }
        }
        return mutableListOf()
    }
}