/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;
//<editor-fold defaultstate="collapsed" desc="Imports">

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.training.HrTdPreserviceCoursesBeanLocal;
import et.gov.eep.hrms.entity.training.HrLuTdPsvcCourseTypes;
import et.gov.eep.hrms.entity.training.HrTdPsvcCourses;

 //</editor-fold>
@Named(value = "hrTdPsvcCoursesController")
@ViewScoped
public class hrTdPreServiceCoursesController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity, bussiness logic &  Variables">
    @Inject
    HrTdPsvcCourses hrTdPsvcCourses;

    @Inject
    HrLuTdPsvcCourseTypes hrLuTdPsvcCourseTypes;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrTdPreserviceCoursesBeanLocal hrTdPsvcCoursesBeanLocal;

    private String tabToggle = "";
    int update = 0;
    private boolean renderPnlCreateAdditional = true;
    private String addorUpdate = "Add";
    private String SaveOrUpdateButton = "Save";
    boolean btnaddvisibility = true;
    private String headerTitle = "Search....";
    private String saveorUpdateBundle = "Update";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private String createOrSearchBundle = "New";
    private boolean disabled;
    private int updateStatus = 0;
    List<HrLuTdPsvcCourseTypes> catagoryList = new ArrayList<>();
    DataModel<HrTdPsvcCourses> hrTdCoursesDataModel;

    //</editor-fold>
    @PostConstruct
    public void init() {
        catagoryList = hrTdPsvcCoursesBeanLocal.categories();
    }
    //<editor-fold defaultstate="collapsed" desc="Getters setters"> 

    public HrLuTdPsvcCourseTypes getHrLuTdPsvcCourseTypes() {
        if (hrLuTdPsvcCourseTypes == null) {
            hrLuTdPsvcCourseTypes = new HrLuTdPsvcCourseTypes();
        }
        return hrLuTdPsvcCourseTypes;
    }

    public void setHrLuTdPsvcCourseTypes(HrLuTdPsvcCourseTypes hrLuTdPsvcCourseTypes) {
        this.hrLuTdPsvcCourseTypes = hrLuTdPsvcCourseTypes;
    }

    public HrTdPsvcCourses getHrTdPsvcCourses() {
        if (hrTdPsvcCourses == null) {
            hrTdPsvcCourses = new HrTdPsvcCourses();
        }
        return hrTdPsvcCourses;
    }

    public void setHrTdPsvcCourses(HrTdPsvcCourses hrTdPsvcCourses) {
        this.hrTdPsvcCourses = hrTdPsvcCourses;
    }

    public String getTabToggle() {
        return tabToggle;
    }

    public void setTabToggle(String tabToggle) {
        this.tabToggle = tabToggle;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<HrLuTdPsvcCourseTypes> getCatagoryList() {
        return catagoryList;
    }

    public void setCatagoryList(List<HrLuTdPsvcCourseTypes> catagoryList) {
        this.catagoryList = catagoryList;
    }

    public DataModel<HrTdPsvcCourses> getHrTdCoursesDataModel() {
        if (hrTdCoursesDataModel == null) {
            hrTdCoursesDataModel = new ArrayDataModel<>();
        }
        return hrTdCoursesDataModel;
    }

    public void setHrTdCoursesDataModel(DataModel<HrTdPsvcCourses> hrTdCoursesDataModel) {
        this.hrTdCoursesDataModel = hrTdCoursesDataModel;
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void newOrSearchPage() {
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                btnNewRender = false;
                createOrSearchBundle = "Search";
                headerTitle = "Electro Mecanics Courses";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlCreateGatePass = false;
                renderPnlManPage = true;
                btnNewRender = false;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        resetPreserviceCourses();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
    }

    public void findByCourse(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrLuTdPsvcCourseTypes.setId(Integer.parseInt(event.getNewValue().toString()));
            hrTdPsvcCourses.setCourseTypeId(hrLuTdPsvcCourseTypes);
            hrTdCoursesDataModel = new ListDataModel(new ArrayList(hrTdPsvcCoursesBeanLocal.findByCourse(hrTdPsvcCourses)));
        }
    }

    public void populate(SelectEvent event) {
        hrTdPsvcCourses = (HrTdPsvcCourses) event.getObject();
        hrTdPsvcCourses = hrTdPsvcCoursesBeanLocal.getSelectedRequest(hrTdPsvcCourses.getId());
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">

    public void savePreserviceCourses() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "savePreserviceCourses", dataset)) {
                try {
                    hrTdPsvcCourses.setCourseTypeId(hrLuTdPsvcCourseTypes);
                    hrTdPsvcCoursesBeanLocal.saveupdate(hrTdPsvcCourses);
                    if (updateStatus == 0) {
                        JsfUtil.addSuccessMessage("Saved Sucessfully");
                        resetPreserviceCourses();
                    } else {
                        JsfUtil.addSuccessMessage("Updated Sucessfully");
                        resetPreserviceCourses();
                    }
                } catch (Exception ex) {
                    JsfUtil.addFatalMessage("Error ocure while save update");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "savePreserviceCourses");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetPreserviceCourses() {
        hrTdPsvcCourses = new HrTdPsvcCourses();
        hrLuTdPsvcCourseTypes = new HrLuTdPsvcCourseTypes();
    }
    //</editor-fold>
}
