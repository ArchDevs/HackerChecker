package mnt.damgel.hackerchecker.commands;

import mnt.damgel.hackerchecker.HackerChecker;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final HackerChecker plugin;

    public ReloadCommand(HackerChecker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.config-reload", "&aConfiguration reloaded")));
        return true;
    }
}