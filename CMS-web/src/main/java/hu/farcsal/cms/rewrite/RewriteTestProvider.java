package hu.farcsal.cms.rewrite;

import hu.farcsal.util.UrlParameters;
import javax.servlet.ServletContext;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Condition;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Rule;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

/**
 *
 * @author zoli
 */
@RewriteConfiguration
public class RewriteTestProvider extends HttpConfigurationProvider {
    
    public static final UrlParameters LNG_PARAM = new UrlParameters("lang");
    
    private static class LngCondition extends ParameterCondition {
        
        public LngCondition(String value) {
            super(LNG_PARAM, value);
        }
        
    }
    
    @Override
    public int priority() {
        return 0;
    }

    @Override
    public Configuration getConfiguration(final ServletContext context) {
        Condition conditionHu = new LngCondition("hu");
        Condition conditionEn = new LngCondition("en");
        Rule ruleHu = Join.path("/tigris").to("/faces/tiger.xhtml");
        Rule ruleEn = Join.path("/tiger").to("/faces/tiger.xhtml");
        return ConfigurationBuilder.begin()
            .addRule(ruleHu).when(conditionHu)
            .addRule(ruleEn).when(conditionEn);
    }
    
}
