package hu.farcsal.cms.include;

import hu.farcsal.util.Strings;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author zoli
 */
@ManagedBean(name="includes")
@ApplicationScoped
public class IncludeBean {
    
    private static final String INCLUDE_PACKAGE = "hu.farcsal.cms.include.";
    
    public List<Include> getTestIncludes() {
        ArrayList<Include> s = new ArrayList<>();
        for (String name : getTestIncludeNames()) {
            s.add(create(name));
        }
        return s;
    }
    
    public List<String> getTestIncludeNames() {
        ArrayList<String> s = new ArrayList<>();
        s.add("TestSrcInclude");
        s.add("TestPageInclude");
        return s;
    }
    
    public Include create(String name) {
        try {
            Class<Include> cls = (Class<Include>) Class.forName(Strings.insert(name, INCLUDE_PACKAGE));
            Constructor<Include> con = cls.getConstructor(new Class<?>[0]);
            return con.newInstance();
        }
        catch (Exception ex) {
            throw new RuntimeException(String.format("Failed to initialize include '%s'", name), ex);
        }
    }
    
}
