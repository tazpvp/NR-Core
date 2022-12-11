package world.ntdi.nrcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;


public class ArmorManager {

    private static final HashMap<UUID, ItemStack[]> items = new HashMap<>();
    private static final HashMap<UUID, ItemStack[]> armor = new HashMap<>();

    public static void remArmor(Player player){
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
    }

    public static void setPlayerContents(final Player player, final boolean remove) {
        if(getItems().containsKey(player.getUniqueId())) {
            remArmor(player);
            player.getInventory().setContents(getItems().get(player.getUniqueId()));
            player.getInventory().setArmorContents(getArmor().get(player.getUniqueId()));
            //Bukkit.getScheduler().runTaskLater(TazPvP.getInstance(), player::updateInventory, 1L);
            if(remove) removeUUID(player.getUniqueId());
        }
    }

    public static void storeAndClearInventory(Player player) {
        getItems().put(player.getUniqueId(), player.getInventory().getContents());
        getArmor().put(player.getUniqueId(), player.getInventory().getArmorContents());

        player.getInventory().clear();

        remArmor(player);
    }

    public static void removeUUID(UUID uuid) {
        getItems().remove(uuid);
        getArmor().remove(uuid);
    }

    public static String inventoryToString(Inventory inventory, String name) {
        try {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
            data.writeInt(inventory.getSize());
            data.writeObject(name);
            for (int i = 0; i < inventory.getSize(); i++) {
                data.writeObject(inventory.getItem(i));
            }
            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String itemArrayToString(ItemStack[] items) {
        try {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(str);
            data.writeInt(items.length);
            for (ItemStack item : items) {
                data.writeObject(item);
            }
            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ItemStack[] stringToItemArray(String inventoryData) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(inventoryData));
            BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
            ItemStack[] items = new ItemStack[data.readInt()];
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) data.readObject();
            }
            data.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Inventory stringToInventory(String inventoryData) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(inventoryData));
            BukkitObjectInputStream data = new BukkitObjectInputStream(stream);
            Inventory inventory = Bukkit.createInventory(null, data.readInt(), data.readObject().toString());
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) data.readObject());
            }
            data.close();
            return inventory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static HashMap<UUID, ItemStack[]> getItems() {
        return items;
    }

    public static HashMap<UUID, ItemStack[]> getArmor() {
        return armor;
    }
}

