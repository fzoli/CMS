package hu.farcsal.cms.include;

import hu.farcsal.cms.entity.spec.LanguageCode;
import java.util.Locale;

/**
 *
 * @author zoli
 */
public class TestPageInclude implements Include {

    private static final String INCLUDE_DIR = "/WEB-INF/includes/";
    
    @Override
    public String getSource(Locale locale) {
        return INCLUDE_DIR + "test.xhtml";
    }

    @Override
    public String getDescription(Locale locale) {
        switch (LanguageCode.getLanguageCode(locale)) {
            case HU:
                return "Tesztoldal include";
            default:
                return "Testpage include";
        }
    }

    @Override
    public boolean isJSF() {
        return true;
    }
    
}
