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
import et.gov.eep.hrms.businessLogic.succession.CompetencyLocal;
import et.gov.eep.hrms.businessLogic.succession.competencyTypeLocal;
import et.gov.eep.hrms.entity.succession.HrSmCompetency;
import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;

@Named("competencyTypecontroller")
@ViewScoped
public class competencyTypecontroller implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @Inject
    HrSmCompetency hrSmCompetency;
    @Inject
    SessionBean SessionBean;
    @EJB
    CompetencyLocal competencyLocal;
    @Inject
    HrSmCompetencyTypes hrSmCompetencyTypes;
    @EJB
    competencyTypeLocal competencyTypeLocal;

    DataModel<HrSmCompetencyTypes> competencyDataModel = new ListDataModel<>();
    HrSmCompetencyTypes selectedRow = null;
    List<HrSmCompetencyTypes> competencys;
    List<HrSmCompetency> HrSmCompetencyList = new ArrayList<>();

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
// </editor-fold>

    @PostConstruct
    public void findAllComptenecy() {
        HrSmCompetencyList = competencyLocal.findAllCompetency();
    }

    //<editor-fold defaultstate="collapsed" desc="geter and setter">
    public competencyTypeLocal getCompetencyTypeLocal() {
        return competencyTypeLocal;
    }

    public void setCompetencyTypeLocal(competencyTypeLocal competencyTypeLocal) {
        this.competencyTypeLocal = competencyTypeLocal;
    }

    public DataModel<HrSmCompetencyTypes> getCompetencyDataModel() {
        return competencyDataModel;
    }

    public void setCompetencyDataModel(DataModel<HrSmCompetencyTypes> competencyDataModel) {
        this.competencyDataModel = competencyDataModel;
    }

    public HrSmCompetencyTypes getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(HrSmCompetencyTypes selectedRow) {
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

    public HrSmCompetency getHrSmCompetency() {
        if (hrSmCompetency == null) {
            hrSmCompetency = new HrSmCompetency();
        }
        return hrSmCompetency;
    }

    public void setHrSmCompetency(HrSmCompetency hrSmCompetency) {
        this.hrSmCompetency = hrSmCompetency;
    }

    public List<HrSmCompetency> getHrSmCompetencyList() {
        return HrSmCompetencyList;
    }

    public void setHrSmCompetencyList(List<HrSmCompetency> HrSmCompetencyList) {
        this.HrSmCompetencyList = HrSmCompetencyList;
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
        hrSmCompetencyTypes = new HrSmCompetencyTypes();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        update = 0;
        saveOrUpdateButton = "Save";
    }

    public void populate(SelectEvent events) {
        hrSmCompetencyTypes = (HrSmCompetencyTypes) events.getObject();
        update = 1;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        saveOrUpdateButton = "Update";
        setIcone("ui-icon-search");
    }

    public void vcl_filiterByCategory(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrSmCompetency = (HrSmCompetency) event.getNewValue();
        }
    }

    public void findByCompTypeName() {
        try {
            if (hrSmCompetencyTypes.getCompetencyType().isEmpty()) {
                competencys = competencyTypeLocal.findAll(hrSmCompetencyTypes);
                competencyDataModel = new ListDataModel(new ArrayList(competencys));
            } else {
                competencys = competencyTypeLocal.findbycompetencyname(hrSmCompetencyTypes);
                competencyDataModel = new ListDataModel(new ArrayList(competencys));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main method">
    public void saveCompetencyType() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "saveCompetencyType", dataset)) {
                try {
                    boolean result = competencyTypeLocal.searchdup(hrSmCompetencyTypes);
                    if (update == 0 && result == true) {
                        JsfUtil.addFatalMessage("data already exist");
                    } else {
                        competencyTypeLocal.SaveOrUpdate(hrSmCompetencyTypes);
                        if (update == 0) {
                            JsfUtil.addSuccessMessage("data saved successfully");
                        } else {
                            JsfUtil.addSuccessMessage("data modified successfully");
                        }
                        hrSmCompetencyTypes = new HrSmCompetencyTypes();
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveCompetencyType");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetCompetencyType() {
        hrSmCompetencyTypes = null;
        hrSmCompetencyTypes = new HrSmCompetencyTypes();
    }
//</editor-fold>
}
