package hu.farcsal.cms.bean;

import hu.farcsal.cms.bean.PageBeanLocal;
import hu.farcsal.log.Log;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author zoli
 */
class Beans {
    
    private static final Log LOGGER = Log.getLogger(Beans.class);
    
    public static PageBeanLocal lookupPageBeanLocal() {
        try {
            Context c = new InitialContext();
            return (PageBeanLocal) c.lookup("java:global/hu.farcsal_CMS-ear_ear_0.1.1-SNAPSHOT/hu.farcsal_CMS-ejb_ejb_0.1.1-SNAPSHOT/PageBean!hu.farcsal.cms.bean.PageBeanLocal");
        }
        catch (NamingException ne) {
            LOGGER.e("exception caught", ne);
            return null;
        }
    }
    
}
