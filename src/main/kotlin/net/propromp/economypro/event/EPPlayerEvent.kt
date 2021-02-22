package net.propromp.economypro.event

import com.destroystokyo.paper.event.player.PlayerConnectionCloseEvent
import net.propromp.economypro.Main
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.persistence.PersistentDataType

class EPPlayerEvent(private val plugin:Main) : Listener{
    init{
        Bukkit.getPluginManager().registerEvents(this,plugin)
    }

    @EventHandler
    fun onPlayerJoin(e:PlayerJoinEvent){
        if(!Main.economy.hasDefaultAccount(e.player)){
            plugin.logger.info("${e.player.name} joined this server for first time. creating bank(default) for ${e.player.name}")
            Main.economy.createDefaultAccount(e.player)
            plugin.logger.info("completed.")
        }
    }
}