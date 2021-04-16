package net.propromp.economypro.command

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType.integer
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.CommandNode
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender


class EconomyProCommands {
    private val dispatcher:CommandDispatcher<BukkitBrigadierCommandSource> = CommandDispatcher<BukkitBrigadierCommandSource>()
    init {

        dispatcher.register(/* /money */
            literal<BukkitBrigadierCommandSource>("money").executes { c: CommandContext<BukkitBrigadierCommandSource> ->
                MoneyCommand().run(c.source.bukkitSender)
            }
        )
    }
}