package net.propromp.economypro

import net.propromp.util.CustomConfig
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player

class PELang(plugin: Main) {
    private var langFiles = HashMap<String, FileConfiguration>()

    init {
        langFiles["en_us"] = CustomConfig(plugin, "lang/en_US.yml").let { it.saveDefaultConfig();it.config }
        langFiles["ja_jp"] = CustomConfig(plugin, "lang/ja_JP.yml").let { it.saveDefaultConfig();it.config }
    }

    fun get(sender: CommandSender, key: String): String {
        var config: FileConfiguration = langFiles["en_us"]!!
        if (sender is Player) {
            langFiles[sender.locale]?.let {
                config = it
            }
        }
        return config.getString(key)!!.replace("&", "§")

    }
}