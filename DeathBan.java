package me.deathban;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DeathBan extends JavaPlugin implements Listener {

    private static final long BAN_DURATION = TimeUnit.MINUTES.toMillis(15);

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        getLogger().info("DeathBan aktif!");
        getLogger().info("Player yang mati akan dibanned selama 15 menit.");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        String name = player.getName();

        Date expires = new Date(
                System.currentTimeMillis() + BAN_DURATION
        );

        String reason = "Kamu mati!\nBan selama 15 menit.";

        Bukkit.getBanList(BanList.Type.NAME).addBan(
                name,
                reason,
                expires,
                "DeathBan"
        );

        Bukkit.getScheduler().runTask(
                this,
                () -> player.kickPlayer(
                        "§c§lKAMU MATI!\n\n" +
                        "§fKamu terkena ban selama §e15 menit§f.\n" +
                        "§7Silakan kembali setelah waktu ban selesai."
                )
        );

        getLogger().info(
                name + " mati dan terkena ban selama 15 menit."
        );
    }
}
