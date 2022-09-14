/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.ifrs.businessLogic.DepreciationSetupBeanLocal;
import et.gov.eep.ifrs.entity.IfrsDepreciationSetup;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject; 
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named("deprSetupControllers")
@ViewScoped
public class DepreciationSetupController implements Serializable {

    @EJB
    DepreciationSetupBeanLocal depreciationSetupBeanLocal;

    @Inject
    IfrsDepreciationSetup depreciationSetup;
    @Inject
    SessionBean SessionBean;
    List<IfrsDepreciationSetup> depreciationSetupList;
    private String SaveOrUpdateButton = "Save";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = true;
    private boolean renderPnlManPage = false;
    private String icone = "ui-icon-plus";
    int update = 0;
     private IfrsDepreciationSetup selectedRow;

     //<editor-fold defaultstate="collapsed" desc="Post Construct ">
    @PostConstruct
    public void fill_DepreciationList() {
     depreciationSetupList = depreciationSetupBeanLocal.depreciationList();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getter & sette">

    public IfrsDepreciationSetup getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(IfrsDepreciationSetup selectedRow) {
        this.selectedRow = selectedRow;
    }
    

    public List<IfrsDepreciationSetup> getDepreciationSetupList() {
        return depreciationSetupList;
    }

    public void setDepreciationSetupList(List<IfrsDepreciationSetup> depreciationSetupList) {
        this.depreciationSetupList = depreciationSetupList;
    }
    
    
    public IfrsDepreciationSetup getDepreciationSetup() {
        if (depreciationSetup == null) {
            depreciationSetup = new IfrsDepreciationSetup();
        }
        return depreciationSetup;
    }

    public void setDepreciationSetup(IfrsDepreciationSetup depreciationSetup) {
        this.depreciationSetup = depreciationSetup;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

//</editor-fold>
    public void createNewAdditionalAmount() {
        if (createOrSearchBundle.equals("New")) {
            update = 1;
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="save method">
    public void saveDepreciationSetupIfrs() {
       try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveDepreciationSetupIfrs", dataset)) {
            depreciationSetupBeanLocal.saveOrUpdate(depreciationSetup);
            clearDepreciationSetup();
            if (update == 0) {
                JsfUtil.addSuccessMessage("Saved Successfuly.");
            } else {
                JsfUtil.addSuccessMessage("Update Successful.");
            }
             } else {
                et.gov.eep.commonApplications.utility.JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object
                security.addEventLog(eventEntry, dataset);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage(depreciationSetup.getDepreciationType() + " Already Exist");
        }
        clearDepreciationSetup();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="clear Method">
    public void clearDepreciationSetup() {
        depreciationSetup = null;
       
    }

//</editor-fold>
    
     //<editor-fold defaultstate="collapsed" desc="populate for update">
    public void populateDepreciation(SelectEvent events) {
        System.out.println("inside populate======");
//        depreciationSetup = null;
        depreciationSetup = (IfrsDepreciationSetup) events.getObject();
        update = 1;
        SaveOrUpdateButton = "Update";

    }
//</editor-fold>
}
