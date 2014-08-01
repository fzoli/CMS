package hu.farcsal.cms.include;

import hu.farcsal.cms.entity.PageLayout;
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
    
    public List<Include> getTestIncludes() {
        ArrayList<Include> s = new ArrayList<>();
        for (String name : getTestIncludeNames()) {
            s.add(Includes.create(name));
        }
        Includes.sort(s);
        return s;
    }
    
    private List<String> getTestIncludeNames() {
        ArrayList<String> s = new ArrayList<>();
        s.add("TestSrcInclude");
        s.add("TestPageInclude");
        return s;
    }
    
    public Include create(String className) {
        return Includes.create(className);
    }
    
    public List<Include> getAvailableIncludes() {
        return Includes.getAvailableIncludes();
    }
    
    public PageLayout.Type getCurrentType() {
        return Includes.getCurrentType();
    }
    
    public int getCurrentContentIndex(boolean leftColumn) {
        return Includes.getCurrentContentIndex(leftColumn);
    }
    
    public List<Include> getCurrentWidgets(boolean leftColumn) {
        return Includes.getCurrentWidgets(leftColumn);
    }
    
}
