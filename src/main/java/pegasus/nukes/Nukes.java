package pegasus.nukes;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Nukes extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onTntGoBoom(EntityExplodeEvent e) {
                if (e.isCancelled()) return;
                if (!(e.getEntityType() == EntityType.PRIMED_TNT || e.getEntityType() == EntityType.MINECART_TNT)) return;

                var location = e.getLocation();
                var world = location.getWorld();

                if (world == null) return;

                world.createExplosion(location, 175);
                e.setCancelled(true);
            }

            @EventHandler
            public void onTntCrafted(InventoryClickEvent e) {
                if (e.isCancelled()) return;

                if (!Objects.equals(e.getInventory().getType(), InventoryType.CRAFTING)) return;

                e.setCancelled(true);
                e.getWhoClicked().sendMessage(Component
                        .text("핵확산방지조약에 따라 핵무기의 조합은 금지되어 있습니다.")
                        .color(TextColor.color(255,0,0))
                );
            }
        }, this);
    }
}
