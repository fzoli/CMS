package hu.farcsal.cms.rewrite;

import com.sun.faces.application.view.MultiViewHandler;
import hu.farcsal.cms.entity.spec.LanguageCode;
import hu.farcsal.cms.util.Faces;
import hu.farcsal.cms.util.Pages;
import java.util.Locale;
import javax.faces.context.FacesContext;

/**
 * @author zoli
 */
public class RewriteViewHandler extends MultiViewHandler {

    @Override
    public String getActionURL(FacesContext context, String viewId) {
        String url = super.getActionURL(context, viewId);
        if (RewriteRuleCache.findByViewId(viewId) != null) {
            url = Pages.getLanguageParameter().set(url, context.getViewRoot().getLocale().getLanguage(), false);
        }
        return url;
    }
    
    @Override
    public Locale calculateLocale(FacesContext context) {
        Locale locale = super.calculateLocale(context);
        String lngParam = context.getExternalContext().getRequestParameterMap().get(Pages.getLanguageParameter().getKey());
        if (lngParam != null) return LanguageCode.getLocale(locale, lngParam);
        RewriteRuleCache cache = RewriteRuleCache.findByUrl(Faces.getRealRequestURI(context, true));
        return cache == null ? locale : LanguageCode.getLocale(locale, cache.getLanguageCode());
    }
    
    @Override
    protected void send404Error(FacesContext context) {
        Faces.send404Error(context);
    }
    
}
