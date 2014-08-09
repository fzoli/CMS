package hu.farcsal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @author zoli
 * @param <K>
 * @param <V>
 */
public abstract class KeyBasedMap<K, V> implements Map<K, V> {

    protected final Object LOCK = new Object();
    
    private static abstract class BaseCollection<K, V, O> implements Collection<O> {

        protected final KeyBasedMap<K, V> M;
        protected final ArrayList<K> L;
        
        public BaseCollection(KeyBasedMap<K, V> map) {
            M = map;
            L = new ArrayList<>(map.safeKeySet());
        }
        
        @Override
        public void clear() {
            M.clear(L);
            L.clear();
        }
        
        @Override
        public int size() {
            return L.size();
        }

        @Override
        public boolean isEmpty() {
            return L.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return KeyBasedMap.contains(iterator(), o);
        }

        @Override
        public abstract Iterator<O> iterator();

        @Override
        public Object[] toArray() {
            Object[] a = new Object[size()];
            return toArray(a);
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return KeyBasedMap.toArray(iterator(), a);
        }

        @Override
        public boolean add(O e) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean remove(Object o) {
            Object key = findKey(o);
            if (key == null) return false;
            L.remove(key);
            return M.remove(key) != null;
        }

        protected abstract Object findKey(Object value);
        
        @Override
        public boolean containsAll(Collection<?> c) {
            return KeyBasedMap.containsAll(this, c);
        }

        @Override
        public boolean addAll(Collection<? extends O> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return KeyBasedMap.removeAll(this, c);
        }
        
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

    }
    
    protected static class KeySet<K, V> extends BaseCollection<K, V, K> implements Set<K> {

        protected class KeyIterator implements Iterator<K> {

            private final Iterator<K> IT = L.iterator();

            private K key;
            
            @Override
            public boolean hasNext() {
                return IT.hasNext();
            }

            @Override
            public K next() {
                return key = IT.next();
            }

            @Override
            public void remove() {
                if (key != null) {
                    IT.remove();
                    M.remove(key);
                }
            }
            
        }
        
        public KeySet(KeyBasedMap<K, V> map) {
            super(map);
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        protected Object findKey(Object value) {
            return value;
        }
        
    }
    
    protected static class Values<K, V> extends BaseCollection<K, V, V> {

        protected class ValueIterator implements Iterator<V> {
            
            private K key = null;
            private V obj = null;
            private int index = -1;
            private Boolean hasNext;

            @Override
            public boolean hasNext() {
                if (hasNext != null) return hasNext;
                synchronized (M.LOCK) {
                    int size = L.size();
                    int nextIndex = index + 1;
                    hasNext = size > 0 && nextIndex < size;
                    if (hasNext) {
                        key = L.get(nextIndex);
                        obj = M.get(key);
                    }
                }
                return hasNext;
            }

            @Override
            public V next() {
                if (!hasNext()) throw new NoSuchElementException();
                hasNext = null;
                index++;
                return obj;
            }
            
            protected K key() {
                return key;
            }
            
            protected V val() {
                return obj;
            }

            @Override
            public void remove() {
                if (key != null) {
                    L.remove(key);
                    M.remove(key);
                }
            }
            
        }
        
        public Values(KeyBasedMap<K, V> map) {
            super(map);
        }
        
        @Override
        public ValueIterator iterator() {
            return new ValueIterator();
        }

        @Override
        protected Object findKey(Object value) {
            if (value == null) return null;
            ValueIterator it = iterator();
            while (it.hasNext()) {
                V val = it.next();
                if (val != null && val.equals(value)) {
                    return it.key();
                }
            }
            return null;
        }
        
    }
    
    protected static class TestMapEntries<K, V> implements Set<Entry<K, V>> {

        private final Values<K, V> VALS;
        
        protected class EntryIterator implements Iterator<Entry<K, V>> {

            private final Values<K, V>.ValueIterator IT;
            
            public EntryIterator() {
                IT = VALS.iterator();
            }

            @Override
            public void remove() {
                IT.remove();
            }
            
            @Override
            public boolean hasNext() {
                return IT.hasNext();
            }

            @Override
            public Entry<K, V> next() {
                IT.next();
                return new Entry<K, V>() {

                    @Override
                    public K getKey() {
                        return IT.key();
                    }

                    @Override
                    public V getValue() {
                        return IT.val();
                    }

                    @Override
                    public V setValue(V value) {
                        return getMap().put(getKey(), value);
                    }

                    @Override
                    public boolean equals(Object obj) {
                        if (obj == null || !(obj instanceof Entry)) return false;
                        Entry e = (Entry) obj;
                        return e.getKey().equals(getKey()) && e.getValue().equals(getValue());
                    }

                    @Override
                    public int hashCode() {
                        K k = getKey();
                        return k == null ? 0 : k.hashCode();
                    }
                    
                };
            }
            
        }
        
        public TestMapEntries(Values<K, V> values) {
            VALS = values;
        }

        protected Map<K, V> getMap() {
            return VALS.M;
        }
        
        @Override
        public int size() {
            return VALS.size();
        }

        @Override
        public boolean isEmpty() {
            return VALS.isEmpty();
        }

        @Override
        public void clear() {
            VALS.clear();
        }
        
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean contains(Object o) {
            return KeyBasedMap.contains(iterator(), o);
        }
        
        @Override
        public Object[] toArray() {
            Object[] a = new Object[size()];
            return toArray(a);
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return KeyBasedMap.toArray(iterator(), a);
        }

        @Override
        public boolean add(Entry<K, V> e) {
             throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof Entry) {
                return VALS.remove(((Entry) o).getKey());
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return KeyBasedMap.containsAll(this, c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return KeyBasedMap.removeAll(this, c);
        }
        
        @Override
        public boolean addAll(Collection<? extends Entry<K, V>> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

    }

    public KeyBasedMap() {
    }
    
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new TestMapEntries<>(new Values<>(this));
    }
    
    @Override
    public Collection<V> values() {
        return new Values<>(this);
    }
    
    @Override
    public Set<K> keySet() {
        return new KeySet<>(this);
    }
    
    protected Set<K> safeKeySet() {
        synchronized (LOCK) {
            Set<K> s = loadKeySet();
            if (s == null) return Collections.emptySet();
            return s;
        }
    }
    
    protected abstract Set<K> loadKeySet();
    
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m == null) return;
        synchronized (LOCK) {
            Set<? extends K> s = m.keySet();
            if (s == null) return;
            for (K obj : s) {
                put(obj, m.get(obj));
            }
        }
    }

    @Override
    public void clear() {
        clear(loadKeySet());
    }

    private void clear(Collection<K> keys) {
        synchronized (LOCK) {
            if (keys == null) return;
            for (K obj : keys) {
                remove(obj);
            }
        }
    }
    
    @Override
    public int size() {
        return safeKeySet().size();
    }

    @Override
    public boolean isEmpty() {
        return size() <= 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return safeKeySet().contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }
    
    private static <T> boolean contains(Iterator<T> it, Object obj) {
        if (it == null || obj == null) {
            return false;
        }
        while (it.hasNext()) {
            T o = it.next();
            if (o != null && o.equals(obj)) return true;
        }
        return false;
    }
    
    private static <T> T[] toArray(Iterator<?> it, T[] a) {
        int i = 0;
        while (it.hasNext()) {
            if (i == a.length) break;
            a[i] = (T) it.next();
            i++;
        }
        return a;
    }
    
    private static boolean containsAll(Collection<?> from, Collection<?> c) {
        if (from == null || c == null) {
            return false;
        }
        for (Object o : c) {
            if (!from.contains(o)) return false;
        }
        return true;
    }
    
    private static boolean removeAll(Collection<?> from, Collection<?> c) {
        if (from == null || c == null) return false;
        boolean b = true;
        for (Object o : c) {
            b &= from.remove(o);
        }
        return b;
    }
    
}
