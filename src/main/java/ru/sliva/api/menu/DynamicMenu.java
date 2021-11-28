package ru.sliva.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;
import ru.sliva.api.Schedule;

public abstract class DynamicMenu extends Menu{

    private boolean isClosed;
    private int dynamicID;
    private final int watchdog;

    public DynamicMenu(@NotNull Player p, String name, int slots, boolean isClosed) {
        super(p, name, slots);
        this.isClosed = isClosed;
        watchdog = Schedule.timerAsync(new WatchdogRunnable(), 20).getTaskId();
    }

    public final void unload() {
        Schedule.stopTimer(watchdog);
        Schedule.stopTimer(dynamicID);
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
        dynamicID = Schedule.timer(new DynamicMenuRunnable(), 20).getTaskId();
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
                if(Schedule.isRunning(dynamicID)) {
                    Schedule.stopTimer(dynamicID);
                }
            } else {
                if(!Schedule.isRunning(dynamicID)) {
                    runDynamicTask();
                }
            }
        }
    }
}
