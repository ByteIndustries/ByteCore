package uk.co.byteindustries.bytecore.event;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ByteCoreInitializationEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private JavaPlugin plugin;

    public ByteCoreInitializationEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
