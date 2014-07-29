package hu.farcsal.cms.include;

import java.util.Locale;

/**
 *
 * @author zoli
 */
public interface Include {
    
    public boolean isJSF();
    
    public String getSource(Locale locale);
    
    public String getDescription(Locale locale);
    
    public String getImageBase64();
    
}
