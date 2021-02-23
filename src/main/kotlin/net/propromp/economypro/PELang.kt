package net.propromp.economypro

import net.propromp.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import java.io.File

class PELang(plugin:Main) {
    private var langFiles = HashMap<String,FileConfiguration>()

    init {
        langFiles["en_US"]=CustomConfig(plugin,"lang/en_US.yml").let{it.saveDefaultConfig();it.config}
        langFiles["ja_JP"]=CustomConfig(plugin,"lang/ja_JP.yml").let{it.saveDefaultConfig();it.config}
    }
    fun get(player:Player,key:String):String{
        var config:FileConfiguration = langFiles["en_US"]!!
        langFiles[player.locale]?.let{
            config=it
        }
        return config.getString(key)!!.replace("&","§")
    }
}