/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

//<editor-fold defaultstate="collapsed" desc="Impoprts">
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPlPgBeanLocal;
import et.gov.eep.hrms.entity.payroll.HrPayrollPlPg;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
//</editor-fold>

/**
 *
 * @author meles
 */
@Named(value = "payLocationPayGroupSetupController")
@ViewScoped
public class PayLocationPayGroupSetupController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Objects & Variables"> 

    @Inject
    HrPayrollPlPg hrPayrollPlPg;
    @Inject
    SessionBean sessionBeanLocal;
    @EJB
    HrPayrollPlPgBeanLocal hrPayrollPlPgBeanLocal;

    private String saveOrUpdate = "Save";
    private String addOrUpdate = "Add";
    private String newOrSearch = "New";
    private String icone = "ui-icon-plus";
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean isUpdated = false;
    private HrPayrollPlPg selectedRow = null;
    private List<HrPayrollPlPg> payLocationPayGroupList = new ArrayList<>();
    private DataModel<HrPayrollPlPg> payLocationPayGroupDataModel = new ListDataModel<>();

    /**
     * Creates a new instance of PayLocationPayGroupSetupController
     */
    public PayLocationPayGroupSetupController() {
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PostConstract">

    @PostConstruct
    public void init() {
        findAllPlPg();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrPayrollPlPg getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrPayrollPlPg selectedRow) {
        this.selectedRow = selectedRow;
    }

    public HrPayrollPlPg getHrPayrollPlPg() {
        if (hrPayrollPlPg == null) {
            hrPayrollPlPg = new HrPayrollPlPg();
        }
        return hrPayrollPlPg;
    }

    public void setHrPayrollPlPg(HrPayrollPlPg hrPayrollPlPg) {
        this.hrPayrollPlPg = hrPayrollPlPg;
    }

    public List<HrPayrollPlPg> getPayLocationPayGroupList() {
        return payLocationPayGroupList;
    }

    public void setPayLocationPayGroupList(List<HrPayrollPlPg> payLocationPayGroupList) {
        this.payLocationPayGroupList = payLocationPayGroupList;
    }

    public DataModel<HrPayrollPlPg> getPayLocationPayGroupDataModel() {
        return payLocationPayGroupDataModel;
    }

    public void setPayLocationPayGroupDataModel(DataModel<HrPayrollPlPg> payLocationPayGroupDataModel) {
        this.payLocationPayGroupDataModel = payLocationPayGroupDataModel;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }

    public String getAddOrUpdate() {
        return addOrUpdate;
    }

    public void setAddOrUpdate(String addOrUpdate) {
        this.addOrUpdate = addOrUpdate;
    }

    public String getNewOrSearch() {
        return newOrSearch;
    }

    public void setNewOrSearch(String newOrSearch) {
        this.newOrSearch = newOrSearch;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="main Methods">
    public void findAllPlPg() {
        payLocationPayGroupList = hrPayrollPlPgBeanLocal.findAllPlPg();
        payLocationPayGroupDataModel = new ListDataModel(new ArrayList(payLocationPayGroupList));
    }

    public void btnNewOrSearch() {
        switch (newOrSearch) {
            case "New":
                searchPage = false;
                newPage = true;
                newOrSearch = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                searchPage = true;
                newPage = false;
                newOrSearch = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    private HrPayrollPlPg payLocPayGroupDetail() {
        HrPayrollPlPg hrPayrollPlPgDetail = new HrPayrollPlPg();
        hrPayrollPlPgDetail.setId(hrPayrollPlPg.getId());
        hrPayrollPlPgDetail.setPayGroup(hrPayrollPlPg.getPayGroup());
        hrPayrollPlPgDetail.setPayLocation(hrPayrollPlPg.getPayLocation());
        hrPayrollPlPgDetail.setDescription(hrPayrollPlPg.getDescription());
        return hrPayrollPlPgDetail;
    }

    public void savePayLocationPayGroupSetup() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "savePayLocationPayGroupSetup", dataset)) {
                if (!isUpdated) {
                    try {
                        hrPayrollPlPgBeanLocal.save(payLocPayGroupDetail());
                        JsfUtil.addSuccessMessage("Successfully Saved.");
                        hrPayrollPlPg = null;
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Error occurs while updating pay location & pay group.");
                    }
                } else {
                    try {
                        hrPayrollPlPgBeanLocal.update(payLocPayGroupDetail());
                        JsfUtil.addSuccessMessage("Successfully Updated.");
                        hrPayrollPlPg = null;
                        isUpdated = false;
                        saveOrUpdate = "Save";
                    } catch (Exception ex) {
                        JsfUtil.addFatalMessage("Error occurs while updating pay location & pay group.");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populate(SelectEvent events) {
        hrPayrollPlPg = null;
        hrPayrollPlPg = (HrPayrollPlPg) events.getObject();
        hrPayrollPlPg = hrPayrollPlPgBeanLocal.getSelectedRequest(hrPayrollPlPg.getId());
        if (hrPayrollPlPg != null) {
            if (hrPayrollPlPg.getId() == 0) {
                isUpdated = false;
                saveOrUpdate = "Save";
            } else {
                isUpdated = true;
                saveOrUpdate = "Update";
            }
        }
        btnNewOrSearch();
    }

    public void reset() {
        hrPayrollPlPg = new HrPayrollPlPg();
        isUpdated = false;
        saveOrUpdate = "Save";
    }
//</editor-fold>
}
