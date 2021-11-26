package ru.sliva.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;
import ru.sliva.core.Core;

public abstract class DynamicMenu extends Menu{

    private boolean isClosed = false;
    private int dynamicID;
    private final int watchdog;

    public DynamicMenu(@NotNull Player p, String name, int slots, boolean isClosed) {
        super(p, name, slots);
        this.isClosed = isClosed;
        watchdog = Bukkit.getScheduler().runTaskTimerAsynchronously(Core.getInstance(), new WatchdogRunnable(), 20, 0).getTaskId();
    }

    public final void unload() {
        Bukkit.getScheduler().cancelTask(watchdog);
        Bukkit.getScheduler().cancelTask(dynamicID);
    }

    public final void setClosed(boolean closed) {
        isClosed = closed;
    }

    public final boolean getClosed() {
        return isClosed;
    }

    @Override
    public final void onCloseEvent(@NotNull InventoryCloseEvent e) {
        super.onCloseEvent(e);
        isClosed = true;
    }

    private void runDynamicTask() {
        dynamicID = Bukkit.getScheduler().runTaskTimer(Core.getInstance(), new DynamicMenuRunnable(), 20, 0).getTaskId();
    }

    public final class DynamicMenuRunnable implements Runnable {

        @Override
        public void run() {
            redrawInventory();
        }
    }

    public final class WatchdogRunnable implements Runnable {

        @Override
        public void run() {
            if(isClosed) {
                if(Bukkit.getScheduler().isCurrentlyRunning(dynamicID)) {
                    Bukkit.getScheduler().cancelTask(dynamicID);
                }
            } else {
                if(!Bukkit.getScheduler().isCurrentlyRunning(dynamicID)) {
                    runDynamicTask();
                }
            }
        }
    }
}
