package winlyps.customdurability;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class ArmorBreak extends JavaPlugin {

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
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (int i = 0; i < armorContents.length; i++) {
            ItemStack armor = armorContents[i];
            if (armor != null && armor.getType() != Material.AIR) {
                ItemMeta meta = armor.getItemMeta();
                if (meta instanceof Damageable) {
                    Damageable damageable = (Damageable) meta;
                    damageable.setDamage(damageable.getDamage() + 1);
                    armor.setItemMeta(meta);
                    if (damageable.getDamage() >= armor.getType().getMaxDurability()) {
                        armorContents[i] = new ItemStack(Material.AIR);
                    }
                }
            }
        }
        player.getInventory().setArmorContents(armorContents);
    }
}
