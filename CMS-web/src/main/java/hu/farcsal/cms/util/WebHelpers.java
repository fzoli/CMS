package hu.farcsal.cms.util;

import hu.farcsal.cms.entity.spec.Helpers;
import javax.servlet.ServletContext;

/**
 *
 * @author zoli
 */
public class WebHelpers {
    
    public static WebPageHelper getPageHelper(ServletContext ctx) {
        return Helpers.initHelper(WebPageHelper.initializer(ctx));
    }
    
}
