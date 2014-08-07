package hu.farcsal.cms.util;

import hu.farcsal.cms.entity.spec.Helpers;
import hu.farcsal.cms.rewrite.PrettyPageHelper;
import javax.servlet.ServletContext;

/**
 *
 * @author zoli
 */
public class WebHelpers {
    
    public static PrettyPageHelper getPageHelper(ServletContext ctx) {
        return Helpers.initHelper(PrettyPageHelper.initializer(ctx));
    }
    
}
