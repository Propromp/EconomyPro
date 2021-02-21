package net.propromp.economypro

import net.propromp.economypro.api.Bank
import net.propromp.economypro.api.ProEconomy
import net.propromp.util.CustomConfig
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.configuration.file.FileConfiguration
import java.util.*

/**
 *
 */
class BankDataLoader(val plugin:Main,var economy: ProEconomy,val mySQL:Boolean) {
    var customConfig = CustomConfig(plugin, "banks.yml")
    private var config:FileConfiguration
    init {
        customConfig.saveDefaultConfig()
        config=customConfig.config
    }
    fun loadAll():ArrayList<Bank> {
        if(mySQL) {//mysql
            TODO()
        } else {//yml
            var res = arrayListOf<Bank>()
            config.getKeys(false).forEach {
                res.add(load(it))
            }
            return res
        }
    }
    fun load(key:String):Bank{
        if(mySQL){//mysql
            TODO()
        } else {//yml
            if(config.contains(key)){
                var bank = Bank(config.getString("$key.name")!!,config.getOfflinePlayer("$key.owner")!!)
                var players = mutableListOf<OfflinePlayer>()
                config.getStringList("$key.members").forEach{
                    players.add(Bukkit.getOfflinePlayer(UUID.fromString(it)))
                }
                bank.members=players
                economy.banks.add(bank)
                return bank
            } else {
                throw ArrayIndexOutOfBoundsException("could not load bank($key).")
            }
        }
    }
    fun saveAll(){
        if(mySQL){
            TODO()
        } else {
            var i = 0
            economy.banks.forEach {
                it?.let {
                    config.set("$i.name",it.name)
                    config.set("$i.owner",it.owner)
                    var uuids = mutableListOf<String>()
                    it.members.forEach{
                        uuids.add(it.uniqueId.toString())
                    }
                    config.set("$i.members",uuids)
                }
                i++
            }
            customConfig.saveConfig()
        }
    }
}