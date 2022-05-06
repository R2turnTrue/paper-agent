package xyz.r2turntrue.agent.paper

import com.google.gson.Gson
import org.bukkit.plugin.java.JavaPlugin

val GSON = Gson()

class PaperAgentPlugin: JavaPlugin() {

    override fun onEnable() {
        UDPServer.start(5555)
    }

}