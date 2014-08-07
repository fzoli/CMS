package hu.farcsal.cms.entity.spec;

import hu.farcsal.cms.entity.Page;

/**
 *
 * @author zoli
 */
public class Helpers {
    
    private static final HelperMap HELPERS = new HelperMap(
        PageHelper.class
    );
    
    public static interface PageHelper {
        public String getFacesDir();
        public String getAppCtxPath();
        public String stripAppCtxFromUrl(String url);
        public String getRealViewPath(Page page, boolean withDir);
    }
    
    public static <K, T extends K> T initHelper(HelperMap.AutoInitializer<K, T> helper) {
        return HELPERS.initHelper(helper);
    }
    
    public static PageHelper getPageHelper() {
        return HELPERS.getHelper(PageHelper.class);
    }
    
}
