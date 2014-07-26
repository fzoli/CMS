package hu.farcsal.cms.rewrite;

/**
 *
 * @author zoli
 */
enum ConfigOrder {
    
    PURE_PAGE_FILTER,
    DATABASE,
    HOME_PAGE,
    CMS;
    
    private static final int ADDITION = 10;
    
    public int getPriority() {
        return ordinal() + ADDITION;
    }
    
}
