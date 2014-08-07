package hu.farcsal.cms.entity.spec;

import java.util.HashMap;

/**
 *
 * @author zoli
 */
public class HelperMap extends HashMap<Class<?>, Object> {

    public static class HelperException extends RuntimeException {

        public HelperException(String msg) {
            super(msg);
        }

    }

    private static interface Initializer<T> {
        T init();
    }
    
    private static interface TypedInitializer<T> extends Initializer<T> {
        Class<T> type();
    }
    
    public static interface AutoInitializer<K, T> extends TypedInitializer<T> {
        Class<K> key();
    }
    
    private static final Object SYN = new Object();

    private final Class<?>[] CLASSES;

    public HelperMap(Class<?> ... classes) {
        CLASSES = classes;
    }

    public <K, T extends K> T initHelper(AutoInitializer<K, T> helper) {
        return initHelper(helper.key(), helper);
    }
    
    private <K, T extends K> T initHelper(Class<K> k, TypedInitializer<T> helper) {
        return initHelper(k, helper.type(), helper);
    }
    
    private <K, T extends K> T initHelper(Class<K> k, Class<T> t, Initializer<T> helper) {
        T o;
        synchronized (SYN) {
            K h = getHelper(k);
            if (h != null && (t == null ? k : t).isInstance(h)) {
                o = (T) h;
            }
            else {
                o = helper.init();
                put(k, o);
            }
        }
        return o;
    }
    
    public <K> K getHelper(Class<K> k) {
        synchronized (SYN) {
            Object o = get(k);
            if (o != null && k.isInstance(o)) {
                return k.cast(o);
            }
            return null;
        }
    }

    @Override
    public Object get(Object key) {
        checkKey(key);
        return super.get(key);
    }

    @Override
    public Object put(Class<?> key, Object value) {
        checkKey(key);
        return super.put(key, value);
    }

    private void checkKey(Object key) {
        Class<?> cls = (Class) key;
        boolean ok = false;
        for (Class<?> c : CLASSES) {
            ok |= cls == c;
        }
        if (!ok) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < CLASSES.length; i++) {
                sb.append(CLASSES[i].getSimpleName());
                if (i != CLASSES.length) sb.append(", ");
            }
            throw new HelperException("invalid class, valid keys: " + sb.toString());
        }
    }

}
