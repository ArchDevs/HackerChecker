package mnt.damgel.hackerchecker;

import mnt.damgel.hackerchecker.commands.CheckCommand;
import mnt.damgel.hackerchecker.commands.ContactCommand;
import mnt.damgel.hackerchecker.commands.ReloadCommand;
import mnt.damgel.hackerchecker.events.BanChatListener;
import mnt.damgel.hackerchecker.events.BanDropItem;
import mnt.damgel.hackerchecker.events.BanMovementListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HackerChecker extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults();

        getCommand("check").setExecutor(new CheckCommand(this));
        getCommand("contact").setExecutor(new ContactCommand(this));
        getCommand("reload").setExecutor(new ReloadCommand(this));

        getServer().getPluginManager().registerEvents(new BanMovementListener(), this);
        getServer().getPluginManager().registerEvents(new BanChatListener(this), this);
        getServer().getPluginManager().registerEvents(new BanDropItem(), this);
    }

}