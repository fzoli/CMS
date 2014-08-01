package hu.farcsal.cms.include;

import hu.farcsal.cms.entity.Page;
import hu.farcsal.cms.entity.PageLayout;
import hu.farcsal.cms.entity.PageLayoutColumn;
import hu.farcsal.cms.rewrite.cache.PageMappingCache;
import hu.farcsal.cms.util.Faces;
import hu.farcsal.util.Strings;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.faces.context.FacesContext;
import org.reflections.Reflections;

/**
 *
 * @author zoli
 */
public class Includes {
    
    private static final String INCLUDE_PACKAGE = "hu.farcsal.cms.include";
    
    private static List<Include> includes;
    
    private static final Comparator<Include> CMP_INCLUDE = new Comparator<Include>() {

        @Override
        public int compare(Include o1, Include o2) {
            if (o1 == null && o2 == null) return 0;
            if (o1 == null) return -1;
            if (o2 == null) return 1;
            return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
        }
        
    };
    
    public static void sort(List<Include> includes) {
        if (includes != null) Collections.sort(includes, CMP_INCLUDE);
    }
    
    public static Include create(String name) {
        try {
            Class<Include> cls = (Class<Include>) Class.forName(Strings.insert(name, INCLUDE_PACKAGE + '.'));
            Constructor<Include> con = cls.getConstructor(new Class<?>[0]);
            return con.newInstance();
        }
        catch (Exception ex) {
            throw new RuntimeException(String.format("Failed to initialize include '%s'", name), ex);
        }
    }
    
    public static List<Include> getAvailableIncludes() {
        if (includes != null) return includes;
        Reflections reflections = new Reflections(INCLUDE_PACKAGE);
        Set<Class<? extends Include>> includeClasses = reflections.getSubTypesOf(Include.class);
        includes = new ArrayList<>();
        for (Class<? extends Include> cls : includeClasses) {
            includes.add(create(cls.getName()));
        }
        sort(includes);
        return includes;
    }
    
    public static int getCurrentContentIndex(boolean leftColumn) {
        PageLayout layout = getLayout(Faces.getRealRequestURI(FacesContext.getCurrentInstance(), true));
        if (layout == null) return 0;
        PageLayoutColumn c = leftColumn ? layout.getLeftColumn() : layout.getRightColumn();
        if (c == null) return 0;
        return c.getContentIndex();
    }
    
    public static List<Include> getCurrentWidgets(boolean leftColumn) {
        return getWidgets(Faces.getRealRequestURI(FacesContext.getCurrentInstance(), true), leftColumn);
    }
    
    private static List<Include> getWidgets(String address, boolean leftColumn) {
        return getWidgets(getLayout(address), leftColumn);
    }
    
    private static List<Include> getWidgets(PageLayout layout, boolean leftColumn) {
        List<Include> widgets = new ArrayList<>();
        if (layout == null) return widgets;
        PageLayoutColumn c = leftColumn ? layout.getLeftColumn() : layout.getRightColumn();
        if (c == null) return widgets;
        for (String name : c.getWidgets()) {
            if (name != null && !name.isEmpty()) widgets.add(create(name));
        }
        return widgets;
    }
    
    private static PageLayout getLayout(String address) {
        Page p = PageMappingCache.getPage(address);
        return p == null ? null : p.getLayout();
    }
    
}
