package net.propromp.economypro.command

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.DoubleArgument
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.CommandExecutor
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import dev.jorel.commandapi.executors.ResultingCommandExecutor
import net.propromp.economypro.Main
import net.propromp.economypro.api.BankAccount
import net.propromp.economypro.api.NormalBankAccount
import org.bukkit.entity.Player
import java.util.*


class EconomyProCommands(plugin: Main) {
    val economy = Main.economy

    init {
        /* /economypro */
        var hookVault = CommandAPICommand("hook")
            .withPermission("economypro.economypro.settings")
            .executes(CommandExecutor { sender, args ->
                EconomyproCommand().hookVault(sender)
            })
        var unhookVault = CommandAPICommand("unhook")
            .withPermission("economypro.economypro.settings")
            .executes(CommandExecutor { sender, args ->
                EconomyproCommand().unhookVault(sender)
            })
        var vault = CommandAPICommand("vault")
            .withPermission("economypro.economypro.settings")
            .withSubcommand(hookVault)
            .withSubcommand(unhookVault)
            .executes(CommandExecutor { sender, args ->
                EconomyproCommand().getVault(sender)
            })
        var hookEnterprise = CommandAPICommand("hook")
            .withPermission("economypro.economypro.settings")
            .executes(CommandExecutor { sender, args ->
                EconomyproCommand().hookEnterprise(sender)
            })
        var unhookEnterprise = CommandAPICommand("unhook")
            .withPermission("economypro.economypro.settings")
            .executes(CommandExecutor { sender, args ->
                EconomyproCommand().unhookEnterprise(sender)
            })
        var enterprise = CommandAPICommand("enterprise")
            .withPermission("economypro.economypro.settings")
            .withSubcommand(hookEnterprise)
            .withSubcommand(unhookEnterprise)
            .executes(CommandExecutor { sender, args ->
                EconomyproCommand().getEnterprise(sender)
            })
        CommandAPICommand("economypro")
            .withSubcommand(enterprise)
            .withSubcommand(vault)
            .withPermission("economypro.economypro.help")
            .executes(CommandExecutor { sender, args ->
                EconomyproCommand().run(sender)
            })
            .register()

        /* /money */
        CommandAPICommand("money")
            .executes(ResultingCommandExecutor { sender, _ ->
                return@ResultingCommandExecutor MoneyCommand().run(sender)
            }).register()

        /* /balance */
        var balanceHelp = CommandAPICommand("help")
            .withPermission("economypro.balance.set")
            .executes(CommandExecutor { sender, args ->
                return@CommandExecutor BalanceCommand().help(sender)
            })
        var balanceSet = CommandAPICommand("set")
            .withPermission("economypro.balance.set")
            .withArguments(CommandUtil.getBankAccountsArgument("account"))
            .withArguments(DoubleArgument("amount"))
            .executes(CommandExecutor { sender, args ->
                BalanceCommand().set(sender, args[0] as BankAccount, args[1] as Double)
            })
        var balanceDeposit = CommandAPICommand("deposit")
            .withPermission("economypro.balance.set")
            .withArguments(CommandUtil.getBankAccountsArgument("account"))
            .withArguments(DoubleArgument("amount"))
            .executes(CommandExecutor { sender, args ->
                BalanceCommand().deposit(sender, args[0] as BankAccount, args[1] as Double)
            })
        var balanceWithdraw = CommandAPICommand("withdraw")
            .withPermission("economypro.balance.set")
            .withArguments(CommandUtil.getBankAccountsArgument("account"))
            .withArguments(DoubleArgument("amount"))
            .executes(CommandExecutor { sender, args ->
                BalanceCommand().withdraw(sender, args[0] as BankAccount, args[1] as Double)
            })
        CommandAPICommand("balance")
            .withSubcommand(balanceHelp)
            .withSubcommand(balanceSet)
            .withSubcommand(balanceDeposit)
            .withSubcommand(balanceWithdraw)
            .withArguments(CommandUtil.getBankAccountsArgument("bank"))
            .withPermission("economypro.balance.get")
            .executes(ResultingCommandExecutor { sender, args ->
                val account = args[0] as BankAccount
                BalanceCommand().get(sender, account)
            })
            .register()

        /* /balancetop */
        CommandAPICommand("balancetop")
            .withPermission("economypro.balancetop")
            .executes(CommandExecutor { sender, args ->
                BalancetopCommand().run(sender)
            }).register()

        /* /pay */
        CommandAPICommand("pay")
            .withArguments(CommandUtil.getBankAccountsArgument("account"), DoubleArgument("amount"))
            .withPermission("economypro.pay")
            .executesPlayer(PlayerCommandExecutor { sender, args ->
                PayCommand().run(sender, args[0] as BankAccount, args[1] as Double)
            })
            .register()

        /* /bank */
        var bankHelp=CommandAPICommand("help")
            .withPermission("economypro.bank")
            .executes(CommandExecutor { sender, args ->
                BankCommand().help(sender)
            })
        var bankSelect = CommandAPICommand("select")
            .withPermission("economypro.bank")
            .withArguments(CommandUtil.getBankAccountsArgument("account"))
            .executesPlayer( PlayerCommandExecutor{ sender, args ->
                BankCommand().select(sender,args[0] as BankAccount)
            })
        var bankInfo = CommandAPICommand("info")
            .withPermission("economypro.bank")
            .withArguments(CommandUtil.getBankAccountsArgument("account"))
            .executes(CommandExecutor { sender, args ->
                BankCommand().info(sender,args[0] as BankAccount)
            })
        var bankDelete = CommandAPICommand("delete")
            .withPermission("economypro.bank")
            .withArguments(CommandUtil.getBankAccountsArgument("account"))
            .executesPlayer(PlayerCommandExecutor{sender,args->
                BankCommand().delete(sender,args[0] as BankAccount)
            })
        var bankCreate = CommandAPICommand("create")
            .withPermission("economypro.bank")
            .withArguments(StringArgument("accountName"))
            .executesPlayer(PlayerCommandExecutor { sender, args ->
                BankCommand().create(sender,args[0] as String)
            })
        var bankInvite = CommandAPICommand("invite")
            .withPermission("economypro.bank")
            .withArguments(CommandUtil.getNormalBankAccountsArgument("account"),PlayerArgument("target"))
            .executesPlayer(
                PlayerCommandExecutor { sender, args ->
                    BankCommand().invite(sender,args[0] as NormalBankAccount,args[1] as Player)
                }
            )
        var bankLeave = CommandAPICommand("leave")
            .withPermission("economypro.bank")
            .withArguments(CommandUtil.getNormalBankAccountsArgument("account"))
            .executesPlayer(PlayerCommandExecutor{sender,args->
                BankCommand().leave(sender,args[0] as NormalBankAccount)
            })
        var bankJoin = CommandAPICommand("join")
            .withPermission("economypro.bank")
            .withArguments(CommandUtil.getNormalBankAccountsArgument("account"))
            .executesPlayer(PlayerCommandExecutor { sender, args ->
                BankCommand().join(sender,args[0] as NormalBankAccount)
            })
        CommandAPICommand("bank")
            .withSubcommand(bankHelp)
            .withSubcommand(bankSelect)
            .withSubcommand(bankInfo)
            .withSubcommand(bankDelete)
            .withSubcommand(bankCreate)
            .withSubcommand(bankInvite)
            .withSubcommand(bankLeave)
            .withSubcommand(bankJoin)
            .register()
    }
}