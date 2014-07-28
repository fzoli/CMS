package hu.farcsal.cms.bean;

import hu.farcsal.cms.entity.Language;
import hu.farcsal.cms.entity.spec.LanguageCode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 * Test.
 * Source: http://www.mkyong.com/jsf2/jsf-2-internationalization-example/
 */
@ManagedBean(name="language")
@SessionScoped
public class LanguageBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @EJB
    private PageBeanLocal bean;
    
    private String lngCodeCms;
    private Language lngCms;
    
    private String test = "";

    public void setTest(String test) {
        System.out.println("setTest: " + test);
        this.test = test;
    }
    
    public List<Language> getLanguages() {
        return bean.getLanguages();
    }

    public Boolean getCodeHintInvalid() {
        return !validateCodeHint();
    }

    public String getCmsLanguageCode() {
        return lngCodeCms;
    }
    
    public void test() {
        System.out.println("language bean test method " + test);
    }

    public void testSave() {
        System.out.println("language bean test save method");
        RequestContext rcontext = RequestContext.getCurrentInstance();
        FacesContext fcontext = FacesContext.getCurrentInstance();
        boolean saved = validateCodeHint();
        if (rcontext != null) {
            rcontext.addCallbackParam("saved", saved);
        }
        if (fcontext != null) {
            if (!saved) fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid language code", "Language is not saved."));
        }
    }
    
    public void setLocaleCode(String localeCode) {
        System.out.println("set locale code: " + localeCode);
    }

    public void cmsLanguageChanged(ValueChangeEvent e) {
        setCmsLanguage(e.getNewValue().toString());
    }

    public void setCmsLanguage(String lngCode) {
        if (lngCode == null) return;
        for (Language l : getLanguages()) {
            if (l != null && l.getCode().equals(lngCode)) {
                lngCodeCms = lngCode;
                lngCms = l;
                System.out.println("cms lng changed to " + l.getName());
                break;
            }
        }
    }
    
    public boolean validateCodeHint() {
        return validateLanguageCode(codeHint);
    }
    
    public boolean validateLanguageCode(String value) {
        if (value == null) return false;
        Pattern p = Pattern.compile("^[a-z]{2}$");
        return p.matcher(value).matches();
    }
    
    public List<String> completeText(String query) {
        List<String> results = new ArrayList<>();
        for (Language l : getLanguages()) {
            String lng = l.getName();
            if (lng.toLowerCase().startsWith(query.toLowerCase())) results.add(lng);
        }
        return results;
    }
    
    public List<String> completeInternationalHint(String query) {
        return completeHint(query, 2);
    }
    
    public List<String> completeNativeHint(String query) {
        return completeHint(query, 1);
    }
    
    private String codeHint;
    
    public List<String> completeCodeHint(String query) {
        codeHint = query;
        System.out.println("typed code hint: " + codeHint);
        return completeHint(query, 0);
    }
    
    public void hintSelected(SelectEvent event) {
        codeHint = event.getObject().toString();
        System.out.println("selected code hint: " + codeHint);
    }
    
    private List<String> completeHint(String query, int hintIndex) {
        List<String> results = new ArrayList<>();
        for (LanguageCode hint : LanguageCode.values()) {
            String lng;
            switch (hintIndex) {
                case 0:
                    lng = hint.name();
                    break;
                case 1:
                    lng = hint.NATIVE_NAME;
                    break;
                default:
                    lng = hint.ENGLISH_NAME;
                    break;
            }
            if (lng.toLowerCase().startsWith(query.toLowerCase())) results.add(lng);
        }
        Collections.sort(results);
        return results;
    }
    
}
