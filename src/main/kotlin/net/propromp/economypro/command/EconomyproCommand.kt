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

class EconomyproCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            if (sender.hasPermission("economypro.economypro.help")) {
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
                return true
            } else {
                sender.sendMessage(Main.lang.get(sender, "command.unknown_command"))
                return true
            }
        }

        if (sender.hasPermission("economypro.economypro.settings")) {
            when (args[0]) {
                "help" -> {
                    sender.sendMessage(
                        "$dgray----------[$red EconomyProHELP$dgray -$white /economypro $dgray]----------\n" +
                                "$gold/economypro enterprise [hook/unhook]$dgray»$white ${
                                    Main.lang.get(sender,
                                        "command.economypro.help.enterprise")
                                }\n" +
                                "$gold/economypro vault [hook/unhook]$dgray»$white ${
                                    Main.lang.get(sender,
                                        "command.economypro.help.vault")
                                }\n" +
                                "$dgray---------------------------------------------"
                    )
                    return true
                }
                "enterprise" -> {
                    if (args.size == 1) {
                        if (Main.instance.hook.isEnterpriseHooked) {//hooked
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.true"))
                        } else {//not hooked
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.false"))
                        }
                    }
                    when (args[1]) {
                        "hook" -> {
                            Main.instance.hook.hookEnterprise()
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.hook"))
                        }
                        "unhook" -> {
                            Main.instance.hook.unhookEnterprise()
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.unhook"))
                        }
                        else -> {
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.enterprise.else"))
                        }
                    }
                    return true
                }
                "vault" -> {
                    if (args.size == 1) {
                        if (Main.instance.hook.isVaultHooked) {//hooked
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.true"))
                        } else {//not hooked
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.false"))
                        }
                    }
                    when (args[1]) {
                        "hook" -> {
                            Main.instance.hook.hookVault()
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.hook"))
                        }
                        "unhook" -> {
                            Main.instance.hook.unhookVault()
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.unhook"))
                        }
                        else -> {
                            sender.sendMessage(Main.lang.get(sender, "command.economypro.vault.else"))
                        }
                    }
                    return true
                }
            }
        } else {
            sender.sendMessage(Main.lang.get(sender, "command.unknown_command"))
            return true
        }

        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>,
    ): MutableList<String> {
        return when (args.size) {
            1 -> {
                listOf("help", "enterprise", "vault").filter { it.startsWith(args[0]) }.toMutableList()
            }
            2 -> {
                listOf("hook", "unhook").filter { it.startsWith(args[1]) }.toMutableList()
            }
            else -> mutableListOf()
        }
    }
}