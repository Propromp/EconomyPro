package net.propromp.economypro.command

import dev.jorel.commandapi.CommandAPI
import net.propromp.economypro.Main
import net.propromp.economypro.api.BankAccount
import net.propromp.economypro.api.NormalBankAccount
import net.propromp.economypro.api.PlayerBankAccount
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player

class PayCommand {
    fun run(sender: Player, targetAccount: BankAccount, amount: Double) {
        val senderAccount = Main.economy.getSelectedAccount(sender)
        val target: Player? = if (targetAccount is NormalBankAccount) {
            Bukkit.getPlayer(targetAccount.ownerUUID)
        } else if (targetAccount is PlayerBankAccount) {
            Bukkit.getPlayer(targetAccount.uuid)
        } else {
            null
        }
        if (senderAccount.has(amount)) {
            senderAccount.withdraw(amount)
            targetAccount.deposit(amount)
            sender.sendMessage(
                "${ChatColor.AQUA}${
                    Main.lang.get(sender, "command.pay.paid").replace("%val1%", amount.toString() + Main.economy.plural)
                        .replace("%val2%", targetAccount.name)
                }"
            )
            target?.sendMessage(
                "${ChatColor.AQUA}${
                    Main.lang.get(sender, "command.pay.received")
                        .replace("%val1%", amount.toString() + Main.economy.plural).replace("%val2%", target.name)
                }"
            )
            sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f)
            target?.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f)
            return
        } else {
            CommandAPI.fail("${ChatColor.DARK_RED}${Main.lang.get(sender, "command.pay.not_enough_balance")}")
            return
        }
    }
}