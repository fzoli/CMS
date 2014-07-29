package hu.farcsal.cms.include;

import hu.farcsal.util.Strings;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.reflections.Reflections;

/**
 *
 * @author zoli
 */
@ManagedBean(name="includes")
@ApplicationScoped
public class IncludeBean implements Comparator<Include> {
    
    private static final String INCLUDE_PACKAGE = "hu.farcsal.cms.include";
    
    private static List<Include> includes;
    
    public List<Include> getIncludes() {
        if (includes != null) return includes;
        Reflections reflections = new Reflections(INCLUDE_PACKAGE);
        Set<Class<? extends Include>> includeClasses = reflections.getSubTypesOf(Include.class);
        includes = new ArrayList<>();
        for (Class<? extends Include> cls : includeClasses) {
            includes.add(create(cls.getName()));
        }
        Collections.sort(includes, this);
        return includes;
    }
    
    @Override
    public int compare(Include o1, Include o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
    }
    
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
            Class<Include> cls = (Class<Include>) Class.forName(Strings.insert(name, INCLUDE_PACKAGE + '.'));
            Constructor<Include> con = cls.getConstructor(new Class<?>[0]);
            return con.newInstance();
        }
        catch (Exception ex) {
            throw new RuntimeException(String.format("Failed to initialize include '%s'", name), ex);
        }
    }
    
}
