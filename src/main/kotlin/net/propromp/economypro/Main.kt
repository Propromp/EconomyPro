package net.propromp.economypro

import dev.jorel.commandapi.CommandAPI
import net.propromp.economypro.api.ProEconomy
import net.propromp.economypro.command.*
import net.propromp.economypro.listener.EPPlayerEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin


class Main : JavaPlugin() {
    companion object {
        lateinit var instance: Main
        lateinit var economy: ProEconomy
        lateinit var bankDataLoader: BankDataLoader
        lateinit var lang: PELang
    }

    lateinit var hook: EconomyHook
    override fun onLoad() {
        CommandAPI.onLoad(false);
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
        lang = PELang(this)

        logger.info("loading economy system...")
        economy = ProEconomy(config.getString("plural")!!, config.getString("singular")!!)
        logger.info("complete.")

        hook = EconomyHook(this, economy)

        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            logger.info("Vault has been detected. hooking vault economy...")
            hook.hookVault()
            logger.info("complete.")
        }

        logger.info("hooking enterprise API...")
        hook.hookEnterprise()
        logger.info("complete.")

        logger.info("loading banks...")
        bankDataLoader = BankDataLoader(this, economy)
        bankDataLoader.loadAll()
        logger.info("complete.")

        logger.info("registering commands...")
        CommandAPI.onEnable(this);
        EconomyProCommands(this)
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