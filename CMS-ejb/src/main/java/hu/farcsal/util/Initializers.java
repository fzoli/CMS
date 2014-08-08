package hu.farcsal.util;

/**
 *
 * @author zoli
 */
public class Initializers {
    
    static interface Initializer<T> {
        T initialize();
    }
    
    public static interface TypedInitializer<T> extends Initializer<T> {
        Class<T> getType();
    }
    
    public static interface AutoInitializer<K, T> extends TypedInitializer<T> {
        K getKey();
    }
    
    public static interface ObjectInitializer<K, T> extends AutoInitializer<Class<K>, T> {}
    
    public static abstract class BaseObjectInitializer<T> implements ObjectInitializer<T, T> {
        
        @Override
        public Class<T> getType() {
            return getKey();
        }
        
    }
    
}
