package managedbeans;

import com.sun.faces.component.visit.FullVisitContext;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by holhosa on 2017.09.19..
 */
public class AbstractMBean implements Serializable {

    protected enum PageViewType {
        VIEW, NEW, MODIFY;
    }

    protected PageViewType pageViewType;

    public void showNewEditPanel() {
        pageViewType = PageViewType.NEW;
    }

    public void showModifyEditPanel() {
        pageViewType = PageViewType.MODIFY;
    }

    public boolean editPanelRendered() {
        return PageViewType.NEW.equals(pageViewType) || PageViewType.MODIFY.equals(pageViewType);
    }

    protected void addFacesMessageForComponents(String message, String... componentNames) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Set<UIComponent> talaltKomponensek = komponensekKeresese(facesContext, componentNames);
        if (!talaltKomponensek.isEmpty()) {
            for (UIComponent uiComponent : talaltKomponensek) {
                facesContext.addMessage(uiComponent.getClientId(), facesMessage);
            }
        }
    }

    private Set<UIComponent> komponensekKeresese(final FacesContext facesContext, final String... componentNames) {
        final int targetCount = componentNames.length;
        final Set<UIComponent> found = new HashSet<>();
        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        uiViewRoot.visitTree(new FullVisitContext(facesContext), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent target) {
                for (String componentName : componentNames) {
                    if (target.getId().equals(componentName)) {
                        found.add(target);
                        if (found.size() == targetCount) {
                            return VisitResult.COMPLETE;
                        }
                    }
                }
                return VisitResult.ACCEPT;
            }
        });
        return found;
    }
}
