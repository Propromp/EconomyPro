package net.propromp.economypro

import net.propromp.economypro.api.BankAccount
import net.propromp.economypro.api.NormalBankAccount
import net.propromp.economypro.api.PlayerBankAccount
import net.propromp.economypro.api.ProEconomy
import net.propromp.util.CustomConfig
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.FileConfiguration
import java.security.InvalidParameterException
import java.util.*

class BankDataLoader(plugin: Main, private var economy: ProEconomy) {
    private var customConfig = CustomConfig(plugin, "banks.yml")
    private var config: FileConfiguration

    init {
        customConfig.saveDefaultConfig()
        config = customConfig.config
    }

    fun loadAll() {
        config.getKeys(false).forEach {
            economy.accounts.add(load(it))
        }
    }

    private fun load(key: String): BankAccount {
        if (config.contains(key)) {
            return when (config.getString("$key.type")) {
                "normal" -> {
                    val bank =
                        NormalBankAccount(config.getString("$key.name")!!,
                            Bukkit.getOfflinePlayer(UUID.fromString(config.getString("$key.owner"))))
                    val players = mutableListOf<UUID>()
                    config.getStringList("$key.members").forEach {
                        players.add(UUID.fromString(it))
                    }
                    config.getStringList("$key.selectedby").forEach {
                        economy.selectAccount(Bukkit.getOfflinePlayer(UUID.fromString(it)), bank)
                    }
                    bank.members = players
                    bank.balance = config.getDouble("$key.balance")
                    bank
                }
                "player" -> {
                    val bank =
                        PlayerBankAccount(Bukkit.getOfflinePlayer(UUID.fromString(config.getString("$key.player"))))
                    config.getConfigurationSection("$key.worldbalance")?.getKeys(false)?.forEach {
                        bank.worldBalance[Bukkit.getWorld(it)!!] = config.getDouble("$key.worldbalance.$it")
                    }
                    config.getStringList("$key.selectedby").forEach {
                        economy.selectAccount(Bukkit.getOfflinePlayer(UUID.fromString(it)), bank)
                    }
                    bank.normalBalance = config.getDouble("$key.normalbalance")
                    bank
                }
                else -> throw InvalidParameterException("could not load bank($key).")
            }
        } else {
            throw ArrayIndexOutOfBoundsException("could not load bank($key).")
        }
    }

    fun saveAll() {
        var i = 0
        economy.accounts.forEach {
            if (it is PlayerBankAccount) {
                config.set("$i.type", "player")
                config.set("$i.player", it.uuid.toString())
                it.worldBalance.forEach { entry ->
                    config.set("$i.worldbalance.${entry.key.name}", entry.value)
                }
                val selectedList = mutableListOf<String>()
                economy.selectedAccounts.entries.forEach { entry ->
                    if (entry.value == it) {
                        selectedList.add(entry.key.toString())
                    }
                }
                config.set("$i.selectedby", selectedList)
                config.set("$i.normalbalance", it.normalBalance)
            }
            if (it is NormalBankAccount) {
                config.set("$i.type", "normal")
                config.set("$i.name", it.name)
                config.set("$i.owner", it.ownerUUID.toString())
                val res = mutableListOf<String>()
                it.members.forEach { e -> res.add(e.toString()) }
                val selectedList = mutableListOf<String>()
                economy.selectedAccounts.entries.forEach { entry ->
                    if (entry.value == it) {
                        selectedList.add(entry.key.toString())
                    }
                }
                config.set("$i.selectedby", selectedList)
                config.set("$i.members", res)
                config.set("$i.balance", it.balance)
            }
            i++
        }
        customConfig.saveConfig()
    }
}