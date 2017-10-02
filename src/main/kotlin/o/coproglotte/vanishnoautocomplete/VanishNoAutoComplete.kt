package o.coproglotte.vanishnoautocomplete

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.events.PacketListener
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.kitteh.vanish.VanishManager
import org.kitteh.vanish.VanishPlugin

class VanishNoAutoComplete : JavaPlugin() {

    lateinit private var vanishManager: VanishManager
    lateinit private var serverTabCompleteListener: PacketListener

    override fun onEnable() {
        vanishManager = (server.pluginManager.getPlugin("VanishNoPacket") as VanishPlugin).manager

        serverTabCompleteListener = createPacketListener()
        ProtocolLibrary.getProtocolManager().addPacketListener(serverTabCompleteListener)
    }

    override fun onDisable() {
        ProtocolLibrary.getProtocolManager().removePacketListener(serverTabCompleteListener)
    }

    private fun createPacketListener() : PacketListener {
        return object: PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.TAB_COMPLETE) {
            override fun onPacketSending(event: PacketEvent) {
                if (isAllowedToTab(event.player)) return

                val autoComplete = event.packet.stringArrays.values[0]
                val vanishedAutoComplete = ArrayList<String>()

                autoComplete.forEach {
                    val player = Bukkit.getPlayer(it) ?: return
                    // Do nothing if proposals does not contain only online player names

                    if (!isVanished(player)) {
                        vanishedAutoComplete.add(it)
                    }
                }

                if (vanishedAutoComplete.size != autoComplete.size) {
                    val array = Array(vanishedAutoComplete.size, {
                        index -> vanishedAutoComplete[index]
                    })
                    event.packet.stringArrays.write(0, array)
                }
            }
        }
    }

    fun isAllowedToTab(player: Player) : Boolean {
        return player.isOp || isVanished(player)
    }

    private fun isVanished(player: Player) : Boolean {
        return vanishManager.isVanished(player)
    }

}