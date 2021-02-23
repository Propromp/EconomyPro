package net.propromp.economypro

import net.milkbowl.vault.economy.Economy
import net.propromp.economypro.api.ProEconomy
import net.propromp.economypro.api.VaultEconomy
import net.propromp.economypro.command.BalanceCommand
import net.propromp.economypro.command.BankCommand
import net.propromp.economypro.command.MoneyCommand
import net.propromp.economypro.command.PayCommand
import net.propromp.economypro.listener.EPPlayerEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.RegisteredServiceProvider
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin


class Main : JavaPlugin() {
    companion object {
        lateinit var instance: Main
        lateinit var economy: ProEconomy
        lateinit var bankDataLoader: BankDataLoader
    }
    override fun onEnable() {
        logger.info(" ${ChatColor.GREEN}┏━━━━━       ${ChatColor.AQUA}┏━━━━━┓")
        logger.info(" ${ChatColor.GREEN}┃            ${ChatColor.AQUA}┃     ┃")
        logger.info(" ${ChatColor.GREEN}┣━━━━━       ${ChatColor.AQUA}┣━━━━━┛")
        logger.info(" ${ChatColor.GREEN}┃            ${ChatColor.AQUA}┃")
        logger.info(" ${ChatColor.GREEN}┗━━━━━${ChatColor.WHITE}conomy ${ChatColor.AQUA}╹      ${ChatColor.WHITE}ro")
        logger.info("Copyright (c) 2021 ${ChatColor.AQUA}Propromp")
        logger.info("${ChatColor.BOLD}This software is released under MIT license.")

        instance = this

        logger.info("loading economy system...")
        economy = ProEconomy(config.getString("plural")!!, config.getString("singular")!!)
        logger.info("complete.")

        if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            logger.info("Vault has been detected. Initializing vault economy support ...")
            saveDefaultConfig()
            server.servicesManager.register(Economy::class.java, VaultEconomy(economy), this, ServicePriority.Normal)
            if (config.getBoolean("disable-essentials")) {
                val econs: Collection<RegisteredServiceProvider<Economy?>> =
                    Bukkit.getPluginManager().getPlugin("Vault")!!.server.servicesManager.getRegistrations(
                        Economy::class.java
                    )
                for (econ in econs) {
                    if (econ.provider.name.equals("Essentials Economy")) {
                        server.servicesManager.unregister(econ.provider)
                    }
                }
            }
            logger.info("complete.")
        }

        logger.info("loading banks...")
        bankDataLoader= BankDataLoader(this, economy,false)
        bankDataLoader.loadAll()
        logger.info("complete.")

        logger.info("loading commands...")
        getCommand("money")?.setExecutor(MoneyCommand())
        getCommand("bank")?.setExecutor(BankCommand())
        getCommand("bank")?.tabCompleter = BankCommand()
        getCommand("balance")?.setExecutor(BalanceCommand())
        getCommand("balance")?.tabCompleter = BalanceCommand()
        getCommand("pay")?.setExecutor(PayCommand())
        getCommand("pay")?.tabCompleter = PayCommand()
        logger.info("complete.")

        logger.info("loading listener listeners...")
        EPPlayerEvent(this)
        logger.info("complete.")
    }

    override fun onDisable() {
        logger.info("saving banks...")
        bankDataLoader.saveAll()
        logger.info("complete.")

        logger.info("bye")
    }
}