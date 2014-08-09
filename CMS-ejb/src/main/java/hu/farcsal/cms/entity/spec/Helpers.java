package hu.farcsal.cms.entity.spec;

import hu.farcsal.util.ObjectMap;
import hu.farcsal.util.Initializers;
import hu.farcsal.cms.entity.Page;

/**
 *
 * @author zoli
 */
public class Helpers {
    
    private static final ObjectMap<Helper> HELPERS = new ObjectMap<Helper>(
        PageHelper.class,
        RankHelper.class
    );
    
    private static interface Helper {}
    
    public static interface PageHelper extends Helper {
        public String getFacesDir();
        public String getAppCtxPath();
        public String stripAppCtxFromUrl(String url);
        public String getRealViewPath(Page page, boolean withDir);
    }
    
    public static interface RankHelper extends Helper {
        public Rank getRank(String name);
    }
    
    public static <K extends Helper, T extends K> T initHelper(Initializers.ObjectInitializer<K, T> helper) {
        return HELPERS.initObject(helper);
    }
    
    public static PageHelper getPageHelper() {
        return HELPERS.getObject(PageHelper.class);
    }
    
    public static RankHelper getRankHelper() {
        return HELPERS.getObject(RankHelper.class);
    }
    
}
