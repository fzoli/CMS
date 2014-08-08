package hu.farcsal.cms.util;

import hu.farcsal.cms.entity.Page;
import hu.farcsal.util.VariableMap.AutoInitializer;
import hu.farcsal.cms.entity.spec.Helpers;
import hu.farcsal.cms.entity.spec.Helpers.PageHelper;
import hu.farcsal.util.Servlets;
import hu.farcsal.util.Strings;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

/**
 *
 * @author zoli
 */
public class WebPageHelper implements Helpers.PageHelper {

    private final ServletContext SC;

    private String facesDir;

    public WebPageHelper(ServletContext sc) {
        SC = sc;
    }

    @Override
    public String getFacesDir() {
        if (facesDir == null) facesDir = Servlets.getMappingDir(SC, FacesServlet.class);
        return facesDir;
    }

    @Override
    public String getAppCtxPath() {
        return SC.getContextPath();
    }

    @Override
    public String stripAppCtxFromUrl(String url) {
        if (url == null) return null;
        return Strings.ltrim(url, getAppCtxPath());
    }

    @Override
    public String getRealViewPath(Page page, boolean withDir) {
        String vp = page.getViewPath(withDir);
        if (!page.isViewPathGenerated()) return vp;
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            MethodExpression method = ExpressionFactory.newInstance().createMethodExpression(elContext, "#{" + vp + "}", String.class, new Class[] {});
            return (String) method.invoke(elContext, new Object[]{});
        }
        catch (Exception ex) {
            return vp;
        }
    }

    public String stripFacesDir(String path) {
        if (path == null) return null;
        return Strings.ltrim(path, getFacesDir(), "/");
    }

    public static AutoInitializer<PageHelper, WebPageHelper> initializer(final ServletContext sc) {
        return new AutoInitializer<PageHelper, WebPageHelper>() {

            @Override
            public WebPageHelper initialize() {
                if (sc == null) return null;
                return new WebPageHelper(sc);
            }

            @Override
            public Class<WebPageHelper> getType() {
                return WebPageHelper.class;
            }
            
            @Override
            public Class<PageHelper> getKey() {
                return PageHelper.class;
            }
            
        };
    }
    
}
