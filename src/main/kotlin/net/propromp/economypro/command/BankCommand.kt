package net.propromp.economypro.command

import dev.jorel.commandapi.CommandAPI
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import net.propromp.economypro.Main
import net.propromp.economypro.api.BankAccount
import net.propromp.economypro.api.NormalBankAccount
import net.propromp.economypro.api.PlayerBankAccount
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

class BankCommand {
    fun help(sender: CommandSender) {
        sender.sendMessage(
            "$dgray----------[$red EconomyProHELP$dgray -$white /bank $dgray]----------\n" +
                    "$gold/bank help$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.bank.help.help"
                        )
                    }\n" +
                    "$gold/bank select [bankName]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.bank.help.select"
                        )
                    }\n" +
                    "$gold/bank info [bankName]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.bank.help.info"
                        )
                    }\n" +
                    "$gold/bank delete [bankName]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.bank.help.delete"
                        )
                    }\n" +
                    "$gold/bank create$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.bank.help.create"
                        )
                    }\n" +
                    "$gold/bank invite [bankName] [player]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.bank.help.invite"
                        )
                    }\n" +
                    "$gold/bank join [bankName]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.bank.help.join"
                        )
                    }\n" +
                    "$gold/bank leave [bankName]$dgray»$white ${
                        Main.lang.get(
                            sender,
                            "command.bank.help.leave"
                        )
                    }\n" +
                    "$dgray---------------------------------------------"
        )
    }

    fun select(sender: Player, account: BankAccount) {
        if (account.canSelect(sender)) {
            Main.economy.selectedAccounts[sender.uniqueId] = account
            sender.sendMessage(
                "${aqua}${
                    Main.lang.get(sender, "command.bank.select.2").replace("%val1%", account.name)
                }"
            )
        } else {
            CommandAPI.fail("${dred}${Main.lang.get(sender, "command.not_owner_or_member")}")
        }
    }

    fun info(sender: CommandSender, account: BankAccount) {
        sender.sendMessage(
            "$dgray[$red ${
                Main.lang.get(sender, "command.bank.info.title")
            }$dgray - $white${account.name} $dgray]"
        )
        sender.sendMessage("   ${gold}${Main.lang.get(sender, "word.name")}$dgray»$white ${account.name}\n")
        sender.sendMessage("   ${gold}${Main.lang.get(sender, "word.balance")}$dgray»$white ${account.balance}\n")
        if (account is PlayerBankAccount) {
            sender.sendMessage(
                "   ${gold}${Main.lang.get(sender, "word.owner")}$dgray»$white ${
                    Bukkit.getOfflinePlayer(
                        account.uuid
                    ).name
                }\n"
            )
        } else if (account is NormalBankAccount) {
            sender.sendMessage(
                "   ${gold}${Main.lang.get(sender, "word.owner")}$dgray»$white ${
                    Bukkit.getOfflinePlayer(
                        account.ownerUUID
                    ).name
                }"
            )
            sender.sendMessage("   ${gold}${
                Main.lang.get(
                    sender,
                    "word.members"
                )
            }$dgray»$white ${account.members.map{Bukkit.getOfflinePlayer(it).name}}")
        }
    }

    fun delete(sender: Player, account: BankAccount){
        if(account is NormalBankAccount){
            if(account.isOwner(sender)){
                Main.economy.deleteAccount(account)
                sender.sendMessage("${aqua}${Main.lang.get(sender, "command.bank.delete.2")}")
            } else {
                CommandAPI.fail("${dred}${Main.lang.get(sender, "command.not_owner")}")
            }
        } else if(account is PlayerBankAccount){
            if(account.uuid==sender.uniqueId){
                Main.economy.deleteAccount(account)
                sender.sendMessage("${aqua}${Main.lang.get(sender, "command.bank.delete.2")}")
            } else {
                CommandAPI.fail("${dred}${Main.lang.get(sender, "command.not_owner")}")
            }
        } else {
            Main.economy.deleteAccount(account)
            sender.sendMessage("${aqua}${Main.lang.get(sender, "command.bank.delete.2")}")
        }
    }

    fun create(sender:Player,accountName:String){
        if (accountName == "default") {
            sender.sendMessage("${dred}${Main.lang.get(sender, "command.bank.create.1")}")
        } else {
            if (!Main.economy.hasAccount(accountName)) {
                Main.economy.createAccount(accountName, sender)
                sender.sendMessage(
                    "${aqua}${
                        Main.lang.get(sender, "command.bank.create.2").replace("%val1%", accountName)
                    }"
                )
            } else {
                CommandAPI.fail("${dred}${Main.lang.get(sender, "command.bank_already_exists")}")
            }
        }

    }

    fun invite(sender: Player,account:NormalBankAccount,target:Player){
        if(account.isMemberOrOwner(target)){
            CommandAPI.fail(red.toString() + Main.lang.get(
                sender,
                "command.bank.invite.already_member"
            ))
        } else {
            account.addInvited(target)
            sender.sendMessage(
                Main.lang.get(sender, "command.bank.invite.1")
                    .replace("%val1%", target.name)
                    .replace("%val2%", account.name)
            )
            target.sendMessage(
                Main.lang.get(sender, "command.bank.invite.2")
                    .replace("%val1%", account.name)
            )
            val text = TextComponent("   ")
            val join = TextComponent(Main.lang.get(sender, "command.bank.invite.3"))
            join.clickEvent =
                ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bank join ${account.name}")
            text.addExtra(join)
            target.sendMessage(text)
        }
    }

    fun leave(sender: Player,account:NormalBankAccount){
        when {
            account.isMember(sender) -> {
                account.members.remove(sender)
                sender.sendMessage(
                    "${aqua}${
                        Main.lang.get(sender, "command.bank.leave.1").replace("%val1%", account.name)
                    }"
                )
            }
            account.isOwner(sender) -> {
                CommandAPI.fail("${dred}${Main.lang.get(sender, "command.bank.leave.2")}")
            }
            else -> {
                CommandAPI.fail("${dred}${Main.lang.get(sender, "command.not_member")}")
            }
        }
    }

    fun join(sender: Player,account: NormalBankAccount){
        if (account.isInvited(sender)) {
            account.members.add(sender.uniqueId)
            sender.sendMessage("${aqua}${Main.lang.get(sender, "command.bank.join.1")}")
        } else {
            CommandAPI.fail("${dred}${Main.lang.get(sender, "command.bank.join.2")}")
        }
    }
}