package net.propromp.economypro

import com.github.sanctum.economy.construct.implement.AdvancedEconomy
import net.milkbowl.vault.economy.Economy
import net.propromp.economypro.api.ProEconomy
import net.propromp.economypro.enterprise.EnterpriseEconomy
import net.propromp.economypro.vault.VaultEconomy
import org.bukkit.Bukkit
import org.bukkit.plugin.ServicePriority


class EconomyHook(val plugin: Main, val economy: ProEconomy) {
    private var enterpriseProvider = EnterpriseEconomy(plugin, economy)
    private var vaultProvider = VaultEconomy(economy)
    var isEnterpriseHooked = false
    var isVaultHooked = false
    fun hookEnterprise() {
        Bukkit.getServicesManager().register(AdvancedEconomy::class.java, enterpriseProvider,
            plugin, ServicePriority.Normal)
        isEnterpriseHooked = true
        plugin.logger.info("- Advanced economy hooked! Now registered as a provider")
    }

    fun unhookEnterprise() {
        Bukkit.getServicesManager().unregister(AdvancedEconomy::class.java, enterpriseProvider)
        isEnterpriseHooked = false
        plugin.logger.info("- Advanced economy un-hooked! No longer registered as a provider")
    }

    fun hookVault() {
        Bukkit.getServicesManager().register(Economy::class.java, vaultProvider, plugin, ServicePriority.Normal)
        isVaultHooked = true
        plugin.logger.info("- Vault economy hooked! Now registered as a provider")
    }

    fun unhookVault() {
        Bukkit.getServicesManager().unregister(Economy::class.java, vaultProvider)
        isVaultHooked = false
        plugin.logger.info("- Vault economy un-hooked! No longer registered as a provider")
    }
}