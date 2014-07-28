package hu.farcsal.cms.include;

import hu.farcsal.cms.entity.spec.LanguageCode;
import java.util.Locale;

/**
 *
 * @author zoli
 */
public class TestInclude implements Include {

    @Override
    public boolean isPageInclude() {
        return false;
    }
    
    @Override
    public String getSource(Locale locale) {
        return "<p>Test include</p>";
    }
    
    @Override
    public String getDescription(Locale locale) {
        switch (LanguageCode.getLanguageCode(locale)) {
            case HU:
                return "Teszt include";
            default:
                return "Test include";
        }
    }
    
}
