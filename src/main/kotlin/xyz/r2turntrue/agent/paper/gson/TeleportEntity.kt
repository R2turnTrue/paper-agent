package xyz.r2turntrue.agent.paper.gson

import org.bukkit.Bukkit
import org.bukkit.Location
import xyz.r2turntrue.agent.paper.GSON
import xyz.r2turntrue.agent.paper.gson.type.Position
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*

object TeleportEntity {
    data class TeleportEntity(
        val entityUuid: String,
        val to: Position
    )

    fun process(pk: TeleportEntity) {
        Bukkit.getEntity(UUID.fromString(pk.entityUuid))?.apply {
            teleport(Location(world, pk.to.x, pk.to.y, pk.to.z, pk.to.yaw, pk.to.pitch))
        }
    }
}