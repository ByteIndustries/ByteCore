package uk.co.byteindustries.bytecore.scheduler;


import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.concurrent.Callable;
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

    private static Plugin plugin;

    public static AsyncSchedulers getAsyncSchedulers() {
        return new AsyncSchedulers();
    }

    public static SyncSchedulers getSyncSchedulers() {
        return new SyncSchedulers();
    }

    Schedulers(Plugin plugin) {
        Schedulers.plugin = plugin;
    }

    private Schedulers() {
    }

    /**
     * Class for async schedulers.
     */
    private static class AsyncSchedulers {

        private ArrayList<ScheduledFuture<?>> asyncTasks = new ArrayList<>();

        /**
         * Runs a delayed task asynchronously.
         *
         * @param runnable   The runnable that is being executed.
         * @param startDelay The delay before the task starts.
         * @return Task
         */
        public ScheduledFuture<?> runAsyncDelayedTask(Runnable runnable, long startDelay) {
            ScheduledFuture<?> s = getScheduledExecutorService().schedule(runnable, startDelay, SECONDS);
            asyncTasks.add(s);
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
            asyncTasks.add(s);
            return s;
        }


        /**
         * Runs a repeating task asynchronously.
         *
         * @param runnable    The runnable that is being executed.
         * @param repeatDelay The delay before the next execution.
         * @param startDelay  The delay before the task starts.
         * @return Task
         */
        public ScheduledFuture<?> runAsyncRepeatingTask(Runnable runnable, long repeatDelay, long startDelay) {
            ScheduledFuture<?> s = getScheduledExecutorService().scheduleWithFixedDelay(runnable, repeatDelay, startDelay, SECONDS);
            asyncTasks.add(s);
            return s;
        }

        /**
         * Terminates all scheduled async tasks.
         */
        public void terminateAllTasks() {
            for (ScheduledFuture<?> task : asyncTasks) {
                task.cancel(false);
            }

            asyncTasks.clear();

            try {
                getScheduledExecutorService().awaitTermination(2, SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Class for sync schedulers.
     */
    private static class SyncSchedulers {

        /**
         * @param runnable The runnable that is being executed.
         * @param startDelay The delay before the task starts.
         * @return <code>null</code>
         */
        public ScheduledFuture<?> runSyncTask(final Runnable runnable, long startDelay) {
            return Schedulers.getAsyncSchedulers().runAsyncDelayedTask(new Runnable() {
                @Override
                public void run() {
                    Bukkit.getServer().getScheduler().callSyncMethod(plugin, new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            runnable.run();
                            return null;
                        }
                    });
                }
            }, startDelay);
        }

        /**
         * @param runnable The runnable that is being executed.
         * @param repeatDelay The delay before the next execution.
         * @param startDelay The delay before the task starts.
         * @return <code>null</code>
         */
        public ScheduledFuture<?> runSyncRepeatingTask(final Runnable runnable, long repeatDelay, long startDelay) {
            return Schedulers.getAsyncSchedulers().runAsyncRepeatingTask(new Runnable() {
                @Override
                public void run() {
                    Bukkit.getServer().getScheduler().callSyncMethod(plugin, new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            runnable.run();
                            return null;
                        }
                    });
                }
            }, repeatDelay, startDelay);
        }
    }
}
