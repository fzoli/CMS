package hu.farcsal.cms.include;

import java.lang.reflect.Constructor;

/**
 *
 * @author zoli
 */
public class Includes {
    
    public static Include create(String name) {
        try {
            Class<Include> cls = (Class<Include>) Class.forName(name);
            Constructor<Include> con = cls.getConstructor(new Class<?>[0]);
            return con.newInstance();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
}
