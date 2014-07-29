package hu.farcsal.cms.include;

import hu.farcsal.cms.entity.spec.LanguageCode;
import java.util.Locale;

/**
 *
 * @author zoli
 */
public class TestSrcInclude implements Include {
    
    @Override
    public String getSource(Locale locale) {
        return "<h2>Include source</h2>";
    }
    
    @Override
    public String getDescription(Locale locale) {
        switch (LanguageCode.getLanguageCode(locale)) {
            case HU:
                return "Teszt Java include";
            default:
                return "Test Java include";
        }
    }

    @Override
    public boolean isJSF() {
        return false;
    }
    
}
