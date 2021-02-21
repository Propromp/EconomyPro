package net.propromp.economypro.api

import org.bukkit.OfflinePlayer
import org.bukkit.World

class Bank(val name: String, var owner: OfflinePlayer) {
    var members = mutableListOf<OfflinePlayer>()
    var world: World? = null
    var balance = 0.0

    constructor(name: String, owner: OfflinePlayer, world: World) : this(name, owner) {
        this.world = world
    }
}