package ru.sliva.modules.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;
import ru.sliva.modules.scheduler.ModuleScheduler;

public abstract class DynamicMenu extends Menu{

    private boolean isClosed;
    private int dynamicID;
    private final int watchdog;

    public DynamicMenu(@NotNull Player p, String name, int slots, boolean isClosed) {
        super(p, name, slots);
        this.isClosed = isClosed;
        watchdog = ModuleScheduler.timerAsync(new WatchdogRunnable(), 20).getTaskId();
    }

    public final void unload() {
        ModuleScheduler.stopTimer(watchdog);
        ModuleScheduler.stopTimer(dynamicID);
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
        dynamicID = ModuleScheduler.timer(new DynamicMenuRunnable(), 20).getTaskId();
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
                if(ModuleScheduler.isRunning(dynamicID)) {
                    ModuleScheduler.stopTimer(dynamicID);
                }
            } else {
                if(!ModuleScheduler.isRunning(dynamicID)) {
                    runDynamicTask();
                }
            }
        }
    }
}
