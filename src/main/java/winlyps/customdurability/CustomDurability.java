package winlyps.customdurability;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class CustomDurability extends JavaPlugin {

    @Override
    public void onEnable() {
        // Schedule the task to run every 20 seconds
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    decreaseArmorDurability(player);
                }
            }
        }.runTaskTimer(this, 0, 400); // 400 ticks = 20 seconds
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void decreaseArmorDurability(Player player) {
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.getType() != Material.AIR) {
                armor.setDurability((short) (armor.getDurability() + 1));
            }
        }
    }
}
