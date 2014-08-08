package hu.farcsal.cms;

import hu.farcsal.cms.entity.spec.Helpers;
import hu.farcsal.cms.util.WebPageHelper;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author zoli
 */
@WebListener(value = "Initializing entity helpers")
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Helpers.initHelper(WebPageHelper.initializer(sce.getServletContext()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ;
    }
    
}
