package mnt.damgel.hackerchecker.commands;

import mnt.damgel.hackerchecker.HackerChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ContactCommand implements CommandExecutor {

    private final HackerChecker plugin;

    public ContactCommand(HackerChecker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can you just player");
            return true;
        }

        Player senderPlayer = (Player) sender;
        UUID senderUUID = senderPlayer.getUniqueId();

        // Retrieve the checker's UUID associated with the sender's UUID
        UUID checkerUUID = CheckCommand.getCheckerUUID(senderUUID);

        if (checkerUUID == null) {
            senderPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.no-one-check", "&7You are not currently being checked by anyone")));
            return true;
        }

        Player checkerPlayer = Bukkit.getPlayer(checkerUUID);

        if (checkerPlayer == null) {
            senderPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.player-not-online", "&4The player checking you is not online")));
            return true;
        }

        String message = String.join(" ", args);

        // Send the message to the checker, not the sender
        checkerPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.message-from", "Message sent from")) + ChatColor.RESET + " " + senderPlayer.getName() + ": " + message);
        senderPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.message-sent", "Message sent to")) + ChatColor.RESET + " " + checkerPlayer.getName());

        return true;
    }
}
