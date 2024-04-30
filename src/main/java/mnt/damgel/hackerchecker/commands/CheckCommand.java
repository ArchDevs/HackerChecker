package mnt.damgel.hackerchecker.commands;

import mnt.damgel.hackerchecker.HackerChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class CheckCommand implements CommandExecutor {
    public static HashMap<UUID, UUID> playerOnCheck = new HashMap<>();
    public static HashMap<UUID, Boolean> isOnCheck = new HashMap<>();
    private final HackerChecker plugin;

    public CheckCommand(HackerChecker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player checker = (Player) sender;

        if (!(args.length == 1)) {
            checker.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.check-usage", "&cError! &7Usage: &a/check [player]")));
            return true;
        } else {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                checker.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.player-not-online", "&4Player is not online")));
                return true;
            }

            if (target.hasPermission("hackerchecker.bypass") || target.isOp() || target.hasPermission("*")) {
                checker.sendMessage(ChatColor.RED + "You can't check this player!");
                return true;
            }

            UUID targetID = target.getUniqueId();

            // Store the checker's UUID with the target's UUID
            playerOnCheck.put(targetID, checker.getUniqueId());

            if (isPlayerOnCheck(targetID)) {
                removeEffects(target);
                removePlayerFromCheck(targetID);
                checker.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.player-is-free", "&aPlayer is free")));
            } else {
                putPlayerOnCheck(targetID);
                applyEffects(target);
                checker.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.player-on-check", "&bPlayer is on check")));
            }
        }
        return true;
    }

    public static boolean isPlayerOnCheck(UUID playerID) {
        return isOnCheck.containsKey(playerID) && isOnCheck.get(playerID);
    }

    public static UUID getCheckerUUID(UUID playerID) {
        return playerOnCheck.get(playerID);
    }

    private void putPlayerOnCheck(UUID playerID) {
        isOnCheck.put(playerID, true);
    }

    private void removePlayerFromCheck(UUID playerID) {
        isOnCheck.remove(playerID);
    }

    private void applyEffects(Player player) {
        boolean TELEPORT_SPAWN = plugin.getConfig().getBoolean("teleport-spawn", true);
        player.sendTitle(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Title.title", "&6CHECK!")), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Title.subtitle", "Type /contact and your Discord!")), 15, 604800, 15);
        player.setGameMode(GameMode.ADVENTURE);
        player.setFlying(false);
        player.setFlySpeed(0.0F);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 6000, 1));
        if (TELEPORT_SPAWN) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "spawn" + player.getName());
        }
    }

    private void removeEffects(Player player) {
        boolean TELEPORT_BACK = plugin.getConfig().getBoolean("teleport-back", true);
        player.resetTitle();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setFlySpeed(1.0F);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        if (TELEPORT_BACK) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "back" + player.getName());
        }
    }
}
