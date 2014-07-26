package hu.farcsal.cms.rewrite;

import hu.farcsal.cms.util.WebHelpers;
import javax.servlet.ServletContext;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

/**
 *
 * @author zoli
 */
@RewriteConfiguration
public class CMSConfigurationProvider extends HttpConfigurationProvider {

    private static String cms;
    
    private static void initProvider(ServletContext context) {
        if (cms == null) {
            String faces = WebHelpers.getPageHelper(context).getFacesDir();
            cms = faces + "/cms/";
        }
    }
    
    @Override
    public int priority() {
        return ConfigOrder.CMS.getPriority();
    }
    
    @Override
    public Configuration getConfiguration(ServletContext context) {
        initProvider(context);
        return ConfigurationBuilder.begin()
        .addRule(Join.path("/cms/").to(cms + "pages.xhtml"))
        .addRule(Join.path("/cms/test/").to(cms + "none.xhtml"));
    }
    
}
