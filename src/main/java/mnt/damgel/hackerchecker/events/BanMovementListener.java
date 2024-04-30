package mnt.damgel.hackerchecker.events;

import mnt.damgel.hackerchecker.commands.CheckCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class BanMovementListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (CheckCommand.playerOnCheck.containsKey(playerUUID)) {
            if (CheckCommand.isPlayerOnCheck(playerUUID)) {
                event.setCancelled(true);
            }
        }
    }
}
