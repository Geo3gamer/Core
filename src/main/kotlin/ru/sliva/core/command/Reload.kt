package ru.sliva.core.command

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class Reload : Command("rs", "Перезапуск сервера", "/rs", emptyList()) {

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if(args.isNotEmpty()) return false

        Bukkit.reload()
        broadcastCommandMessage(sender, "§aReload complete.")

        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
        return emptyList<String>()
    }
}