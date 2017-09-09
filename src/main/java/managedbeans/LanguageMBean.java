package managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Attila on 2017.07.02..
 */
@ManagedBean(name = "languageMBean")
@SessionScoped
public class LanguageMBean implements Serializable {

    private static final Locale HUNGARIAN_LOCALE = new Locale("hu");
    private static final Locale ENGLISH_LOCALE = new Locale("en", "GB");

    private Locale locale = HUNGARIAN_LOCALE;

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return getLanguage();
    }

    public void changeLanguage(String language) {
        switch (language) {
            case "hu":
                FacesContext.getCurrentInstance().getViewRoot().setLocale(HUNGARIAN_LOCALE);
                locale = HUNGARIAN_LOCALE;
                break;
            case "en":
                FacesContext.getCurrentInstance().getViewRoot().setLocale(ENGLISH_LOCALE);
                locale = ENGLISH_LOCALE;
                break;
            default:
                FacesContext.getCurrentInstance().getViewRoot().setLocale(HUNGARIAN_LOCALE);
                locale = HUNGARIAN_LOCALE;
        }
        reload();
    }

    private void reload() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException e) {

        }
    }
}
