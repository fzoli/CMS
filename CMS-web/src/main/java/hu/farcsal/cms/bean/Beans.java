package hu.farcsal.cms.bean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zoli
 */
public class Beans {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Beans.class);
    
    public static PageBeanLocal lookupPageBeanLocal() {
        try {
            Context c = new InitialContext(); 
            return (PageBeanLocal) c.lookup("java:app/CMS-ejb/PageBean!" + PageBeanLocal.class.getName());
        }
        catch (NamingException ne) {
            LOGGER.error("exception caught", ne);
            return null;
        }
    }
    
}
