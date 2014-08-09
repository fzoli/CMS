package hu.farcsal.util;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author zoli
 * @param <K>
 * @param <V>
 */
public class KeyBasedHashMap<K, V> extends KeyBasedMap<K, V> {

    private final HashMap<K, V> MAP = new HashMap<>();

    public KeyBasedHashMap() {
    }

    @Override
    public V get(Object key) {
        return MAP.get(key);
    }

    @Override
    public V put(K key, V value) {
        return MAP.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return MAP.remove(key);
    }

    @Override
    protected Set<K> loadKeySet() {
        return MAP.keySet();
    }
    
}
