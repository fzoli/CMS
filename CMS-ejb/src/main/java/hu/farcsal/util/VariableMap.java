package hu.farcsal.util;

import java.util.HashMap;

/**
 *
 * @author zoli
 */
public class VariableMap extends HashMap<Class<?>, Object> {

    public static class InvalidKeyException extends IllegalArgumentException {

        public InvalidKeyException(String msg) {
            super(msg);
        }

    }

    private static interface Initializer<T> {
        T initialize();
    }
    
    public static interface TypedInitializer<T> extends Initializer<T> {
        Class<T> getType();
    }
    
    public static interface AutoInitializer<K, T> extends TypedInitializer<T> {
        Class<K> getKey();
    }
    
    public static abstract class BaseInitializer<T> implements AutoInitializer<T, T> {
        
        @Override
        public Class<T> getType() {
            return getKey();
        }
        
    }
    
    private static final Object SYN = new Object();

    private final Class<?>[] CLASSES;
    
    private final String VALID_CLASSES;
    
    public VariableMap(Class<?> ... classes) {
        CLASSES = classes;
        VALID_CLASSES = Strings.join(CLASSES, ", ", new Strings.ClassFormatter());
    }
    
    public <K, T extends K> T setVariable(AutoInitializer<K, T> initializer) {
        return setVariable(false, initializer.getKey(), initializer.getType(), initializer);
    }
    
    public <K, T extends K> T initVariable(AutoInitializer<K, T> initializer) {
        return initVariable(initializer.getKey(), initializer);
    }
    
    public <K, T extends K> T initVariable(Class<K> key, TypedInitializer<T> initializer) {
        return initVariable(key, initializer.getType(), initializer);
    }
    
    private <K, T extends K> T initVariable(Class<K> key, Class<T> type, Initializer<T> initializer) {
        return setVariable(true, key, type, initializer);
    }
    
    private <K, T extends K> T setVariable(boolean lazy, Class<K> key, Class<T> type, Initializer<T> initializer) {
        T o;
        synchronized (SYN) {
            K h = getVariable(key);
            if (lazy && (h != null && (type == null ? key : type).isInstance(h))) {
                o = (T) h;
            }
            else {
                o = initializer.initialize();
                put(key, o);
            }
        }
        return o;
    }
    
    public <K> K getVariable(Class<K> key) {
        synchronized (SYN) {
            Object o = get(key);
            if (o != null && key.isInstance(o)) {
                return key.cast(o);
            }
            return null;
        }
    }

    @Override
    public Object put(Class<?> key, Object value) {
        checkKey(key);
        return super.put(key, value);
    }
    
    private void checkKey(Object key) {
        if (!isValidKey((Class) key)) throw new InvalidKeyException("invalid class, valid keys: " + VALID_CLASSES);
    }
    
    private boolean isValidKey(Class<?> key) {
        for (Class<?> c : CLASSES) {
            if (c == key) return true;
        }
        return false;
    }
    
}
