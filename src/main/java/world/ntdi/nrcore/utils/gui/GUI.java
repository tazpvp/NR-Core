package world.ntdi.nrcore.utils.gui;

import world.ntdi.nrcore.NRCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GUI implements Listener {
    public static final ItemStack FILLER;
    private final Inventory inventory;
    private Set<Integer> openSlots;
    private Runnable onDestroy;
    private BiConsumer<InventoryClickEvent, List<Integer>> onClickOpenSlot;
    private Consumer<InventoryDragEvent> onDragOpenSlot;
    private Map<Integer, Button> buttons;
    private boolean returnItems;
    private boolean destroyOnClose;
    private boolean destroyed;

    /**
     * Generate an GUI
     * @param inventory The Bukkit Inventory you want to clone
     */
    public GUI(Inventory inventory) {
        this.openSlots = new LinkedHashSet();
        this.onClickOpenSlot = (e, i) -> {};
        this.onDragOpenSlot = (e) -> {};
        this.buttons = new HashMap();
        this.returnItems = true;
        this.destroyOnClose = true;
        this.inventory = inventory;
        this.destroyed = false;
        Bukkit.getPluginManager().registerEvents(this, NRCore.getInstance());
    }

    /**
     * Generate an GUI
     * @param name The name of the GUI
     * @param rows The amount of rows needed for a gui (less than or equal to 6)
     */
    public GUI(String name, int rows) {
        this(Bukkit.createInventory((InventoryHolder)null, rows * 9, name));
    }

    /**
     * Add a button to the GUI
     * @param button Button Object
     * @param slot Slot that the button should go in.
     */
    public void addButton(Button button, int slot) {
        button.setSlot(slot);
        this.inventory.setItem(slot, button.getItem());
        this.buttons.put(slot, button);
    }

    /**
     * Add a button to the GUI
     * @param button Button Object
     * @param x The X coordinate for the button
     * @param y The Y coordinate for the button
     */
    public void addButton(Button button, int x, int y) {
        int slot = x + y * 9;
        this.addButton(button, slot);
    }

    /**
     * Fill the GUI with a certain ItemStack.
     * @param start Where to start the fill
     * @param end Where to end the fill
     * @param item The ItemStack to use
     */
    public void fill(int start, int end, ItemStack item) {
        for(int i = start; i < end; ++i) {
            this.inventory.setItem(i, item == null ? null : item.clone());
        }

    }

    /**
     * Remove a button from a GUI
     * @param button The button object to remove
     */
    public void removeButton(Button button) {
        this.inventory.setItem(button.getSlot(), new ItemStack(Material.AIR));
        this.buttons.remove(button.getSlot());
    }

    /**
     * Get all the buttons in the GUI
     * @return
     */
    public List<Button> getButtons() {
        return new ArrayList(this.buttons.values());
    }

    /**
     * Get the button in a given slot
     * @param slot The slot where the button would be
     * @return Button object
     */
    public Button getButton(int slot) {
        return this.buttons.get(slot);
    }

    /**
     * Remove a button or other item from a slot
     * @param slot The targeted slot
     */
    public void clearSlot(int slot) {
        Button button = (Button)this.buttons.get(slot);
        if (button != null) {
            this.removeButton(button);
        } else {
            this.inventory.setItem(slot, new ItemStack(Material.AIR));
        }
    }

    /**
     * Update the GUI with the newest information
     */
    public void update() {
        Iterator var1 = this.buttons.values().iterator();

        while(var1.hasNext()) {
            Button button = (Button)var1.next();
            this.inventory.setItem(button.getSlot(), button.getItem());
        }

    }

    /**
     * Open a Slot for players to interact with.
     * @param slot
     */
    public void openSlot(int slot) {
        this.openSlots.add(slot);
    }

    /**
     * Open multiple slots for players to interact with.
     * @param start Starting index
     * @param end Ending Index
     */
    public void openSlots(int start, int end) {
        for(int i = start; i < end; ++i) {
            this.openSlots.add(i);
        }

    }

    /**
     * Open multiple slots for players to interact with.
     * @param x1 Starting X Index
     * @param y1 Starting Y Index
     * @param x2 Ending X Index
     * @param y2 Ending Y Index
     */
    public void openSlots(int x1, int y1, int x2, int y2) {
        for(int y = y1; y < y2; ++y) {
            for(int x = x1; x < x2; ++x) {
                this.openSlots.add(y * 9 + x);
            }
        }

    }

    /**
     * Close a slot from player interaction.
     * @param slot The targeted slot.
     */
    public void closeSlot(int slot) {
        this.openSlots.remove(slot);
    }

    /**
     * Close multiple slots from player interaction.
     * @param start Starting index
     * @param end Ending index
     */
    public void closeSlots(int start, int end) {
        for(int i = start; i < end; ++i) {
            this.openSlots.remove(i);
        }

    }

    /**
     * Close multiple slots from player interaction.
     * @param x1 Starting X Index
     * @param y1 Starting Y Index
     * @param x2 Ending X Index
     * @param y2 Ending Y Index
     */
    public void closeSlots(int x1, int y1, int x2, int y2) {
        for(int y = y1; y < y2; ++y) {
            for(int x = x1; x < x2; ++x) {
                this.openSlots.remove(y * 9 + x);
            }
        }

    }

    /**
     * Get all the open slots
     * @return A set of all the open slots
     */
    public Set<Integer> getOpenSlots() {
        return this.openSlots;
    }

    /**
     * Open the GUI to the player
     * @param player The targeted player
     */
    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    /**
     * Check if the GUI should return items put in open slots to the player when it's closed
     * @return True or False
     */
    public boolean returnsItems() {
        return this.returnItems;
    }

    /**
     * Change if the GUI should return items put in open slots of the GUI to the player when it's closed
     * @param returnItems if the GUI should return items put in open slots of the GUI to the player when it's closed
     */
    public void setReturnsItems(boolean returnItems) {
        this.returnItems = returnItems;
    }

    /**
     * Whether the GUI should destroy itself when it's closed
     * @return If the GUI should destroy itself when it's closed
     */
    public boolean destroysOnClose() {
        return this.destroyOnClose;
    }

    /**
     * Change if the GUI should be destroyed when it's closed
     * @param destroyOnClose Boolean
     */
    public void setDestroyOnClose(boolean destroyOnClose) {
        this.destroyOnClose = destroyOnClose;
    }

    /**
     * What should happen when the GUI is destroyed
     * @param onDestroy A Runnable.
     */
    public void setOnDestroy(Runnable onDestroy) {
        this.onDestroy = onDestroy;
    }

    /**
     * Set what happens when a open slot is clicked
     * @param handler The Invntory click event
     */
    public void setOnClickOpenSlot(Consumer<InventoryClickEvent> handler) {
        this.onClickOpenSlot = (e, i) -> {
            handler.accept(e);
        };
    }

    /**
     * Set what happens when a open slot is clicked
     * @param handler The Invntory click event and the integers
     */
    public void setOnClickOpenSlot(BiConsumer<InventoryClickEvent, List<Integer>> handler) {
        this.onClickOpenSlot = handler;
    }

    public void destroy(Player lastViewer) {
        if (this.onDestroy != null) {
            this.onDestroy.run();
        }

        HandlerList.unregisterAll(this);
        if (this.returnItems && lastViewer != null) {
            Iterator var2 = this.openSlots.iterator();

            while(var2.hasNext()) {
                int slot = (Integer)var2.next();
                ItemStack item = this.inventory.getItem(slot);
                if (item != null) {
                    lastViewer.getInventory().addItem(new ItemStack[]{item}).values().forEach((i) -> {
                        lastViewer.getWorld().dropItem(lastViewer.getLocation(), i);
                    });
                }
            }
        }
        this.destroyed = true;

        this.inventory.clear();
        this.buttons.clear();
    }

    /**
     * Destroys the GUI
     */
    public void destroy() {
        this.destroy((Player)null);
    }

    /**
     * Clears the GUI of all it's items
     */
    public void clear() {
        this.inventory.clear();
        this.buttons.clear();
    }

    /**
     * What happens when a item is dragged to an open slot.
     * @param onDrag The InventoryDragEvent
     */
    public void setOnDragOpenSlot(Consumer<InventoryDragEvent> onDrag) {
        this.onDragOpenSlot = onDrag;
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        List<Integer> slots = e.getRawSlots().stream().filter((s) -> this.getInventory(e.getView(), s).equals(this.inventory)).collect(Collectors.toList());
        if (slots.size() != 0) {
            if (!this.openSlots.containsAll(slots)) {
                e.setCancelled(true);
            } else {
                this.onDragOpenSlot.accept(e);
            }
        }
    }

    private Inventory getInventory(InventoryView view, int rawSlot) {
        return rawSlot < view.getTopInventory().getSize() ? view.getTopInventory() : view.getBottomInventory();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (this.inventory.equals(e.getView().getTopInventory())) {
            if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR && !e.getClickedInventory().equals(this.inventory)) {
                e.setCancelled(true);
            } else {
                if (!this.inventory.equals(e.getClickedInventory()) && e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                    if (this.openSlots.size() > 0) {
                        Map<Integer, ItemStack> slots = new HashMap();
                        int amount = e.getCurrentItem().getAmount();
                        Iterator var4 = this.openSlots.iterator();

                        while(var4.hasNext()) {
                            int slot = (Integer)var4.next();
                            if (amount <= 0) {
                                break;
                            }

                            ItemStack item = this.inventory.getItem(slot);
                            int max;
                            if (item == null) {
                                max = Math.min(amount, e.getCurrentItem().getType().getMaxStackSize());
                                amount -= max;
                                ItemStack clone = e.getCurrentItem().clone();
                                clone.setAmount(max);
                                slots.put(slot, clone);
                            } else if (e.getCurrentItem().isSimilar(item)) {
                                max = item.getType().getMaxStackSize() - item.getAmount();
                                int diff = Math.min(max, e.getCurrentItem().getAmount());
                                amount -= diff;
                                ItemStack clone = item.clone();
                                clone.setAmount(clone.getAmount() + diff);
                                slots.put(slot, clone);
                            }
                        }

                        if (slots.size() == 0) {
                            return;
                        }

                        this.onClickOpenSlot.accept(e, new ArrayList(slots.keySet()));
                        if (e.isCancelled()) {
                            return;
                        }

                        e.setCancelled(true);
                        ItemStack item = e.getCurrentItem();
                        item.setAmount(amount);
                        e.setCurrentItem(item);
                        Inventory var10001 = this.inventory;
                        Objects.requireNonNull(var10001);
                        slots.forEach(var10001::setItem);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(NRCore.getInstance(), () -> {
                            ((Player)e.getWhoClicked()).updateInventory();
                        });
                        return;
                    }

                    e.setCancelled(true);
                }

                if (e.getInventory().equals(e.getClickedInventory())) {
                    if (this.openSlots.contains(e.getSlot())) {
                        List<Integer> list = new ArrayList();
                        list.add(e.getSlot());
                        this.onClickOpenSlot.accept(e, list);
                        return;
                    }

                    e.setCancelled(true);
                    Button button = (Button)this.buttons.get(e.getSlot());
                    if (button != null) {
                        button.onClick(e);
                    }
                }

            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().equals(this.inventory) && this.destroyOnClose && e.getViewers().size() <= 1) {
            this.destroy((Player)e.getPlayer());
        }

    }

    static {
        FILLER = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    }
}
