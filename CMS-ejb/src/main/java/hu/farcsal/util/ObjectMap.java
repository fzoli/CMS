package hu.farcsal.util;

import java.util.HashMap;

/**
 *
 * @author zoli
 * @param <Obj>
 */
public class ObjectMap<Obj> extends HashMap<Class<? extends Obj>, Obj> {

    public static class InvalidKeyException extends IllegalArgumentException {

        public InvalidKeyException(String msg) {
            super(msg);
        }

    }
    
    private static final Object SYN = new Object();

    private final Class<? extends Obj>[] CLASSES;
    
    private final String VALID_CLASSES;
    
    public ObjectMap(Class<? extends Obj> ... classes) {
        CLASSES = classes;
        VALID_CLASSES = Strings.join(CLASSES, ", ", new Strings.ClassFormatter());
    }
    
    public <K extends Obj, T extends K> T setObject(Initializers.ObjectInitializer<K, T> initializer) {
        return setObject(false, initializer.getKey(), initializer.getType(), initializer);
    }
    
    public <K extends Obj, T extends K> T initObject(Initializers.ObjectInitializer<K, T> initializer) {
        return initObject(initializer.getKey(), initializer);
    }
    
    public <K extends Obj, T extends K> T initObject(Class<K> key, Initializers.TypedInitializer<T> initializer) {
        return initObject(key, initializer.getType(), initializer);
    }
    
    private <K extends Obj, T extends K> T initObject(Class<K> key, Class<T> type, Initializers.Initializer<T> initializer) {
        return setObject(true, key, type, initializer);
    }
    
    private <K extends Obj, T extends K> T setObject(boolean lazy, Class<K> key, Class<T> type, Initializers.Initializer<T> initializer) {
        T o;
        synchronized (SYN) {
            K h = getObject(key);
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
    
    public <K extends Obj> K getObject(Class<K> key) {
        synchronized (SYN) {
            Obj o = get(key);
            if (o != null && key.isInstance(o)) {
                return key.cast(o);
            }
            return null;
        }
    }

    @Override
    public Obj put(Class<? extends Obj> key, Obj value) {
        checkKey(key);
        return super.put(key, value);
    }
    
    private void checkKey(Object key) {
        if (!isValidKey((Class) key)) throw new InvalidKeyException("invalid class, valid keys: " + VALID_CLASSES);
    }
    
    private boolean isValidKey(Class<? extends Obj> key) {
        for (Class<?> c : CLASSES) {
            if (c == key) return true;
        }
        return false;
    }
    
}
