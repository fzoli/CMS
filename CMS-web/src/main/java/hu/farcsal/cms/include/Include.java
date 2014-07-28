package hu.farcsal.cms.include;

import java.util.Locale;

/**
 *
 * @author zoli
 */
public interface Include {
    
    public String getSource(Locale locale);
    
    public String getDescription(Locale locale);
    
    public boolean isPageInclude();
    
}
