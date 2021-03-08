package net.propromp.economypro.enterprise

import com.github.sanctum.economy.construct.entity.PlayerEconomyEntityBase
import org.bukkit.OfflinePlayer

class PlayerEconomyEntity(val offlinePlayer: OfflinePlayer) : PlayerEconomyEntityBase(offlinePlayer) {
    /**
     * Get a string which uniquely identifies this entity.
     *
     * Uses the following format:
     *
     * 'type'='identity'
     *
     *
     * Sample implementations:
     *
     * (Persistent Player/OfflinePlayer) UniqueId-based player id: 'p_uid=uuid'
     *
     * (Temporary Player) Session-based player id: 'p_name=PlayerName'
     *
     * (NPCs) NamespacedKey-based npc id: 'npc=plugin:string-key'
     * @return String which is unique to this entity
     */
    override fun id(): String {
        return offlinePlayer.uniqueId.toString()
    }
}