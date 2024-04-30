package mnt.damgel.hackerchecker.events;

import mnt.damgel.hackerchecker.HackerChecker;
import mnt.damgel.hackerchecker.commands.CheckCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;

public class BanChatListener implements Listener {

    public final HackerChecker plugin;

    public BanChatListener(HackerChecker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        final String NOT_ALLOWED = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.not-allowed", "&cYou are allowed to use just /contact"));

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (CheckCommand.playerOnCheck.containsKey(playerUUID)) {
            if (CheckCommand.isPlayerOnCheck(playerUUID)) {
                if (!event.getMessage().startsWith("/contact")) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(NOT_ALLOWED);
                }
            }
        }

    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        final String NOT_ALLOWED = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.not-allowed", "&cYou are allowed to use just /contact"));

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (CheckCommand.playerOnCheck.containsKey(playerUUID)) {
            if (CheckCommand.isPlayerOnCheck(playerUUID)) {
                String message = event.getMessage();
                if (!message.startsWith("/contact")) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(NOT_ALLOWED);
                }
            }
        }
    }
}
