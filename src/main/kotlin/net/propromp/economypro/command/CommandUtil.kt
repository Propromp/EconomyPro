package net.propromp.economypro.command

import dev.jorel.commandapi.arguments.Argument
import org.bukkit.Bukkit

import dev.jorel.commandapi.arguments.CustomArgument
import dev.jorel.commandapi.arguments.CustomArgument.*
import net.propromp.economypro.Main
import net.propromp.economypro.api.NormalBankAccount
import net.propromp.economypro.api.PlayerBankAccount
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class CommandUtil {
    companion object {
        fun getBankAccountsArgument(nodeName:String): Argument? {
            return CustomArgument(nodeName, label@ CustomArgumentParser { input: String ->
                val account = Main.economy.getAccount(input)
                if (account == null) {
                    throw CustomArgumentException(MessageBuilder("the account doesn't exists: ").appendArgInput())
                } else {
                    return@CustomArgumentParser account
                }
            }).overrideSuggestions { sender: CommandSender? ->
                Main.economy.accounts.map{it.name}.toTypedArray()
            }
        }
        fun getNormalBankAccountsArgument(nodeName:String): Argument? {
            return CustomArgument(nodeName, label@ CustomArgumentParser { input: String ->
                val account = Main.economy.getNormalAccount(input)
                if (account == null) {
                    throw CustomArgumentException(MessageBuilder("the account doesn't exists: ").appendArgInput())
                } else {
                    return@CustomArgumentParser account
                }
            }).overrideSuggestions { sender: CommandSender? ->
                Main.economy.accounts.filterIsInstance<NormalBankAccount>().map{it.name}.toTypedArray()
            }
        }
        fun getSelectableBankAccountsArgument(nodeName: String):Argument?{
            return CustomArgument(nodeName, label@ CustomArgumentParser { input: String ->
                val account = Main.economy.getAccount(input)
                if (account == null) {
                    throw CustomArgumentException(MessageBuilder("the account doesn't exists: ").appendArgInput())
                } else {
                    return@CustomArgumentParser account
                }
            }).overrideSuggestions { sender: CommandSender? ->
                Main.economy.accounts.filter{
                    if(sender is Player) {
                        if (it is NormalBankAccount) {
                            return@filter it.isMemberOrOwner(sender)
                        } else if(it is PlayerBankAccount) {
                            return@filter it.uuid==sender.uniqueId
                        } else {
                            return@filter false
                        }
                    } else {
                        true
                    }
                }.map{it.name}.toTypedArray()
            }
        }
    }
}