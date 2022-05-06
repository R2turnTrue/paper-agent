package xyz.r2turntrue.agent.paper

import com.google.gson.JsonParser
import xyz.r2turntrue.agent.paper.gson.RequestEntityPosition
import xyz.r2turntrue.agent.paper.gson.TeleportEntity
import xyz.r2turntrue.agent.paper.gson.type.Position
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.CompletableFuture

object UDPServer {

    fun start(port: Int) {
        val ds = DatagramSocket(port)
        CompletableFuture.runAsync {
            while(true) {
                val buffer = ByteArray(512)
                var dp = DatagramPacket(buffer, buffer.size)
                ds.receive(dp)

                val str = String(dp.data)
                val parsed = JsonParser.parseString(str).asJsonObject
                val className = parsed.get("__type__").asString
                when(className) {
                    "request_entity_position" ->
                        RequestEntityPosition.process(ds,
                            RequestEntityPosition.RequestEntityPosition(parsed.get("entity_uuid").asString),
                            dp.address,
                            dp.port)
                    "teleport_entity" -> {
                        val pos = parsed.get("to").asJsonObject
                        TeleportEntity.process(
                            TeleportEntity.TeleportEntity(parsed.get("entity_uuid").asString,
                            Position(
                                pos.get("x").asDouble, pos.get("y").asDouble, pos.get("z").asDouble, pos.get("yaw").asFloat, pos.get("pitch").asFloat)))
                    }
                }
            }
        }
    }

}