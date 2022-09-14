/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.succession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
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
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.succession.CompetencysubtybebeanLocal;
import et.gov.eep.hrms.businessLogic.succession.competencyTypeLocal;
import et.gov.eep.hrms.entity.succession.HrSmCompetencySubtypes;
import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;

/**
 *
 * @author meles
 */
@Named("CompetencySubTypeController")
@ViewScoped
public class CompetencySubTypeController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @Inject
    HrSmCompetencyTypes hrSmCompetencyTypes;
    @EJB
    competencyTypeLocal competencyTypeLocal;
    @Inject
    HrSmCompetencySubtypes hrSmCompetencySubtypes;
    @Inject
    SessionBean SessionBean;
    @EJB
    CompetencysubtybebeanLocal competencysubtybebeanLocal;

    private boolean renderPnlCreateGatePass = false;
    private boolean disableBtnCreate;
    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String tab = "disabled";
    private boolean renderPnlApproved = false;
    private boolean checkBoxReqst = false;
    private boolean checkBoxApprove = false;

    private String saveOrUpdateButton = "Save";
    int update = 0;

    List<HrSmCompetencySubtypes> competencys;
    List<HrSmCompetencyTypes> HrSmCompetencyList = new ArrayList<>();
    DataModel<HrSmCompetencySubtypes> competencyDataModel = new ListDataModel<>();
    HrSmCompetencySubtypes selectedRow = null;
// </editor-fold> 

    @PostConstruct
    public void findAllComptenecy() {
        HrSmCompetencyList = competencysubtybebeanLocal.findAllCompetency();
    }
    //<editor-fold defaultstate="collapsed" desc="Getter setter">

    public DataModel<HrSmCompetencySubtypes> getCompetencyDataModel() {
        return competencyDataModel;
    }

    public void setCompetencyDataModel(DataModel<HrSmCompetencySubtypes> competencyDataModel) {
        this.competencyDataModel = competencyDataModel;
    }

    public HrSmCompetencySubtypes getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrSmCompetencySubtypes selectedRow) {
        this.selectedRow = selectedRow;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public boolean isRenderPnlApproved() {
        return renderPnlApproved;
    }

    public void setRenderPnlApproved(boolean renderPnlApproved) {
        this.renderPnlApproved = renderPnlApproved;
    }

    public boolean isCheckBoxReqst() {
        return checkBoxReqst;
    }

    public void setCheckBoxReqst(boolean checkBoxReqst) {
        this.checkBoxReqst = checkBoxReqst;
    }

    public boolean isCheckBoxApprove() {
        return checkBoxApprove;
    }

    public void setCheckBoxApprove(boolean checkBoxApprove) {
        this.checkBoxApprove = checkBoxApprove;
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public HrSmCompetencyTypes getHrSmCompetencyTypes() {
        if (hrSmCompetencyTypes == null) {
            hrSmCompetencyTypes = new HrSmCompetencyTypes();
        }
        return hrSmCompetencyTypes;
    }

    public void setHrSmCompetencyTypes(HrSmCompetencyTypes hrSmCompetencyTypes) {
        this.hrSmCompetencyTypes = hrSmCompetencyTypes;
    }

    public competencyTypeLocal getCompetencyTypeLocal() {
        return competencyTypeLocal;
    }

    public void setCompetencyTypeLocal(competencyTypeLocal competencyTypeLocal) {
        this.competencyTypeLocal = competencyTypeLocal;
    }

    public HrSmCompetencySubtypes getHrSmCompetencySubtypes() {
        if (hrSmCompetencySubtypes == null) {
            hrSmCompetencySubtypes = new HrSmCompetencySubtypes();
        }

        return hrSmCompetencySubtypes;
    }

    public void setHrSmCompetencySubtypes(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
        this.hrSmCompetencySubtypes = hrSmCompetencySubtypes;
    }

    public CompetencysubtybebeanLocal getCompetencysubtybebeanLocal() {
        return competencysubtybebeanLocal;
    }

    public void setCompetencysubtybebeanLocal(CompetencysubtybebeanLocal competencysubtybebeanLocal) {
        this.competencysubtybebeanLocal = competencysubtybebeanLocal;
    }

    public List<HrSmCompetencySubtypes> getCompetencys() {
        return competencys;
    }

    public void setCompetencys(List<HrSmCompetencySubtypes> competencys) {
        this.competencys = competencys;
    }

    public List<HrSmCompetencyTypes> getHrSmCompetencyList() {
        return HrSmCompetencyList;
    }

    public void setHrSmCompetencyList(List<HrSmCompetencyTypes> HrSmCompetencyList) {
        this.HrSmCompetencyList = HrSmCompetencyList;
    }

    public void vcl_filiterByCategory(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrSmCompetencyTypes = (HrSmCompetencyTypes) event.getNewValue();
        }
    }

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search">
    public void createNewAdditionalAmount() {
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            btnNewRender = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            btnNewRender = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void newButtonAction() {
        hrSmCompetencySubtypes = new HrSmCompetencySubtypes();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        update = 0;
        saveOrUpdateButton = "Save";
    }

    public void populate(SelectEvent events) {
        hrSmCompetencySubtypes = (HrSmCompetencySubtypes) events.getObject();
        update = 1;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        saveOrUpdateButton = "Update";
        setIcone("ui-icon-search");
    }

    public void findByCompSubtypeName() {
        try {
            if (hrSmCompetencySubtypes.getCompetencySubtype().isEmpty()) {
                competencys = competencysubtybebeanLocal.findAll(hrSmCompetencySubtypes);
                competencyDataModel = new ListDataModel(new ArrayList(competencys));
            } else {
                competencys = competencysubtybebeanLocal.findbycompetencyname(hrSmCompetencySubtypes);
                competencyDataModel = new ListDataModel(new ArrayList(competencys));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Method">

    public void saveCompetencySubtype() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveCompetencySubtype", dataset)) {
                try {
                    boolean result = competencysubtybebeanLocal.searchdup(hrSmCompetencySubtypes);
                    if (update == 0 && result == true) {
                        JsfUtil.addFatalMessage("Duplicate Entry.");
                    } else {
                        competencysubtybebeanLocal.SaveOrUpdate(hrSmCompetencySubtypes);
                        if (update == 0) {
                            JsfUtil.addSuccessMessage(" Saved Successfuly.");

                        } else {
                            JsfUtil.addSuccessMessage("Updated Successfully .");
                        }
                        hrSmCompetencySubtypes = new HrSmCompetencySubtypes();
                    }
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error occured while save update");
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveCompetencySubtype");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void resetCompetencySubtype() {
        hrSmCompetencySubtypes = null;
        hrSmCompetencySubtypes = new HrSmCompetencySubtypes();
    }
    //</editor-fold>

}
