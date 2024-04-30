package mnt.damgel.hackerchecker.events;

import mnt.damgel.hackerchecker.commands.CheckCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.UUID;

public class BanDropItem implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (CheckCommand.playerOnCheck.containsKey(playerUUID)) {
            if (CheckCommand.isPlayerOnCheck(playerUUID)) {
                e.setCancelled(true);
            }
        }
    }
}