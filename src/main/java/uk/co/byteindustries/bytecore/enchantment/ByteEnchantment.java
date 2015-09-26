package uk.co.byteindustries.bytecore.enchantment;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/************************************************************
 * ______     _________ ______ _____ ____  _____  ______  *
 * |  _ \ \   / /__   __|  ____/ ____/ __ \|  __ \|  ____| *
 * | |_) \ \_/ /   | |  | |__ | |   | |  | | |__) | |__    *
 * |  _ < \   /    | |  |  __|| |   | |  | |  _  /|  __|   *
 * | |_) | | |     | |  | |___| |___| |__| | | \ \| |____  *
 * |____/  |_|     |_|  |______\_____\____/|_|  \_\______| *
 * *
 * ***********************************************************
 * Author: Byte Industries      License: Apache License 2.0 *
 ************************************************************/
public abstract class ByteEnchantment extends Enchantment implements Listener {

    public ByteEnchantment(int id, JavaPlugin plugin) {
        super(id);

        Bukkit.getPluginManager().registerEvents(this, plugin);

        try {
            Field byIdField = Enchantment.class.getDeclaredField("byId");
            Field byNameField = Enchantment.class.getDeclaredField("byName");

            byIdField.setAccessible(true);
            byNameField.setAccessible(true);

            @SuppressWarnings("unchecked")
            HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) byNameField.get(null);

            if (byId.containsKey(id)) {
                byId.remove(id);
            }

            if (byName.containsKey(getName())) {
                byName.remove(getName());
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Get tarting level of the enchantment.
     *
     * @return The starting level.
     */
    @Override
    public int getStartLevel() {
        return 1;
    }

    /**
     * See whether or not the enchantment can be applied to the specified ItemStack.
     *
     * @param itemStack The itemstack to check.
     * @return If the itemstack can have the enchantment applied to.
     */
    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        if (getEnchantable().contains(itemStack.getType())) {
            return true;
        }

        return false;
    }

    /**
     * Get the display name of the enchantment, does not include level.
     *
     * @return The display name of the enchantment.
     */
    public abstract String getDisplayName();

    /**
     * Get what materials can be enchanted with.
     *
     * @return The materials that are allowed to be enchanted on.
     */
    public abstract List<Material> getEnchantable();

    /**
     * Run when player is PvEing an entity.
     *
     * @param player The player fighting the entity.
     * @param entity The entity being hurt by the player.
     */
    public abstract void pve(Player player, LivingEntity entity);

    /**
     * Run when the player is PvPing another player.
     *
     * @param player The player damaging the enemy.
     * @param enemy The enemy receiving the damage.
     */
    public abstract void pvp(Player player, Player enemy);

    /**
     * Run when the player is mining.
     *
     * @param player The player mining.
     * @param block The destroyed block.
     */
    public abstract void mine(Player player, Block block);

    /**
     * Run when the player has placed a block.
     *
     * @param player The player that is building.
     * @param block The block that has been played.
     */
    public abstract void place(Player player, Block block);

    /**
     * Run when the player holds the item with the enchantment.
     *
     * @param player The player that holds the item.
     * @param item The item that is being held.
     */
    public abstract void onHold(Player player, ItemStack item);

    /**
     * Run when the player unholds the item with the enchantment.
     *
     * @param player The player that no longer holds the item.
     * @param item The item that was held.
     */
    public abstract void onUnhold(Player player, ItemStack item);

    @EventHandler(ignoreCancelled = false)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getItemInHand().hasItemMeta()
                && event.getPlayer().getItemInHand().getItemMeta().hasLore()
                && event.getPlayer().getItemInHand().getItemMeta().getLore().contains(getDisplayName())) {
            place(event.getPlayer(), event.getBlock());
        }
    }

    @EventHandler(ignoreCancelled = false)
    public void onBlockDestroy(BlockBreakEvent event) {
        if (event.getPlayer().getItemInHand().hasItemMeta()
                && event.getPlayer().getItemInHand().getItemMeta().hasLore()
                && event.getPlayer().getItemInHand().getItemMeta().getLore().contains(getDisplayName())) {
            mine(event.getPlayer(), event.getBlock());
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player damager = null;

        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        } else {
            if (event.getDamager() instanceof Arrow) {
                if (((Arrow) event.getDamager()).getShooter() instanceof Player) {
                    damager = (Player) ((Arrow) event.getDamager()).getShooter();
                }
            }
        }

        if (event.getEntity() instanceof Player) {
            if (damager != null) {
                if (damager.getItemInHand().hasItemMeta() && damager.getItemInHand().getItemMeta().hasLore()
                        && damager.getItemInHand().getItemMeta().getLore().contains(getDisplayName())) {
                    pvp(damager, (Player) event.getEntity());
                }
            }
        } else {
            if (damager != null) {
                if (damager.getItemInHand().hasItemMeta() && damager.getItemInHand().getItemMeta().hasLore()
                        && damager.getItemInHand().getItemMeta().getLore().contains(getDisplayName())) {
                    pve(damager, (LivingEntity) event.getEntity());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemChange(PlayerItemHeldEvent event) {
        if(isItemEnchanted(event.getPlayer().getInventory().getItem(event.getPreviousSlot()))) {
            onUnhold(event.getPlayer(), event.getPlayer().getInventory().getItem(event.getPreviousSlot()));
        }

        if(isItemEnchanted(event.getPlayer().getInventory().getItem(event.getNewSlot()))) {
            onHold(event.getPlayer(), event.getPlayer().getInventory().getItem(event.getNewSlot()));
        }
    }

    public void apply(ItemStack itemStack, int level) {
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()
                && itemStack.getItemMeta().getLore().contains(getDisplayName())) {
            return;
        }

        ItemMeta itemMeta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(
                itemStack.getType());

        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', getDisplayName()));
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        itemStack.addUnsafeEnchantment(this, level);
    }

    @SuppressWarnings("deprecation")
    public boolean equals(Object enchantment) {
        if (!(enchantment instanceof ByteEnchantment)) {
            return false;
        }

        ByteEnchantment byteEnchantment = (ByteEnchantment) enchantment;

        if (!byteEnchantment.getName().equals(this.getName())) {
            return false;
        }

        if (byteEnchantment.getId() != this.getId()) {
            return false;
        }

        return true;
    }

    private boolean isItemEnchanted(ItemStack item) {
        if(item == null) {
            return false;
        }

        if (item.hasItemMeta() &&
                item.getItemMeta().hasLore()) {
            for(String loreMessage : item.getItemMeta().getLore()) {
                if(loreMessage.contains(getDisplayName())) {
                    return true;
                }
            }
        }

        return false;
    }

}