package org.dimasik.menus.inheritance;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.dimasik.menus.implementation.IMenu;

public abstract class Menu implements IMenu {
    @Getter
    protected Player viewer;
    @Getter
    protected Inventory inventory;
    private final JavaPlugin plugin;

    public Menu(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public Menu setPlayer(Player player){
        this.viewer = player;
        return this;
    }

    public void open() {
        if (viewer == null || inventory == null) return;

        Runnable openTask = () -> viewer.openInventory(inventory);
        if (Bukkit.isPrimaryThread())
            openTask.run();
        else
            Bukkit.getScheduler().runTask(plugin, openTask);
    }

    public abstract Menu compile();

    public void close(){
        if (viewer == null || viewer.getOpenInventory().getTopInventory() != inventory) return;

        Runnable openTask = () -> viewer.closeInventory();
        if (Bukkit.isPrimaryThread())
            openTask.run();
        else
            Bukkit.getScheduler().runTask(plugin, openTask);
    }

    public Inventory getCachedInventory(){
        return inventory;
    }
}
