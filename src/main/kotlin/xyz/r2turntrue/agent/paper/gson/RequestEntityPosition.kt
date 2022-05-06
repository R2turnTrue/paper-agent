package xyz.r2turntrue.agent.paper.gson

import com.google.gson.JsonObject
import org.bukkit.Bukkit
import xyz.r2turntrue.agent.paper.GSON
import xyz.r2turntrue.agent.paper.gson.type.Position
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*


object RequestEntityPosition {
    data class RequestEntityPosition(
        val entityUuid: String
    )

    fun process(ds: DatagramSocket, pk: RequestEntityPosition, ia: InetAddress, port: Int) {
        Bukkit.getEntity(UUID.fromString(pk.entityUuid))?.apply {
            val json = GSON.toJsonTree(Position(location.x, location.y, location.z, location.yaw, location.pitch)).asJsonObject
            json.addProperty("__type__", "responded_entity_position")
            val str = GSON.toJson(json)
            val dp = DatagramPacket(str.toByteArray(), str.toByteArray().size, ia, port)
            ds.send(dp)
        }
    }
}