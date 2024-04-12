package pegasus.nukes;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

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

                var thread = new Thread(() -> {
                    world.createExplosion(location, 175);
                });

                thread.start();
                e.setCancelled(true);
            }
        }, this);
    }
}
