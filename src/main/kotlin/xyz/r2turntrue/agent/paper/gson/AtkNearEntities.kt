package xyz.r2turntrue.agent.paper.gson

import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import xyz.r2turntrue.agent.paper.GSON
import xyz.r2turntrue.agent.paper.gson.type.Position
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.*

object AtkNearEntities {
    data class AtkNearEntities(
        val entityUuid: String,
        val radius: Double,
        val damage: Double
    )

    fun process(ds: DatagramSocket, pk: AtkNearEntities, ia: InetAddress, port: Int) {
        Bukkit.getEntity(UUID.fromString(pk.entityUuid))?.apply {
            val json = GSON.toJsonTree(Position(location.x, location.y, location.z, location.yaw, location.pitch)).asJsonObject
            json.addProperty("__type__", "attacked_near_entities")
            getNearbyEntities(pk.radius, pk.radius, pk.radius).filter { entity ->
                entity is LivingEntity && entity.uniqueId != uniqueId
            }.apply {
                json.addProperty("size", size)
                forEach { entity ->
                    (entity as LivingEntity).damage(pk.damage)
                }
            }
            val str = GSON.toJson(json)
            val dp = DatagramPacket(str.toByteArray(), str.toByteArray().size, ia, port)
            ds.send(dp)
        }
    }
}