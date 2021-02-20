package net.propromp.economypro

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
	override fun onEnable() {
		logger.info(" ┏━━━━━       ┏━━━━┓")
		logger.info(" ┃            ┃    ┃")
		logger.info(" ┣━━━━━       ┣━━━━┛")
		logger.info(" ┃            ┃")
		logger.info(" ┗━━━━━conomy ┃ro")
	}
	override fun onDisable() {
		//停止処理
	}
}