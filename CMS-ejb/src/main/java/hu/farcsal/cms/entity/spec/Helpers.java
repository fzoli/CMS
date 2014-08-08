package hu.farcsal.cms.entity.spec;

import hu.farcsal.util.VariableMap;
import hu.farcsal.cms.entity.Page;

/**
 *
 * @author zoli
 */
public class Helpers {
    
    private static final VariableMap HELPERS = new VariableMap(
        PageHelper.class
    );
    
    public static interface PageHelper {
        public String getFacesDir();
        public String getAppCtxPath();
        public String stripAppCtxFromUrl(String url);
        public String getRealViewPath(Page page, boolean withDir);
    }
    
    public static interface RankHelper {
        public Rank getRank(String name);
    }
    
    public static <K, T extends K> T initHelper(VariableMap.AutoInitializer<K, T> helper) {
        return HELPERS.initVariable(helper);
    }
    
    public static PageHelper getPageHelper() {
        return HELPERS.getVariable(PageHelper.class);
    }
    
    public static RankHelper getRankHelper() {
        return HELPERS.getVariable(RankHelper.class);
    }
    
}
