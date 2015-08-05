package uk.co.byteindustries.bytecore.scheduler;


import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

import static uk.co.byteindustries.bytecore.scheduler.SchedulerManager.getScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;

/************************************************************
 *   ______     _________ ______ _____ ____  _____  ______  *
 *  |  _ \ \   / /__   __|  ____/ ____/ __ \|  __ \|  ____| *
 *  | |_) \ \_/ /   | |  | |__ | |   | |  | | |__) | |__    *
 *  |  _ < \   /    | |  |  __|| |   | |  | |  _  /|  __|   *
 *  | |_) | | |     | |  | |___| |___| |__| | | \ \| |____  *
 *  |____/  |_|     |_|  |______\_____\____/|_|  \_\______| *
 *                                                          *
 ************************************************************
 * Author: Byte Industries      License: Apache License 2.0 *
 ************************************************************/
public class Schedulers {

    //private Plugin plugin;

    private ArrayList<ScheduledFuture<?>> tasks = new ArrayList<>();

    Schedulers(Plugin plugin) {
        //this.plugin = plugin;
    }

    private Schedulers() {
    }

    /**
     * Runs a delayed task asynchronously.
     *
     * @param runnable The runnable that is being executed.
     * @param startDelay The delay before the task starts.
     * @return Task
     */
    public ScheduledFuture<?> runAsyncDelayedTask(Runnable runnable, long startDelay) {
        ScheduledFuture<?> s = getScheduledExecutorService().schedule(runnable, startDelay, SECONDS);
        tasks.add(s);
        return s;
    }

    /**
     * Runs a task asynchronously.
     *
     * @param runnable The runnable that is being executed.
     * @return Task
     */
    public ScheduledFuture<?> runAsyncTask(Runnable runnable) {
        ScheduledFuture<?> s = getScheduledExecutorService().schedule(runnable, 0, SECONDS);
        tasks.add(s);
        return s;
    }


    /**
     * Runs a repeating task asynchronously.
     *
     * @param runnable The runnable that is being executed.
     * @param repeatDelay The delay before the next execution.
     * @param startDelay The delay before the task starts.
     * @return Task
     */
    public ScheduledFuture<?> runAsyncRepeatingTask(Runnable runnable, long repeatDelay, long startDelay) {
        ScheduledFuture<?> s = getScheduledExecutorService().scheduleWithFixedDelay(runnable, repeatDelay, startDelay, SECONDS);
        tasks.add(s);
        return s;
    }

    /**
     * Terminates all scheduled tasks.
     */
    public void terminateAllTasks() {
        for (ScheduledFuture<?> task : tasks) {
            task.cancel(false);
        }
        tasks.clear();
        try {
            getScheduledExecutorService().awaitTermination(2, SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
