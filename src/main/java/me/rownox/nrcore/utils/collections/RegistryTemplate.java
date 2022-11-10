package me.rownox.nrcore.utils.collections;

import lombok.NonNull;

import java.util.HashMap;

public class RegistryTemplate<K, V> {

    private final HashMap<K, V> items;

    private final V defaultValue;
    private boolean locked;

    /**
     * Creates a new registry template
     *
     * @param defaultValue
     */
    protected RegistryTemplate(@NonNull final V defaultValue) {
        this.items = new HashMap<>();
        this.defaultValue = defaultValue;
        this.locked = false;
    }

    /**
     * Attemps to retrieve an item from the registry if it does not exist it returns
     * a default value
     *
     * @param key the key to retrieve
     * @return
     */
    public V get(@NonNull final K key) {
        return this.items.getOrDefault(key, defaultValue);
    }

    /**
     * Registers an item to the registry if the registry is locked it will throw an
     * IllegalStateException
     *
     * @param key   the key to register
     * @param value the value to register
     */
    public void register(@NonNull final K key, @NonNull final V value) {
        if (this.locked) {
            throw new IllegalStateException("Registry is locked");
        }
        this.items.computeIfAbsent(key, (K k) -> this.items.put(k, value));
    }

    /**
     * Attempts to unregister an item from the registry if the registry is locked it
     * will throw an IllegalStateException
     *
     * @param key the key to unregister
     */
    public void unregister(@NonNull final K key) {
        if (this.locked) {
            throw new IllegalStateException("Registry is locked");
        }
        this.items.computeIfPresent(key, (K k, V v) -> this.items.remove(k));
    }

    /**
     * Permantly locks the registry preventing any further changes
     */
    public void lock() {
        this.locked = true;
    }

}
