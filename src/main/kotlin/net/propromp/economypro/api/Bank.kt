package net.propromp.economypro.api

import org.bukkit.OfflinePlayer
import org.bukkit.World
import java.util.*

class Bank(val name: String, var owner: OfflinePlayer) {
    var members = mutableListOf<OfflinePlayer>()
    val uuid = UUID.randomUUID().toString()
    var world: World? = null
    var balance = 0.0

    constructor(name: String, owner: OfflinePlayer, world: World) : this(name, owner) {
        this.world = world
    }
}