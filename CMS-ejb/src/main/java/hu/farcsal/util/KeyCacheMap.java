package hu.farcsal.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author zoli
 * @param <V>
 */
public class KeyCacheMap<V extends Serializable> extends KeyBasedMap<String, V> {

    public static class CacheException extends RuntimeException {

        public CacheException(Throwable t) {
            super(t);
        }
        
        public CacheException(String msg, Throwable t) {
            super(msg, t);
        }
        
    }
    
    public static class CacheReadException extends CacheException {

        public CacheReadException(Throwable t) {
            super(t);
        }
        
    }
    
    public static class CacheWriteException extends CacheException {

        public CacheWriteException(Throwable t) {
            super(t);
        }
        
    }
    
    private final File DIR;
    
    public KeyCacheMap(String dir) throws IOException {
        this(new File(dir));
    }
    
    public KeyCacheMap(File dir) throws IOException {
        DIR = createDirectory(dir);
    }

    private static File createDirectory(File dir) throws IOException {
        if (dir.isDirectory()) {
            if (!dir.canWrite()) throw new IOException("directory is readonly");
        }
        else {
            if (!dir.exists()) {
                try {
                    dir.mkdirs();
                }
                catch (Exception ex) {
                    throw new IOException("error creating directory", ex);
                }
            }
            else {
                throw new IOException("not a directory");
            }
        }
        return dir;
    }

    @Override
    protected Set<String> loadKeySet() {
        final ArrayCollection<String> ac = new ArrayCollection<>();
//        File[] files = DIR.listFiles(new FileFilter() {
//
//            @Override
//            public boolean accept(File pathname) {
//                return pathname.isFile();
//            }
//            
//        });
//        for (File f : files) {
//            ac.add(createKey(f));
//        }
        DIR.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile()) {
                    ac.add(createKey(pathname));
                }
                return false;
            }
            
        });
        return ac;
    }

    private String createKey(File f) {
        return f.getName();
    }
    
    @Override
    public V get(Object key) {
        return load(key, false);
    }

    @Override
    public V put(String key, V value) {
        String k = toKey(key);
        File f = new File(DIR, k);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f, false));
            out.writeObject(value);
            out.close();
            return null;
        }
        catch (Exception ex) {
            throw new CacheWriteException(ex);
        }
    }

    @Override
    public V remove(Object key) {
        return load(key, true);
    }

    private V load(Object key, boolean delete) {
        String k = toKey(key);
        File f = new File(DIR, k);
        if (!f.isFile()) return null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
            Object o = in.readObject();
            in.close();
            if (delete) {
                f.delete();
            }
            return (V) o;
        }
        catch (Exception ex) {
            throw new CacheReadException(ex);
        }
    }
    
    private String toKey(Object key) {
        if (key == null || !(key instanceof String)) return null;
        return (String) key;
    }
    
}
