package com.ravvenlord.bronx.plugin;

import com.ravvenlord.bronx.Bronx;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The {@link BronxPlugin} class is the spigot plugin instance that the server will load. This class contains both the
 * onEnable and onDisable method which represent both start and stop of the plugins runtime
 */
public class BronxPlugin extends JavaPlugin implements Bronx {

    @Override
    public void onLoad() {
        getServer().getServicesManager().register(Bronx.class, this, this, ServicePriority.Normal);
    }
}
