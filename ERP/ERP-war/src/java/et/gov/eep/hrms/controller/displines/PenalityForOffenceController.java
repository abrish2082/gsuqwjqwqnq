/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.displines;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
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
import et.gov.eep.hrms.businessLogic.displine.HrDisciplineOffencePenalityBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplineOffenceTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.displine.HrDisciplinePenaltyTypesBeanLocal;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenality;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;

/**
 *
 * @author user
 */
@Named("penlityForOffenceController")
@ViewScoped
public class PenalityForOffenceController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Entity, bussiness logic & variables">
    @EJB
    HrDisciplineOffencePenalityBeanLocal OffencePenaltyBeanLocal;
    @EJB
    HrDisciplineOffenceTypesBeanLocal offenceTypesBeanLocal;
    @EJB
    HrDisciplinePenaltyTypesBeanLocal penaltyTypesBeanLocal;

    @Inject
    HrDisciplineOffencePenality OffencePenalty;
    @Inject
    HrDisciplineOffenceTypes offenceTypes;
    @Inject
    HrDisciplinePenaltyTypes penaltyTypes;
    @Inject
    SessionBean sessionBeanLocal;

    private String saveorUpdateBundle = "Create";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;

    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;

    List<HrDisciplineOffencePenality> OffencePenalityList;
    private HrDisciplineOffencePenality selectedCom;
    List<HrDisciplineOffenceTypes> nameList;
    List<HrDisciplineOffenceTypes> offenceNameLists;
    List<HrDisciplinePenaltyTypes> disciplinePenaltyTypeses;

    //</editor-fold>
    @PostConstruct
    public void init() {
        listPenalityTypes();
        seachByOffenceCode();
    }

    // <editor-fold defaultstate="collapsed" desc="Getters setters"> 
    public List<HrDisciplineOffenceTypes> getOffenceNameLists() {
        return offenceNameLists = OffencePenaltyBeanLocal.findAllOffnces();
    }

    public void setOffenceNameLists(List<HrDisciplineOffenceTypes> offenceNameLists) {
        this.offenceNameLists = offenceNameLists;
    }

    public List<HrDisciplineOffenceTypes> getNameList() {
        return nameList = offenceTypesBeanLocal.findAlls();
    }

    public void setNameList(List<HrDisciplineOffenceTypes> nameList) {
        this.nameList = nameList;
    }

    public List<HrDisciplineOffencePenality> getOffencePenalityList() {
        if (OffencePenalityList == null) {
            OffencePenalityList = new ArrayList<>();
        }
        return OffencePenalityList;
    }

    public void setOffencePenalityList(List<HrDisciplineOffencePenality> OffencePenalityList) {
        this.OffencePenalityList = OffencePenalityList;
    }

    private String saveOrUpdateButton = "Save";
    int update = 0;

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
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

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getSaveOrUpdateButton() {
        return saveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String saveOrUpdateButton) {
        this.saveOrUpdateButton = saveOrUpdateButton;
    }

    public HrDisciplineOffencePenality getSelectedCom() {
        return selectedCom;
    }

    public void setSelectedCom(HrDisciplineOffencePenality selectedCom) {
        this.selectedCom = selectedCom;
    }

    public HrDisciplinePenaltyTypes getPenaltyTypes() {
        if (penaltyTypes == null) {
            penaltyTypes = new HrDisciplinePenaltyTypes();
        }
        return penaltyTypes;
    }

    public void setPenaltyTypes(HrDisciplinePenaltyTypes penaltyTypes) {
        this.penaltyTypes = penaltyTypes;
    }

    public HrDisciplineOffencePenality getOffencePenalty() {
        if (OffencePenalty == null) {
            OffencePenalty = new HrDisciplineOffencePenality();
        }
        return OffencePenalty;
    }

    public HrDisciplineOffenceTypes getOffenceTypes() {
        if (offenceTypes == null) {
            offenceTypes = new HrDisciplineOffenceTypes();
        }
        return offenceTypes;
    }

    public void setOffenceTypes(HrDisciplineOffenceTypes offenceTypes) {
        this.offenceTypes = offenceTypes;
    }

    public void setOffencePenalty(HrDisciplineOffencePenality OffencePenalty) {
        this.OffencePenalty = OffencePenalty;
    }

    List<HrDisciplineOffenceTypes> offenceTypesesList;

    public List<HrDisciplineOffenceTypes> getOffenceTypesesList() {
        return offenceTypesesList;
    }

    public void setOffenceTypesesList(List<HrDisciplineOffenceTypes> offenceTypesesList) {
        this.offenceTypesesList = offenceTypesesList;
    }

    public void seachByOffenceCode() {
        offenceTypesesList = offenceTypesBeanLocal.findByOffenceCode();
    }

    public void displayOffenceChanged(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            offenceTypes = (HrDisciplineOffenceTypes) event.getNewValue();
        }
    }

    public List<HrDisciplinePenaltyTypes> getDisciplinePenaltyTypeses() {
        return disciplinePenaltyTypeses;
    }

    public void setDisciplinePenaltyTypeses(List<HrDisciplinePenaltyTypes> disciplinePenaltyTypeses) {
        this.disciplinePenaltyTypeses = disciplinePenaltyTypeses;
    }

    public void listPenalityTypes() {
        disciplinePenaltyTypeses = penaltyTypesBeanLocal.findAll();
    }

    public void penalityChanged(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            penaltyTypes = (HrDisciplinePenaltyTypes) event.getNewValue();
            OffencePenalty.setPenaltyId(penaltyTypes);
        }
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Search">
    public void populate(SelectEvent events) {
        OffencePenalty = (HrDisciplineOffencePenality) events.getObject();
        offenceTypes = OffencePenalty.getOffenceTypeId();
        penaltyTypes = OffencePenalty.getPenaltyId();
        saveOrUpdateButton = "Update";
        update = 1;
        renderPnlManPage = false;
        renderPnlCreateAdditional = true;
        btnNewRender = true;
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
    }

    public void createNewAdditionalAmount() {
        clearPenalityForOffence();
        saveorUpdateBundle = "Create";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            btnNewRender = false;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            btnNewRender = false;
        }
    }

    public void newButtonAction() {
        clearPenalityForOffence();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        btnNewRender = false;
    }

    public void valueChangeListnerOffencePenality(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            String offenceName = event.getNewValue().toString();
            if (offenceName.equalsIgnoreCase("Load all")) {
                OffencePenalityList = OffencePenaltyBeanLocal.findAlls();
            } else {
                OffencePenalityList = OffencePenaltyBeanLocal.getOffenceAndPenalityListByName(offenceName);
            }
        }
    }

//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Main">
    public void savePenalityForOffence() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "savePenalityForOffence", dataset)) {
                if (OffencePenalty != null) {
                    try {
                        OffencePenalty.setDecider(penaltyTypes.getActionTaker());
                        OffencePenaltyBeanLocal.saveOrUpdate(OffencePenalty);
                        if (update == 1) {
                            JsfUtil.addSuccessMessage("Update Successfully.");
                        } else {
                            JsfUtil.addSuccessMessage("Submitted Successfully");
                        }
                        clearPenalityForOffence();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addSuccessMessage("Error Occur while save update");
                    }
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "savePenalityForOffence");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearPenalityForOffence() {
        OffencePenalty = null;
        penaltyTypes = null;
        offenceTypes = null;
        saveOrUpdateButton = "Save";
        update = 0;
    }
//</editor-fold>
}
