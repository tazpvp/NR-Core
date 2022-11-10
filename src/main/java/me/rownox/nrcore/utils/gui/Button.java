package me.rownox.nrcore.utils.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class Button {
    protected ItemStack item;
    private int slot;

    /**
     * Creates a static button with no functionality.
     * @param item The ItemStack you want to use.
     * @return A static button.
     */
    public static Button createBasic(ItemStack item) {
        return new Button(item) {
            public void onClick(InventoryClickEvent e) {
                // Do nothing
            }
        };
    }

    /**
     * Create a functional button.
     * @param item The ItemStack you want to use.
     * @param listener What should happen when the button is clicked on.
     * @return A functional button.
     */
    public static Button create(ItemStack item, final Consumer<InventoryClickEvent> listener) {
        return new Button(item) {
            public void onClick(InventoryClickEvent e) {
                listener.accept(e);
            }
        };
    }

    /**
     * Create a functional button.
     * @param item The ItemStack you want to use.
     * @param listener What should happen when the button is clicked on. Gives you both the ClickEvent and the Button that was clicked.
     * @return A functional button.
     */
    public static Button create(ItemStack item, final BiConsumer<InventoryClickEvent, Button> listener) {
        return new Button(item) {
            public void onClick(InventoryClickEvent e) {
                listener.accept(e, this);
            }
        };
    }

    public Button(ItemStack item) {
        this.item = item;
    }

    /**
     * Get the ItemStack used for the button.
     * @return The ItemStack used for the button.
     */
    public ItemStack getItem() {
        return this.item;
    }

    /**
     * Get what slot the button is in.
     * @return The slot that contains the button.
     */
    protected int getSlot() {
        return this.slot;
    }

    /**
     * Set the slot that the button is in.
     * @param slot The slot of the GUI that the button should be in.
     */
    protected void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Set the ItemStack of the button.
     * @param item The new ItemStack.
     */
    public void setItem(ItemStack item) {
        this.item = item;
    }

    public abstract void onClick(InventoryClickEvent var1);
}